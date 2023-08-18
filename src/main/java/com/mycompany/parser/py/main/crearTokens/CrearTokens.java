
package com.mycompany.parser.py.main.crearTokens;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.tokens.Token;

public class CrearTokens {
    
    private boolean finDeLinea = false;
    private int indice = 1, columnaMovil = 0;
    
    // Método en Caso se encuentre un Comentario  
    public int analizarComentarios(int fila, int columna, int columnaAMandar, String palabra, ListaElementos<Token> tokensIdentificados) {
        String cadena = "";
        
        while (true) {
            columna++;
            
            if (columna != (palabra.length())) {
                if ("\r".equals("" + palabra.charAt(columna))) {
                    tokensIdentificados.agregarALaLista(new Token("Comentario", "#" + cadena, fila, columnaAMandar));
                    break;
                } else {
                    cadena += palabra.charAt(columna);
                }
            } else {
                tokensIdentificados.agregarALaLista(new Token("Comentario", "#" + cadena, fila, columnaAMandar));
                break;
            }
        }
        return columna;
    }
    
    // Método Central de Reconicimiento
    public void analizarCentral(int fila, int columna, String palabra, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores) {
        if (!analizarTipoDeToken(palabra, true, tokensIdentificados, fila, columna - palabra.length())) {         
            if (!analizarConstantes(palabra, true, false, tokensIdentificados, errores, fila, columna - palabra.length())) {
                analizarRestante(palabra, tokensIdentificados, errores, fila, columna - palabra.length());
            }
        }
    }
    
    //Métodos de Verificación
    private boolean analizarTipoDeToken(String palabra, boolean crearToken, ListaElementos<Token> tokensIdentificados, int fila, int columna) {
        TablaDeSimbolos tabla = new TablaDeSimbolos();
        
        if (tabla.obtenerTablaDeSimbolos().containsKey(palabra)) {    
            if (crearToken) {
                tokensIdentificados.agregarALaLista(new Token(tabla.obtenerTablaDeSimbolos().get(palabra), palabra, fila, columna));
            }
            return true;
        } else {
            return false; 
        }
    }
       
