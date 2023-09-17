
package com.mycompany.parser.py.main.frame;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import javax.swing.JTextPane;

public class ReporteBloquesFronted {
    
    public void mostrarBloques(JTextPane panelDeTexto, String bloqueSeleccionado, ListaElementos<Token> tokensIdentificados) {
        ListaElementos<String> listaDeBloques = new ListaElementos<>();
        int indicePrueba = 1;
        int filaComparacion = 0;
        String union = "", tabulaciones = "";
        
        try {
            while (indicePrueba != (tokensIdentificados.getLongitud() + 1)) {
                if (tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema().equals(bloqueSeleccionado)){
                    int referencia = tokensIdentificados.obtenerContenido(indicePrueba).obtenerBloque();
                    tabulaciones = tokensIdentificados.obtenerContenido(indicePrueba).obtenerTabulaciones();
                    union = tokensIdentificados.obtenerContenido(indicePrueba).obtenerLexema();
                    filaComparacion = tokensIdentificados.obtenerContenido(indicePrueba).obtenerFila();
                    indicePrueba++;
                    
                    while (true) {
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
                    }
                    listaDeBloques.agregarALaLista("");
                    listaDeBloques.agregarALaLista("------------------------------------------------");
                    listaDeBloques.agregarALaLista("");
                }
                indicePrueba++;
            }
            colocarTexto(panelDeTexto, listaDeBloques);
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error al momento de invocar bloque");
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
