
package com.mycompany.parser.py.main.frame;

import com.mycompany.parser.py.main.analizadorDeTokens.AnalizadorDeTokens;
import com.mycompany.parser.py.main.analizarArchivo.Archivo;
import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import java.awt.Color;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class AnalizadorFronted {
   
    public String cargaDeArchivoEntrante(JTextPane panelDeTexto, ListaElementos<String> lista) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
        String pathEntrante = "";
        
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            pathEntrante = jFileChooser.getSelectedFile().getPath();
            Archivo archivo = new Archivo();
            lista = archivo.crearVisualizacionDeArchivo(pathEntrante);
            colocarTexto(panelDeTexto, lista);
        }
        return pathEntrante;
    }
    
    public ListaElementos<Token> activarReconocimientoDeTokens(String textoEscrito, ListaElementos<Token> tokensIdentificados, JTextPane panelDeTexto,  JTextArea panelErrores, ListaElementos<String> erroresLexicos, ListaElementos<String> erroresSintacticos, ListaElementos<String> lista) {
        if (!"".equals(textoEscrito)) {
            tokensIdentificados = new ListaElementos<>();
            erroresLexicos = new ListaElementos<>();
            Archivo crear = new Archivo();
            int indice = 4;
            String cadena = "";
            
            if (" ".equals("" + textoEscrito.charAt(0)) && "1".equals("" + textoEscrito.charAt(1)) && "|".equals("" + textoEscrito.charAt(2)) && " ".equals("" + textoEscrito.charAt(3))) {
                while (indice < textoEscrito.length()) {
                    if (!"\n".equals("" + textoEscrito.charAt(indice))) {
                        cadena += textoEscrito.charAt(indice);
                    } else {
                        while (true) {
                           if ("|".equals("" + textoEscrito.charAt(indice))) {
                               indice++;
                               break;
                           } else {
                               indice++;
                           }
                        }
                    }
                    indice++;
                }
                textoEscrito = cadena;
            }
            crear.organizarCadena(textoEscrito, tokensIdentificados, erroresLexicos);
            panelErrores.setText("");
            colocarColores(panelDeTexto, tokensIdentificados);
            colocarErrores(panelErrores, erroresLexicos, "Errores Léxicos");
            if (erroresLexicos.estaVacia()) {
                AnalizadorDeTokens analizar = new AnalizadorDeTokens(tokensIdentificados);
                analizar.analizarListaDeTokens();
                colocarErrores(panelErrores, analizar.obtenerErroresSintacticos(), "Errores Sintácticos");
            }
        } else {
            JOptionPane.showMessageDialog(null,"Debes subir un Archivo o Escribir un Texto","Falta de Información",JOptionPane.ERROR_MESSAGE);
        }
        return tokensIdentificados;
    }
    
    public void comprobarExistenciaDeTokens(ListaElementos<Token> tokensIdentificados, int numero) {
        if (tokensIdentificados.getLongitud() != 0) { 
            if (numero == 1) {
                Grafica visualizar = new Grafica(null, tokensIdentificados);
                visualizar.setVisible(true);
            } else {
                Reportes visualizar = new Reportes(null, tokensIdentificados);
                visualizar.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null,"No hay Tokens para Mostrar","Falta de Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void colocarTexto(JTextPane panelDeTexto, ListaElementos<String> lista) { 
        for (int i = 1; i <= lista.getLongitud(); i++) {
            try {
                if (i == 1) {
                    panelDeTexto.setText(lista.obtenerContenido(i));
                } else {
                    String textoEnPantalla = panelDeTexto.getText() + "\r";
                    panelDeTexto.setText((textoEnPantalla + lista.obtenerContenido(i)));
                }
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error en Mostrar Archivo");
            }
        }
    }
    
    public void colocarErrores(JTextArea panelErrores, ListaElementos<String> errores, String tipoDeError) {
        try {
            if (!errores.estaVacia()) {
                panelErrores.append(tipoDeError + "\r\n\r\n");
            }
            for (int i = 1; i <= errores.getLongitud(); i++) {
                if (i == 1) {
                    panelErrores.append((i + "| " + errores.obtenerContenido(i)));
                } else {
                    panelErrores.append(("\r\n" + i + "| " + errores.obtenerContenido(i)));
                }
                panelErrores.setEditable(false);
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en Mostrar Archivo");
        }
    }
    
    public void colocarColores(JTextPane panelDeTexto, ListaElementos<Token> tokensIdentificados) {
        StyledDocument doc = panelDeTexto.getStyledDocument();
        Style style = panelDeTexto.addStyle("I'm a Style", null);
        panelDeTexto.setText("");
        int filas = 2;
        String recuento = " 1| ";
        
        for (int i = 1; i <= tokensIdentificados.getLongitud(); i++) {
            
            try {
                if ("Identificador".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                    StyleConstants.setForeground(style, Color.BLACK);
                } else if ("Aritmetico".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken()) || "Comparacion".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken()) || "Asignacion".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                    StyleConstants.setForeground(style, Color.cyan);
                } else if ("Palabra Clave".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                    StyleConstants.setForeground(style, Color.PINK);
                } else if ("Constante".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                    StyleConstants.setForeground(style, Color.RED);
                } else if ("Comentario".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                    StyleConstants.setForeground(style, Color.GRAY);
                } else if ("Otros".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                    StyleConstants.setForeground(style, Color.GREEN);
                } else {
                    StyleConstants.setForeground(style, Color.BLUE);
                }
                String combinacion = "";
                
                if (i == 1) {
                    combinacion = tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto();
                } else if (i != tokensIdentificados.getLongitud()) {
                    if (tokensIdentificados.obtenerContenido(i).obtenerFila() == tokensIdentificados.obtenerContenido(i - 1).obtenerFila()) {
                        combinacion += tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto();
                    } else {
                        combinacion = tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto();
                    }
                } else {
                    combinacion += tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto();
                }
                
                int contador = 0;
                
                while (contador < combinacion.length()) {
                    if ("\n".equals("" + combinacion.charAt(contador))) {
                        recuento += combinacion.charAt(contador) + " " + filas + "| ";
                        filas++;
                    } else {
                        recuento += combinacion.charAt(contador);
                    }
                    contador++;
                }
                combinacion = recuento;
                recuento = "";
//                while (contador < combinacion.length()) {
//                    if ("\n".equals("" + combinacion.charAt(contador))) {
//                        filas++;
//                    }
//                    contador++;
//                }
            try { doc.insertString(doc.getLength(), combinacion, style); }
                catch (BadLocationException e){
                    System.out.println("Error: " + e.getMessage());
                }
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
//        String no = "";
//        for (int i = 1; i <= tokensIdentificados.getLongitud(); i++) {
//            try {
//                if ("def".equals(tokensIdentificados.obtenerContenido(i).obtenerLexema())) {
//                    no = tokensIdentificados.obtenerContenido(i + 1).obtenerLexema();
//                } else if (tokensIdentificados.obtenerContenido(i).obtenerLexema().equals(no)) {
//                    System.out.println("Encontrado!!!!!!!"
//                            + "");
//                }
//            } catch (ListaElementosExcepcion ex) {
//                System.out.println("................");
//            }
//        }
    }
}
