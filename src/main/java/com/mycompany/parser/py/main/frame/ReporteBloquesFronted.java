
package com.mycompany.parser.py.main.frame;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import javax.swing.JTextPane;

public class ReporteBloquesFronted {
    
    private ListaElementos<String> listaDeBloques = new ListaElementos<>();
    private int indicePrueba = 1;
    private boolean bloque = true, moverIndice = true;
    
    public void mostrarBloques(JTextPane panelDeTexto, String bloqueSeleccionado, ListaElementos<Token> tokensIdentificados) {
        try {
            while (indicePrueba != (tokensIdentificados.getLongitud() + 1)) {
                moverIndice = true;
                
                if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals(bloqueSeleccionado) && "while".equals(bloqueSeleccionado)){
                    bloque = true;
                    analizarBloque(tokensIdentificados);
                } else if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals(bloqueSeleccionado) && "if".equals(bloqueSeleccionado) && "Normal".equals(tokensIdentificados.obtenerContenido(indicePrueba).obtenerTipoDeIf())){
                    bloque = true;
                    analizarBloque(tokensIdentificados);
                    if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals("elif")) {
                        bloque = true;
                        analizarBloque(tokensIdentificados);
                    }
                    if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals("else")) {
                        bloque = true;
                        analizarBloque(tokensIdentificados);
                    }
                } else if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals(bloqueSeleccionado) && "Operador Ternario".equals(bloqueSeleccionado) && "Especial".equals(tokensIdentificados.obtenerContenido(indicePrueba).obtenerTipoDeIf())){
                    bloque = false;
                    analizarBloque(tokensIdentificados);
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
            int filaComparacion = 0;
            String union = "", tabulaciones = "";          
            int referencia = tokensIdentificados.obtenerContenido(indicePrueba).obtenerBloque();
            tabulaciones = tokensIdentificados.obtenerContenido(indicePrueba).obtenerTabulaciones();
            union = tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema();
            filaComparacion = tokensIdentificados.obtenerContenido(indicePrueba).obtenerFila();
            indicePrueba++;
                    
            while (true) {
                if (indicePrueba != tokensIdentificados.getLongitud()) {
                    
                    if (bloque) {
                        // Analiza Bloques
                        if (referencia < tokensIdentificados.obtenerContenido(indicePrueba).obtenerBloque() || tokensIdentificados.obtenerContenido(indicePrueba).obtenerFila() == filaComparacion){
                            if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerFila() == tokensIdentificados.obtenerContenido(indicePrueba - 1).obtenerFila()) {
                                union += tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema();
                                indicePrueba++;
                            } else {
                                listaDeBloques.agregarALaLista(tabulaciones + union);
                                union = "";
                                union += tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema();
                                tabulaciones = tokensIdentificados.obtenerContenido(indicePrueba).obtenerTabulaciones();
                                indicePrueba++;
                            }
                        } else {
                            if (!"".equals(union)) {
                                listaDeBloques.agregarALaLista(tabulaciones + union);
                                union = "";
                            }
                            break;
                        }
                    } else {
                        // Analiza Lineas
                        if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerFila() == filaComparacion){
                            if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerFila() == tokensIdentificados.obtenerContenido(indicePrueba - 1).obtenerFila()) {
                                union += tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema();
                                indicePrueba++;
                            } else {
                                listaDeBloques.agregarALaLista(tabulaciones + union);
                                union = "";
                                union += tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema();
                                tabulaciones = tokensIdentificados.obtenerContenido(indicePrueba).obtenerTabulaciones();
                                indicePrueba++;
                            }
                        } else {
                            if (!"".equals(union)) {
                                listaDeBloques.agregarALaLista(tabulaciones + union);
                                union = "";
                            }
                            break;
                        }
                    }
                } else {
                    if (!"".equals(union)) {
                        union += tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema();
                        listaDeBloques.agregarALaLista(tabulaciones + union);
                        union = "";
                    }
                    break;
                }
            }
            moverIndice = false;
            listaDeBloques.agregarALaLista("");
            listaDeBloques.agregarALaLista("------------------------------------------------");
            listaDeBloques.agregarALaLista("");
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en AnalizarBloque de tipo: " + ex.getMessage());
        }
    }
    
    private void colocarTexto(JTextPane panelDeTexto, ListaElementos<String> lista) {
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
}
