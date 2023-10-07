
package com.mycompany.parser.py.main.frame;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReportesFronted {
    
    private ListaElementos<Token> tokensIdentificados;
    private DefaultTableModel tablaModelo;
    private JTable tablaReporte;
    private String caracter;
    private int indiceBloque = 1;
    private boolean moverIndice = true;
    private boolean bloque = true;
    
    public ReportesFronted(ListaElementos<Token> tokensIdentificados, JTable tablaReporte, DefaultTableModel tablaModelo) {
        this.tokensIdentificados = tokensIdentificados;
        this.tablaReporte = tablaReporte;
        this.tablaModelo = tablaModelo;
    }
    
    public void mostrarInformacionTokens(String bloqueSeleccionado) {
        if ("Global".equals(bloqueSeleccionado)) {
            mostrarTablaDeSimbolos();
        } else {
            if ("Variables".equals(bloqueSeleccionado)) {
                caracter = "variables";
            } else if ("If".equals(bloqueSeleccionado)) {
                caracter = "if";
            } else if ("For".equals(bloqueSeleccionado)) {
                caracter = "for";
            } else if ("While".equals(bloqueSeleccionado)) {
                caracter = "while";
            }
            mostrarEspecifico();
        }
    }
    
    private void mostrarTablaDeSimbolos() {
        tablaReporte.removeAll();
        String[] titulos = {"Token", "Patrón", "Lexema", "Linea", "Columna"};
        tablaModelo.setColumnIdentifiers(titulos);
        tablaReporte.setModel(tablaModelo);
        tablaModelo.setRowCount(0);
        
        if (tokensIdentificados.getLongitud() != 0) { 
            for (int i = 1; i <= tokensIdentificados.getLongitud(); i++) {
                try {
                    if (!"Espacio".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken()) && !"Error".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                        tablaModelo.addRow(new Object[] {tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken(), tokensIdentificados.obtenerContenido(i).obtenerPatron(), tokensIdentificados.obtenerContenido(i).obtenerLexema(), tokensIdentificados.obtenerContenido(i).obtenerFila(), tokensIdentificados.obtenerContenido(i).obtenerColumna()});
                    }
                } catch (ListaElementosExcepcion ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null,"No hay Tokens para Mostrar", "Falta de Tokens", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void mostrarEspecifico() {
        tablaReporte.removeAll();
        String[] titulos = {"Lexema", "Tipo", "Linea", "Columna/Bloque"};
        tablaModelo.setColumnIdentifiers(titulos);
        tablaReporte.setModel(tablaModelo);
        tablaModelo.setRowCount(0);
        
        int indice = 1;
        int indiceInicio = 1;
        String texto = "";
        String tipo = "";
        
        while (indice != tokensIdentificados.getLongitud()) {
            try {
                if (caracter.equals(tokensIdentificados.obtenerContenido(indice).obtenerLexema())) {
                    indiceInicio = indice;
                    texto = tokensIdentificados.obtenerContenido(indice).obtenerLexemaCompuesto();
                    tipo = caracter;
                    indice++;
                    
                    while (tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        texto += tokensIdentificados.obtenerContenido(indice).obtenerLexemaCompuesto();
                        indice++;
                    }
                    tablaModelo.addRow(new Object[] {texto, caracter,tokensIdentificados.obtenerContenido(indiceInicio).obtenerFila(), tokensIdentificados.obtenerContenido(indiceInicio).obtenerBloque()});
                    texto = "";
                } else if (("elif".equals(tokensIdentificados.obtenerContenido(indice).obtenerLexema()) || "else".equals(tokensIdentificados.obtenerContenido(indice).obtenerLexema())) && "if".equals(caracter)){
                    indiceInicio = indice;
                    texto = tokensIdentificados.obtenerContenido(indice).obtenerLexemaCompuesto();
                    tipo = caracter;
                    indice++;
                    
                    while ((tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) || (tokensIdentificados.obtenerContenido(indiceInicio).obtenerColumna() < tokensIdentificados.obtenerContenido(indice).obtenerColumna())) {
                        texto += tokensIdentificados.obtenerContenido(indice).obtenerLexemaCompuesto();
                        indice++;
                    }
                    tablaModelo.addRow(new Object[] {texto, caracter,tokensIdentificados.obtenerContenido(indiceInicio).obtenerFila(), tokensIdentificados.obtenerContenido(indiceInicio).obtenerBloque()});
                    texto = "";
                } else {
                    if (caracter.equals("variables")) {
                        if ("if".equals(tokensIdentificados.obtenerContenido(indice).obtenerLexema()) || "elif".equals(tokensIdentificados.obtenerContenido(indice).obtenerLexema()) || "else".equals(tokensIdentificados.obtenerContenido(indice).obtenerLexema()) || "while".equals(tokensIdentificados.obtenerContenido(indice).obtenerLexema()) || "for".equals(tokensIdentificados.obtenerContenido(indice).obtenerLexema())) {
                            indice++;
                            while (tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                            }
                        } else {
                            texto = tokensIdentificados.obtenerContenido(indice).obtenerLexemaCompuesto();
                            indiceInicio = indice;
                            indice++;
                            
                            tipo = obtenerTipo(indiceInicio);
                            
                            while (tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                texto += tokensIdentificados.obtenerContenido(indice).obtenerLexemaCompuesto();
                                indice++;
                            }
                            tablaModelo.addRow(new Object[] {texto, tipo,tokensIdentificados.obtenerContenido(indiceInicio).obtenerFila(), tokensIdentificados.obtenerContenido(indiceInicio).obtenerBloque()});
                            texto = "";
                        }
                    } else {
                        indice++;
                    }
                }
            } catch (ListaElementosExcepcion ex) {
                if (!"".equals(texto)) {
                    try {
                        tablaModelo.addRow(new Object[] {texto, tipo,tokensIdentificados.obtenerContenido(indiceInicio).obtenerFila(), tokensIdentificados.obtenerContenido(indiceInicio).obtenerBloque()});
                    } catch (ListaElementosExcepcion ex1) {
                        System.out.println("Error en Mostrar Especificos");
                    }
                }
                break;
            }
        }
    }
    
    public void mostrarInformacionBloque(String bloqueSeleccionado) {
        tablaReporte.removeAll();
        String[] titulos = {"Lexema", "Tipo", "Linea", "Columna/Bloque"};
        tablaModelo.setColumnIdentifiers(titulos);
        tablaReporte.setModel(tablaModelo);
        tablaModelo.setRowCount(0);
        
        if ("If".equals(bloqueSeleccionado)) {
            caracter = "if";
        } else if ("For".equals(bloqueSeleccionado)) {
            caracter = "for";
        } else if ("While".equals(bloqueSeleccionado)) {
            caracter = "while";
        } else if ("Def".equals(bloqueSeleccionado)) {
            caracter = "def";
        }
        int numeroDeBloqueIf = 0;
        indiceBloque = 1;
        
        try {
            while (indiceBloque != (tokensIdentificados.getLongitud() + 1)) {
                moverIndice = true;

                if (tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexema().equals(caracter) && "while".equals(caracter)){
                    bloque = true;
                    mostrarBloque();
                } else if (tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexema().equals(caracter) && "if".equals(caracter) && "Normal".equals(tokensIdentificados.obtenerContenido(indiceBloque).obtenerTipoDeEstructura())){
                    numeroDeBloqueIf = tokensIdentificados.obtenerContenido(indiceBloque).obtenerBloque();   
                    mostrarBloque(); 

                    if (tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexema().equals("elif")) {
                        bloque = true;
                        mostrarBloque();
                    }
                    if (tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexema().equals("else") && tokensIdentificados.obtenerContenido(indiceBloque).obtenerBloque() >= numeroDeBloqueIf) {
                        bloque = true;
                        mostrarBloque();
                    }
                } else if (tokensIdentificados.obtenerContenido(indiceBloque).obtenerTipoDeToken().equals(caracter) && "Identificador".equals(caracter) && "Especial".equals(tokensIdentificados.obtenerContenido(indiceBloque).obtenerTipoDeEstructura())){
                    bloque = false;
                    mostrarBloque();
                } else if (tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexema().equals(caracter) && "for".equals(caracter)) {
                    bloque = true;
                    mostrarBloque();

                    if (tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexema().equals("else") && tokensIdentificados.obtenerContenido(indiceBloque).obtenerBloque() >= numeroDeBloqueIf) {
                        bloque = true;
                        mostrarBloque();
                    }
                } else if (tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexema().equals(caracter) && "def".equals(caracter)) {
                    bloque = true;
                    mostrarBloque();
                }
                if (moverIndice) {
                    indiceBloque++;
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error al momento de invocar bloque");
        }
    }
    
    private void mostrarBloque() {
        try {
            int filaComparacion = 0;
            String union = "";
            int referencia = tokensIdentificados.obtenerContenido(indiceBloque).obtenerBloque();
            union = tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexemaCompuesto();
            filaComparacion = tokensIdentificados.obtenerContenido(indiceBloque).obtenerFila();
            int indiceInicio = indiceBloque;
            String tipo = obtenerTipo(indiceInicio);
            indiceBloque++;
            
            while (true) {
                if (indiceBloque != tokensIdentificados.getLongitud()) {
                    
                    if (bloque) {
                        // Analiza Bloques
                        if (referencia < tokensIdentificados.obtenerContenido(indiceBloque).obtenerBloque() || tokensIdentificados.obtenerContenido(indiceBloque).obtenerFila() == filaComparacion){
                            if (tokensIdentificados.obtenerContenido(indiceBloque).obtenerFila() == tokensIdentificados.obtenerContenido(indiceBloque - 1).obtenerFila()) {
                                union += tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexemaCompuesto();
                                indiceBloque++;
                            } else {
                                tablaModelo.addRow(new Object[] {union, tipo,tokensIdentificados.obtenerContenido(indiceInicio).obtenerFila(), tokensIdentificados.obtenerContenido(indiceBloque - 1).obtenerBloque()});
                                union = tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexemaCompuesto();
                                tipo = obtenerTipo(indiceBloque);
                                indiceBloque++;
                            }
                        } else {
                            if (!"".equals(union)) {
                                tablaModelo.addRow(new Object[] {union, tipo,tokensIdentificados.obtenerContenido(indiceInicio).obtenerFila(), tokensIdentificados.obtenerContenido(indiceBloque - 1).obtenerBloque()});
                                union = "";
                            }
                            break;
                        }
                    } else {
                        // Analiza Lineas
                        if (tokensIdentificados.obtenerContenido(indiceBloque).obtenerFila() == filaComparacion){
                            union += tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexemaCompuesto();
                            indiceBloque++;
                        } else {
                            if (!"".equals(union)) {
                                tablaModelo.addRow(new Object[] {union, tipo,tokensIdentificados.obtenerContenido(indiceInicio).obtenerFila(), tokensIdentificados.obtenerContenido(indiceBloque - 1).obtenerBloque()});
                                union = "";
                            }
                            break;
                        }
                    }
                } else {
                    if (!"".equals(union)) {
                        union += tokensIdentificados.obtenerContenido(indiceBloque).obtenerLexemaCompuesto();
                        tablaModelo.addRow(new Object[] {union, tipo,tokensIdentificados.obtenerContenido(indiceInicio).obtenerFila(), tokensIdentificados.obtenerContenido(indiceBloque - 1).obtenerBloque()});
                    }
                    break;
                }
            }
            moverIndice = false;
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en AnalizarBloque de tipo: " + ex.getMessage());
        }
    }
    
    public String obtenerTipo(int indiceInicio) {
        String tipo = "";
        try {
            if ("def".equals(tokensIdentificados.obtenerContenido(indiceInicio).obtenerLexema())) {
                tipo = "Metodo";
            } else if ("Identificador".equals(tokensIdentificados.obtenerContenido(indiceInicio).obtenerTipoDeToken())) {
                tipo = "Asignación";
            } else if ("Comentario".equals(tokensIdentificados.obtenerContenido(indiceInicio).obtenerTipoDeToken())) {
                tipo = "Comentario";
            } else {
                tipo = tokensIdentificados.obtenerContenido(indiceInicio).obtenerLexema();
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en Obtener Tipo");
        }
        return tipo;
    }
}
