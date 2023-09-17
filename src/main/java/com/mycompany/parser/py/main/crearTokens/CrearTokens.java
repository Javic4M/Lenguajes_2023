
package com.mycompany.parser.py.main.crearTokens;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrearTokens {
    
    private boolean finDeLinea = false;
    private int indice = 1, columnaMovil = 0, numeroDeTabuladores = 0, filaAComparar = 0;
    String tabulador = "";
    
    // Método en Caso se encuentre un Comentario 
    public int analizarComentarios(int fila, int columna, int columnaAMandar, String palabra, ListaElementos<Token> tokensIdentificados) {
        String cadena = "";
        numeroDeTabuladores = 0;
        
        while (true) {
            columna++;
            
            if (columna != (palabra.length())) {
                if ("\r".equals("" + palabra.charAt(columna))) {
                    tokensIdentificados.agregarALaLista(new Token("Comentario", "#" + cadena, fila, columnaAMandar, numeroDeTabuladores, tabulador, "\r"));
                    break;
                } else {
                    cadena += palabra.charAt(columna);
                }
            } else {
                tokensIdentificados.agregarALaLista(new Token("Comentario", "#" + cadena, fila, columnaAMandar, numeroDeTabuladores, tabulador, "\r"));
                break;
            }
        }
        return columna;
    }
    
    // Método Central de Reconicimiento
    public void analizarCentral(int fila, int columna, String palabra, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, String caracterSiguiente) {
        int indice = 0;
        String[] cadena = palabra.split("");
        String union = "";
        
        if (filaAComparar != fila) {
            numeroDeTabuladores = 0;
        }
        
        while(indice != cadena.length) {
            if (!"\t".equals(cadena[indice])) {
                union += cadena[indice];
                indice++;
            } else {
                tabulador += "\t";
                numeroDeTabuladores++;
                filaAComparar = fila;
                indice++;
            }
        }      
        palabra = union;
        
        if (!analizarTipoDeToken(palabra, true, tokensIdentificados, fila, columna - palabra.length(), caracterSiguiente)) {         
            if (!analizarConstantes(palabra, true, false, tokensIdentificados, errores, fila, columna - palabra.length(), caracterSiguiente)) {
                analizarRestante(palabra, tokensIdentificados, errores, fila, columna - palabra.length(), caracterSiguiente);
            }
        }
    }
    
    //Métodos de Verificación
    private boolean analizarTipoDeToken(String palabra, boolean crearToken, ListaElementos<Token> tokensIdentificados, int fila, int columna, String caracterSiguiente) {
        TablaDeSimbolos tabla = new TablaDeSimbolos();
        
        if (tabla.obtenerTablaDeSimbolos().containsKey(palabra)) {    
            if (crearToken) {
                tokensIdentificados.agregarALaLista(new Token(tabla.obtenerTablaDeSimbolos().get(palabra), palabra, fila, columna, numeroDeTabuladores, tabulador, caracterSiguiente));
                tabulador = "";
            }
            return true;
        } else {
            return false; 
        }
    }
       
    private boolean analizarConstantes(String palabra, boolean crearToken, boolean crearError, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, int fila, int columna, String caracterSiguiente) {
        
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
                        tokensIdentificados.agregarALaLista(new Token("Constante", palabra, fila, columna, numeroDeTabuladores, tabulador, caracterSiguiente));
                        tabulador = "";
                    }
                } else if (palabra.length() == contador + contadorPunto) {
                    if (crearToken) {
                        tokensIdentificados.agregarALaLista(new Token("Constante", palabra, fila, columna, numeroDeTabuladores, tabulador, caracterSiguiente));
                        tabulador = "";
                    }
                } else {
                    if (crearError) {
                        tokensIdentificados.agregarALaLista(new Token("Error", palabra, fila, columna, numeroDeTabuladores, tabulador, caracterSiguiente));
                        errores.agregarALaLista("Error Númerico en la fila: " + fila + ", columna: " + columna);
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
    private void analizarRestante(String palabra, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, int fila, int columna, String caracterSiguiente) {
        String union = "";
        indice = 0;          
        columnaMovil = columna;
        
        while (indice != palabra.length()) {
            
            if (analizarSigno(palabra, tokensIdentificados, false, fila, columnaMovil, "")) {
                if (!analizarTipoDeToken(union, true, tokensIdentificados, fila, columnaMovil - union.length(), "")) {  
                    if (!analizarConstantes(union, true, true, tokensIdentificados, errores, fila, columnaMovil - union.length(), "")) {
                        union = analizarIdentificadores(union, tokensIdentificados, errores, fila, columnaMovil - union.length(), "");
                    } else {
                        union = "";
                    }
                } else {
                    union = "";
                }
                analizarSigno(palabra, tokensIdentificados, true, fila, columnaMovil, "");
            } else {
                union += palabra.charAt(indice);
            }
            indice++; columnaMovil++;
        }
        
        if (!"".equals(union)) { 
            if (!analizarTipoDeToken(union, true, tokensIdentificados, fila, columnaMovil - union.length(), "")) { 
                if (!analizarConstantes(union, true, true, tokensIdentificados, errores, fila, columnaMovil - union.length(), "")) {
                    union = analizarIdentificadores(union, tokensIdentificados, errores, fila, columnaMovil - union.length(), "");
                }
            }
        }
        try {
            tokensIdentificados.obtenerContenido(tokensIdentificados.getLongitud()).guardarCaracterSiguiente(caracterSiguiente);
        } catch (ListaElementosExcepcion ex) {
            Logger.getLogger(CrearTokens.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean analizarSigno(String palabra, ListaElementos<Token> tokensIdentificados, boolean crearToken, int fila, int columna, String caracterSiguiente) {
        TablaDeSimbolos tabla = new TablaDeSimbolos();
        String cadenaSigno = "" + palabra.charAt(indice);
        
        if (tabla.obtenerTablaDeSimbolos().containsKey("" + palabra.charAt(indice))) {
            if ("+".equals("" + palabra.charAt(indice)) || "-".equals("" + palabra.charAt(indice)) || "*".equals("" + palabra.charAt(indice)) || "/".equals("" + palabra.charAt(indice)) || "=".equals("" + palabra.charAt(indice)) || "<".equals("" + palabra.charAt(indice)) || ">".equals("" + palabra.charAt(indice)) || "!".equals("" + palabra.charAt(indice))) {
                if (indice != palabra.length() - 1) {
                    if ("=".equals("" + palabra.charAt(indice + 1)) || "/".equals("" + palabra.charAt(indice + 1)) || "*".equals("" + palabra.charAt(indice + 1))) {
                        cadenaSigno += palabra.charAt(indice + 1);
                        if (crearToken) {
                            indice++; columnaMovil++;
                        }
                    }
                }
            }
            if (crearToken) {
                analizarTipoDeToken(cadenaSigno, true, tokensIdentificados, fila , columna, caracterSiguiente);
            }
            return true;
        }
        return false;
    }

    private String analizarIdentificadores(String palabra, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, int fila, int columna, String caracterSiguiente) {
        if (palabra.charAt(0) == '0' || palabra.charAt(0) == '1' || palabra.charAt(0) == '2' || palabra.charAt(0) == '3' || palabra.charAt(0) == '4' || palabra.charAt(0) == '5' || palabra.charAt(0) == '6' || palabra.charAt(0) == '7' || palabra.charAt(0) == '8' || palabra.charAt(0) == '9') {
            tokensIdentificados.agregarALaLista(new Token("Error", palabra, fila, columna,  numeroDeTabuladores, tabulador, caracterSiguiente));
            errores.agregarALaLista("Error en el Identificador de la fila: " + fila + ", columna: " + columna);
        } else { 
            tokensIdentificados.agregarALaLista(new Token("Identificador", palabra, fila, columna, numeroDeTabuladores, tabulador, caracterSiguiente));
            tabulador = "";
        }
        return "";
    }
            
    // Método en Caso se encuentre una Cadena 
    public int analizarCadena(int fila, int columna, int columnaAMandar, String palabra, String signo, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, String caracterSiguiente) {
        String union = "";
        int contadorDeComillas = 0;

        while (true) {

            if (columna != (palabra.length())) {
                if (!"\r".equals("" + palabra.charAt(columna))) {

                    if (("" + palabra.charAt(columna)).equals(signo)) {
                        contadorDeComillas++;
                        union += palabra.charAt(columna);
                        
                        if (contadorDeComillas == 2) {
                            tokensIdentificados.agregarALaLista(new Token("Constante", union, fila, columnaAMandar,  numeroDeTabuladores, tabulador, caracterSiguiente));
                            tabulador = "";
                            columna++;
                            finDeLinea = false;
                            break;
                        }
                    } else {
                        union += palabra.charAt(columna);
                    }
                } else {
                    tokensIdentificados.agregarALaLista(new Token("Error", union, fila, columnaAMandar,  numeroDeTabuladores, tabulador, caracterSiguiente));                           
                    errores.agregarALaLista("Error en la Escritura de la Cadena en la fila: " + fila + ", columna: " + columnaAMandar);
                    finDeLinea = true;
                    break;
                }
                columna++;
            } else {
                tokensIdentificados.agregarALaLista(new Token("Error", union, fila, columnaAMandar,  numeroDeTabuladores,  tabulador, caracterSiguiente));                                             
                errores.agregarALaLista("Error en la Escritura de la Cadena en la fila: " + fila + ", columna: " + columnaAMandar);
                finDeLinea = true;
                break;
            }
        }
        return columna;
    }
    
    public boolean saberFinDeLinea() {
        return finDeLinea;
    }
}