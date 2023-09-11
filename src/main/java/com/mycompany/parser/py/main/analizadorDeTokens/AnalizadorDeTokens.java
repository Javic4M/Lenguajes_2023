
package com.mycompany.parser.py.main.analizadorDeTokens;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;

public class AnalizadorDeTokens {
    
    private ListaElementos<Token> tokensIdentificados;
    private int indice;
    
    public AnalizadorDeTokens(ListaElementos<Token> tokensIdentificados, int indice) {
        this.indice = indice;
        this.tokensIdentificados = tokensIdentificados;
    }
    
    public void analizarListaDeTokens() {
        System.out.println("Longitud: " + tokensIdentificados.getLongitud());
        
        while (indice != tokensIdentificados.getLongitud()) {
            if (!analizarMixto()) {
                
            }
        }
    }
    
    public boolean analizarMixto() {
        boolean estado = false;
        
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                indice++; // 2
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Asignacion")) {
                    indice++; // 3
                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                        if (indice != tokensIdentificados.getLongitud()) {
                            
                            if (tokensIdentificados.obtenerContenido(indice + 1).obtenerLexema().equals("if")) {
                                indice++; // 4
                                estado = analizarSentencias();
                            } else {
                                System.out.println("Es una Asignacion");
                                indice++;
                                estado = true;
                            }
                        } else {
                            System.out.println("Es una Asignacion");
                            if (indice != tokensIdentificados.getLongitud()) {
                                indice++;
                            }
                            estado = true;
                        }
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizado de Tokens de tipo: " + ex);
        }
        return estado;
    }
    
    public boolean analizarSentencias() {
        // Exiten dos posibles casos el primero que sea una sentencia normal y la otra que sea una sentencia corta
        
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("if")) {
                indice++; // 5
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                    indice++; // 6
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else")) {
                        indice++; //7
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                            System.out.println("Sentencia normal");
                            if (indice != tokensIdentificados.getLongitud()) {
                                indice++;
                            }
                        }
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                System.out.println("Sentencia con not");
                                if (indice != tokensIdentificados.getLongitud()) {
                                    indice++;
                                }
                            }
                        }
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizado de Tokens de tipo: " + ex);
        }
        return false;
    }
    
}
