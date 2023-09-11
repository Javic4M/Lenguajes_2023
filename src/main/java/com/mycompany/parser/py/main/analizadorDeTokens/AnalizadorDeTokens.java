
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
        
        while (indice != (tokensIdentificados.getLongitud() + 1)) {
            if (analizarMixto()) {
                
            } else if (esUnComentario()) {
                
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
                } else {
                    analizarAsignacionEspecial();
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizador Mixco de tipo: " + ex);
        }
        return estado;
    }
    
    public boolean analizarSentencias() {
       
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("if")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else")) {
                        indice++;
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
                        } else {
                            // dfadf}
                            verificarBloqueIf();
                        }
                    }
                }
            } else {
                // Para ver si no es un bloque if
                verificarBloqueIf();
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizador de Sentencias de tipo: " + ex);
        }
        return false;
    }
    
    public void analizarAsignacionEspecial() {
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("=")) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",")) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                    System.out.println("Se Encontro una Asignacion Especial");
                                    if (indice != tokensIdentificados.getLongitud()) {
                                        indice++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizador Especial de tipo: " + ex);
        }
    }
    
    public boolean esUnComentario() {
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comentario")) {
                indice++;
                System.out.println("Se encontro Comentario");
                return true;
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizado de Comentario de tipo: " + ex);
        }
        return false;
    }
    
    public void verificarBloqueIf() throws ListaElementosExcepcion {
        //Hay que encontrar una forma de determinar si es una constante, un identificado o un true
        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(">")) {
            indice++;
            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                    boolean salir = false;
                    
                    while(!salir) {
                        
                    }
                }
            }
        }
    }
}