    private boolean analizarConstantes(String palabra, boolean crearToken, boolean crearError, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, int fila, int columna) {
        
        if (!"".equals(palabra)) {
            if (palabra.charAt(0) == '0' || palabra.charAt(0) == '1' || palabra.charAt(0) == '2' || palabra.charAt(0) == '3' || palabra.charAt(0) == '4' || palabra.charAt(0) == '5' || palabra.charAt(0) == '6' || palabra.charAt(0) == '7' || palabra.charAt(0) == '8' || palabra.charAt(0) == '9') {
                int contador = 0, contadorPunto = 0;

                for (int indice = 0; indice < palabra.length(); indice++) {
                    if (palabra.charAt(indice) == '0' || palabra.charAt(indice) == '1' || palabra.charAt(indice) == '2' || palabra.charAt(indice) == '3' || palabra.charAt(indice) == '4' || palabra.charAt(indice) == '5' || palabra.charAt(indice) == '6' || palabra.charAt(indice) == '7' || palabra.charAt(indice) == '8' || palabra.charAt(indice) == '9') {
                        contador++;
                    } else {
                        if (palabra.charAt(indice) == '.') {
                            contadorPunto++;
                        }
                    }
                }

                if (palabra.length() == contador) {
                    if (crearToken) {
                        tokensIdentificados.agregarALaLista(new Token("Constante", palabra, fila, columna));
                    }
                } else if (palabra.length() == contador + contadorPunto) {
                    if (crearToken) {
                        tokensIdentificados.agregarALaLista(new Token("Constante", palabra, fila, columna));
                    }
                } else {
                    if (crearError) {
                        errores.agregarALaLista("Error Númerico");
                        return true;
                    }
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    // Analiza Palabras Restantes
    private void analizarRestante(String palabra, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, int fila, int columna) {
        String union = "";
        indice = 0;          
        columnaMovil = columna;
        
        while (indice != palabra.length()) {
            
            if (analizarSigno(palabra, tokensIdentificados, false, fila, columnaMovil)) {
                if (!analizarTipoDeToken(union, true, tokensIdentificados, fila, columnaMovil - union.length())) {  
                    if (!analizarConstantes(union, true, true, tokensIdentificados, errores, fila, columnaMovil - union.length())) {
                        union = analizarIdentificadores(union, tokensIdentificados, errores, fila, columnaMovil - union.length());
                    } else {
                        union = "";
                    }
                } else {
                    union = "";
                }
                analizarSigno(palabra, tokensIdentificados, true, fila, columnaMovil);
            } else {
                union += palabra.charAt(indice);
            }
            indice++; columnaMovil++;
        }
        
        if (!"".equals(union)) { 
            if (!analizarTipoDeToken(union, true, tokensIdentificados, fila, columnaMovil - union.length())) { 
                if (!analizarConstantes(union, true, true, tokensIdentificados, errores, fila, columnaMovil - union.length())) {
                    union = analizarIdentificadores(union, tokensIdentificados, errores, fila, columnaMovil - union.length());
                }
            }
        }
    }
    
    private boolean analizarSigno(String palabra, ListaElementos<Token> tokensIdentificados, boolean crearToken, int fila, int columna) {
        TablaDeSimbolos tabla = new TablaDeSimbolos();
        String cadenaSigno = "" + palabra.charAt(indice);
        
        if (tabla.obtenerTablaDeSimbolos().containsKey("" + palabra.charAt(indice))) {
            if ("+".equals("" + palabra.charAt(indice)) || "-".equals("" + palabra.charAt(indice)) || "*".equals("" + palabra.charAt(indice)) || "/".equals("" + palabra.charAt(indice)) || "=".equals("" + palabra.charAt(indice)) || "<".equals("" + palabra.charAt(indice)) || ">".equals("" + palabra.charAt(indice)) || "!".equals("" + palabra.charAt(indice))) {
                if ("=".equals("" + palabra.charAt(indice + 1)) || "/".equals("" + palabra.charAt(indice + 1))) {
                    cadenaSigno += "" + palabra.charAt(indice + 1);
                    indice++; columnaMovil++;
                }
            }
            if (crearToken) {
                analizarTipoDeToken(cadenaSigno, true, tokensIdentificados, fila, columna);
            }
            return true;
        }
        return false;
    }

    private String analizarIdentificadores(String palabra, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, int fila, int columna) {
        if (palabra.charAt(0) == '0' || palabra.charAt(0) == '1' || palabra.charAt(0) == '2' || palabra.charAt(0) == '3' || palabra.charAt(0) == '4' || palabra.charAt(0) == '5' || palabra.charAt(0) == '6' || palabra.charAt(0) == '7' || palabra.charAt(0) == '8' || palabra.charAt(0) == '9') {
            errores.agregarALaLista("Error, un Identificado no puede empezar con un Número");
        } else { 
            tokensIdentificados.agregarALaLista(new Token("Identificador", palabra, fila, columna));
        }
        return "";
    }
            
    // Método en Caso se encuentre una Cadena 
    public int analizarCadena(int fila, int columna, int columnaAMandar, String palabra, String signo, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores) {
        String union = "";
        int contadorDeComillas = 0;

        while (true) {

            if (columna != (palabra.length())) {
                if (!"\r".equals("" + palabra.charAt(columna))) {

                    if (("" + palabra.charAt(columna)).equals(signo)) {
                        contadorDeComillas++;

                        if (contadorDeComillas == 2) {
                            tokensIdentificados.agregarALaLista(new Token("Constante",signo + union + signo, fila, columnaAMandar));
                            columna++;
                            finDeLinea = false;
                            break;
                        }
                    } else {
                        union += palabra.charAt(columna);
                    }
                } else {
                    errores.agregarALaLista("Error en la Escritura de la Cadena");
                    finDeLinea = true;
                    break;
                }
                columna++;
            } else {
                errores.agregarALaLista("Error en la Escritura de la Cadena");
                finDeLinea = true;
                break;
            }
        }
        return columna;
    }
    
    public boolean saberFinDeLinea() {
        return finDeLinea;
    } // angel profundo tu amor si en el mundo
}