
package com.mycompany.parser.py.main.frame;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Asignacion;
import com.mycompany.parser.py.main.tokens.Token;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

public class ReporteBloquesFronted {
    
    private ListaElementos<Asignacion> informacionBloquesl = new ListaElementos<>();
    private ListaElementos<String> listaDeBloques = new ListaElementos<>();
    private int indicePrueba = 1, fila = 1;
    private boolean bloque = true, moverIndice = true, finDeBloque = false;
    private String tipoDeBloque;
    
    public void mostrarBloques(JTextPane panelDeTexto, String bloqueSeleccionado, ListaElementos<Token> tokensIdentificados) {
        int numeroDeGuia = 0;
        
        try {
            if ("Operador Ternario".equals(bloqueSeleccionado)) {
                bloqueSeleccionado = "Identificador";
            }
            
            while (indicePrueba != (tokensIdentificados.getLongitud() + 1)) {
                moverIndice = true;
                finDeBloque = false;
                
                if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals(bloqueSeleccionado) && "while".equals(bloqueSeleccionado)){
                    tipoDeBloque = "while"; // Tipo de Bloque
                    bloque = true;
                    finDeBloque = true;
                    analizarBloque(tokensIdentificados);
                } else if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals(bloqueSeleccionado) && "if".equals(bloqueSeleccionado) && "Normal".equals(tokensIdentificados.obtenerContenido(indicePrueba).obtenerTipoDeEstructura())){
                    tipoDeBloque = "if"; // Tipo de Bloque
                    numeroDeGuia = tokensIdentificados.obtenerContenido(indicePrueba).obtenerColumna();   
                    analizarBloque(tokensIdentificados);
                    
                    if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals("elif")) {
                        bloque = true;
                        analizarBloque(tokensIdentificados);
                    }
                    if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals("else") && tokensIdentificados.obtenerContenido(indicePrueba).obtenerColumna() >= numeroDeGuia) {
                        bloque = true;
                        finDeBloque = true;
                        analizarBloque(tokensIdentificados);
                    }
                    if (!finDeBloque) {
                        listaDeBloques.agregarALaLista("\r");
                        listaDeBloques.agregarALaLista("------------------------------------------------------------------------------------------------------\r");
                        listaDeBloques.agregarALaLista("\r");
                    }
                } else if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerTipoDeToken().equals(bloqueSeleccionado) && "Identificador".equals(bloqueSeleccionado) && "Especial".equals(tokensIdentificados.obtenerContenido(indicePrueba).obtenerTipoDeEstructura())){
                    tipoDeBloque = "Operador Ternario"; // Tipo de Bloque
                    bloque = false;
                    analizarBloque(tokensIdentificados);
                } else if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals(bloqueSeleccionado) && "for".equals(bloqueSeleccionado)) {
                    tipoDeBloque = "for"; // Tipo de Bloque
                    numeroDeGuia = tokensIdentificados.obtenerContenido(indicePrueba).obtenerColumna();   
                    bloque = true;
                    analizarBloque(tokensIdentificados);
                    
                    if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals("else") && tokensIdentificados.obtenerContenido(indicePrueba).obtenerColumna() >= numeroDeGuia) {
                        bloque = true;
                        finDeBloque = true;
                        analizarBloque(tokensIdentificados);
                    }
                    if (!finDeBloque) {
                        listaDeBloques.agregarALaLista("\r");
                        listaDeBloques.agregarALaLista("------------------------------------------------------------------------------------------------------\r");
                        listaDeBloques.agregarALaLista("\r");
                    }
                }
                if (moverIndice) {
                    indicePrueba++;
                }
            }
            colocarTexto(panelDeTexto, listaDeBloques);
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error al momento de invocar bloque");
        }
    }
    
    private void analizarBloque(ListaElementos<Token> tokensIdentificados) {
        try {
            int filaComparacion = tokensIdentificados.obtenerContenido(indicePrueba).obtenerFila();
            int referenciaColumna = tokensIdentificados.obtenerContenido(indicePrueba).obtenerColumna();
            String union = tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexemaCompuesto();
            indicePrueba++;
                    
            while (true) {
                if (indicePrueba != (tokensIdentificados.getLongitud() + 1)) {
                    
                    if (bloque) {
                        // Analiza Bloques
                        if (referenciaColumna < tokensIdentificados.obtenerContenido(indicePrueba).obtenerColumna() || tokensIdentificados.obtenerContenido(indicePrueba).obtenerFila() == filaComparacion){
                            if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerFila() == tokensIdentificados.obtenerContenido(indicePrueba - 1).obtenerFila()) {
                                union += tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexemaCompuesto();
                                indicePrueba++;
                            } else {
                                listaDeBloques.agregarALaLista(union);
                                union = "";
                                union += tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexemaCompuesto();
                                indicePrueba++;
                            }
                        } else {
                            if (!"".equals(union)) {
                                listaDeBloques.agregarALaLista(union);
                                union = "";
                            }
                            break;
                        }
                    } else {
                        // Analiza Lineas
                        if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerFila() == filaComparacion){
                            System.out.println("Lexema: " + tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexemaCompuesto());
                            union += tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexemaCompuesto();
                            indicePrueba++;
                        } else {
                            System.out.println("Union: " + union);
                            if (!"".equals(union)) {
                                listaDeBloques.agregarALaLista(union);
                                listaDeBloques.agregarALaLista("\r");
                                listaDeBloques.agregarALaLista("------------------------------------------------------------------------------------------------------\r");
                                listaDeBloques.agregarALaLista("\r");
                                indicePrueba++;
                            }
                            break;
                        }
                    }
                } else {
//                    if (!"".equals(union)) {
//                        union += tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexemaCompuesto();
//                        listaDeBloques.agregarALaLista(union);
//                        union = "";
//                    }
                    break;
                }
            }
            moverIndice = false;
            
            if (finDeBloque) {
                listaDeBloques.agregarALaLista("\r");
                listaDeBloques.agregarALaLista("------------------------------------------------------------------------------------------------------\r");
                listaDeBloques.agregarALaLista("\r");
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en AnalizarBloque de tipo: " + ex.getMessage());
        }
    }
    
    private void colocarTexto(JTextPane panelDeTexto, ListaElementos<String> lista) {
        StyledDocument doc = panelDeTexto.getStyledDocument();
        Style style = panelDeTexto.addStyle("I'm a Style", null);
        
        for (int i = 1; i <= lista.getLongitud(); i++) {
            try {
                if (i == 1) {
                    panelDeTexto.setText((lista.obtenerContenido(i)));
                } else {
                    String textoEnPantalla = panelDeTexto.getText();
                    if ("Bloque\r".equals(lista.obtenerContenido(i))) { // serenity cox
                        StyleConstants.setForeground(style, Color.RED);
                    } else {
                        StyleConstants.setForeground(style, Color.BLACK);
                    }
                    try {

                        try { doc.insertString(doc.getLength(), lista.obtenerContenido(i), style); }
                        catch (BadLocationException e){
                            System.out.println("Error: " + e.getMessage());
                        }
                    } catch (ListaElementosExcepcion ex) {
                            System.out.println("Error: " + ex.getMessage());
                    }
                    panelDeTexto.setText((textoEnPantalla + lista.obtenerContenido(i)));
                }
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error en Mostrar Archivo");
            }
        }
    }
}
