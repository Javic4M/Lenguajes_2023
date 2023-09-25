
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
    
    public ListaElementos<Token> activarReconocimientoDeTokens(String textoEscrito, ListaElementos<Token> tokensIdentificados, JTextPane panelDeTexto,  JTextArea panelErrores, ListaElementos<String> errores, ListaElementos<String> lista) {
        if (!"".equals(textoEscrito)) {
            tokensIdentificados = new ListaElementos<>();
            errores = new ListaElementos<>();
            Archivo crear = new Archivo();
            crear.organizarCadena(textoEscrito, tokensIdentificados, errores);
            panelErrores.setText("");
            colocarColores(panelDeTexto, tokensIdentificados);
            colocarErrores(panelErrores, errores);
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
                    panelDeTexto.setText((lista.obtenerContenido(i)));
                } else {
                    String textoEnPantalla = panelDeTexto.getText() + "\r";
                    panelDeTexto.setText((textoEnPantalla + lista.obtenerContenido(i)));
                }
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error en Mostrar Archivo");
            }
        }
    }
    
    public void colocarErrores(JTextArea panelErrores, ListaElementos<String> errores) {
        for (int i = 1; i <= errores.getLongitud(); i++) {
            try {
                if (i == 1) {
                    panelErrores.append((i + "| " + errores.obtenerContenido(i)));
                } else {
                    panelErrores.append(("\r\n" + i + "| " + errores.obtenerContenido(i)));
                }
                panelErrores.setEditable(false);
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error en Mostrar Archivo");
            }
        }
    }
    
    public void colocarColores(JTextPane panelDeTexto, ListaElementos<Token> tokensIdentificados) {
        StyledDocument doc = panelDeTexto.getStyledDocument();
        Style style = panelDeTexto.addStyle("I'm a Style", null);
        panelDeTexto.setText("");
        
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
                
                if (i != 1 && i != tokensIdentificados.getLongitud()) {
                    if (tokensIdentificados.obtenerContenido(i + 1).obtenerFila() > tokensIdentificados.obtenerContenido(i).obtenerFila()) {
                        combinacion = tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto() + "\n";
                    } else {
                        combinacion = tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto() + " "; // numeroDeFila + "." +
                    }
                } else if (i == 1) {
                    if (tokensIdentificados.getLongitud() != 1) {
                        if (tokensIdentificados.obtenerContenido(i + 1).obtenerFila() > tokensIdentificados.obtenerContenido(i).obtenerFila()) {
                            combinacion = tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto() + "\n";
                        } else {
                            combinacion = tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto() + " ";
                        }
                    } else {
                        combinacion = tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto();
                    }
                } else {
                    combinacion = tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto();
                }
                
            try { doc.insertString(doc.getLength(), combinacion, style); }
                catch (BadLocationException e){
                    System.out.println("Error: " + e.getMessage());
                }
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        
        String u = "";
        for (int i = 1; i <= tokensIdentificados.getLongitud(); i++) {
            try {
                if (i == 1) {
                    u = tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto_2();
                } else if (i != tokensIdentificados.getLongitud()) {
                    if (tokensIdentificados.obtenerContenido(i).obtenerFila() == tokensIdentificados.obtenerContenido(i - 1).obtenerFila()) {
                        u += tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto_2();
                    } else {
                        System.out.print(u);
                        u = tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto_2();
                    }
                } else {
                    u += tokensIdentificados.obtenerContenido(i).obtenerLexemaCompuesto_2();
                    System.out.print(u);
                }          
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error: lista especial" );
            }
        }
        AnalizadorDeTokens analizar = new AnalizadorDeTokens(tokensIdentificados, 1);
        analizar.analizarListaDeTokens();
    }
}
