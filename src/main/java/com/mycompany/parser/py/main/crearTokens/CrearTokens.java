
package com.mycompany.parser.py.main.crearTokens;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.tokens.Token;

public class CrearTokens {
    
    // Método en Caso se encuentre un Comentario  
    public int analizarComentarios(int fila, int columna, int columnaAMandar, String palabra, ListaElementos<Token> tokensIdentificados) {
        System.out.println("Se Encontro un Token de Comentario");
        System.out.println("columna:" + columna);
        String cadena = "";
        
        while (true) {
            columna++;

            if ("\n".equals("" + palabra.charAt(columna))) {
                tokensIdentificados.agregarALaLista(new Token("Comentario", cadena, fila, columnaAMandar - 1));
                break;
            } else {
                cadena += palabra.charAt(columna);
            }
        }
        System.out.println("columna: " + columna);
        return columna;
    }
    
    // Método Central de Reconicimiento
    public void analizarCentral(int fila, int columna, String palabra, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores) {
        if (!analizarAritmetica(palabra, true, tokensIdentificados, fila, columna - palabra.length() + 1)) {
            if (!analizarComparacion(palabra, true, tokensIdentificados, fila, columna - palabra.length() + 1)) {
                if (!analizarAsignacion(palabra, true, tokensIdentificados, fila, columna - palabra.length() + 1)) {
                    if (!analizarPalabrasClave(palabra, true, tokensIdentificados, fila, columna - palabra.length() + 1)) {
                        if (!analizarOtro(palabra, true, tokensIdentificados, fila, columna - palabra.length() + 1)) {
                            if (!analizarConstantes(palabra, true, tokensIdentificados, errores, fila, columna - palabra.length() + 1)) {
                                analizarRestantes(palabra, tokensIdentificados, errores, fila, columna - palabra.length() + 1);
                            }
                        }
                    }
                }
            } 
        }
    }
    
    // Cadena de Métodos de Verificación
    private boolean analizarAritmetica(String palabra, boolean crearToken, ListaElementos<Token> tokensIdentificados, int fila, int columna) {
        if ("+".equals(palabra) || "-".equals(palabra) || "**".equals(palabra) || "/".equals(palabra) || "//".equals(palabra) || "%".equals(palabra) || "*".equals(palabra)) {
            if (crearToken) {
                System.out.println("Se Encontro Token Aritmetico");
                tokensIdentificados.agregarALaLista(new Token("Aritmetico", palabra, fila, columna - 1));
            }
        } else {
            return false; 
        }
        return true;
    }
    
    private boolean analizarComparacion(String palabra, boolean crearToken, ListaElementos<Token> tokensIdentificados, int fila, int columna) {       
        if ("==".equals(palabra) || "!=".equals(palabra) || "<=".equals(palabra) || ">=".equals(palabra) || "<".equals(palabra) || ">".equals(palabra)) {
            if (crearToken) {
                System.out.println("Se Encontro Token de Comparacion");
                tokensIdentificados.agregarALaLista(new Token("Comparacion", palabra, fila, columna - 1));
            }
        } else {
            return false; 
        }
        return true;
    }
    
    private boolean analizarAsignacion(String palabra, boolean crearToken, ListaElementos<Token> tokensIdentificados, int fila, int columna) {
        if ("=".equals(palabra) || "+=".equals(palabra) || "-=".equals(palabra) || "*=".equals(palabra) || "/=".equals(palabra)) {
            if (crearToken) {
                System.out.println("Se Encontro Token de Asignación");
                tokensIdentificados.agregarALaLista(new Token("Asignacion", palabra, fila, columna - 1));
            }
        } else {
            return false; 
        }
        return true;
    }
    
    private boolean analizarPalabrasClave(String palabra, boolean crearToken, ListaElementos<Token> tokensIdentificados, int fila, int columna) {
        if ("and".equals(palabra) || "as".equals(palabra) || "assert".equals(palabra) || "break".equals(palabra) || "class".equals(palabra) || "continue".equals(palabra) || "def".equals(palabra) || "del".equals(palabra) || "elif".equals(palabra) || "else".equals(palabra) || "except".equals(palabra) || "finally".equals(palabra) || "for".equals(palabra) || "from".equals(palabra) || "global".equals(palabra) || "if".equals(palabra) || "import".equals(palabra) || "in".equals(palabra) || "is".equals(palabra) || "lambda".equals(palabra) || "None".equals(palabra) || "nonlocal".equals(palabra) || "not".equals(palabra) || "or".equals(palabra) || "pass".equals(palabra) || "raise".equals(palabra) || "return".equals(palabra) || "try".equals(palabra) || "while".equals(palabra) || "with".equals(palabra) || "yield".equals(palabra)) {
            if (crearToken) {
                System.out.println("Se Encontro Token de Palabras Clave");
                tokensIdentificados.agregarALaLista(new Token("Palabras Clave", palabra, fila, columna - 1));
            }
        } else {
            return false; 
        }
        return true;
    }
       
    private boolean analizarOtro(String palabra, boolean crearToken, ListaElementos<Token> tokensIdentificados, int fila, int columna) {       
        if ("(".equals(palabra) || ")".equals(palabra) || "{".equals(palabra) || "}".equals(palabra) || "[".equals(palabra) || "]".equals(palabra)) {
            if (crearToken) {
                tokensIdentificados.agregarALaLista(new Token("Otro", palabra, fila, columna));
                System.out.println("Se Encontro Token de Parantesis");
            }
        } else {
            return false; 
        }
        return true;
    }
        
    private boolean analizarConstantes(String palabra, boolean crearToken, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, int fila, int columna) {
        if ("True".equals(palabra) || "False".equals(palabra)) { 
            if (crearToken) {
                System.out.println("Se Encontro Token Booleano");
                tokensIdentificados.agregarALaLista(new Token("Constante", palabra, fila, columna));
            }
        } else {
            if (palabra.charAt(0) == '0' || palabra.charAt(0) == '1' || palabra.charAt(0) == '2' || palabra.charAt(0) == '3' || palabra.charAt(0) == '4' || palabra.charAt(0) == '5' || palabra.charAt(0) == '6' || palabra.charAt(0) == '7' || palabra.charAt(0) == '8' || palabra.charAt(0) == '9') {
                int contador = 0, contadorPunto = 0;

                for (int indice = 0; indice < palabra.length(); indice++) {
                    if (palabra.charAt(indice) == '0' || palabra.charAt(indice) == '1' || palabra.charAt(indice) == '2' || palabra.charAt(indice) == '3' || palabra.charAt(indice) == '4' || palabra.charAt(indice) == '5' || palabra.charAt(indice) == '6' || palabra.charAt(indice) == '7' || palabra.charAt(indice) == '8' || palabra.charAt(indice) == '9') {
                        contador++;
                    } else {
                        if (palabra.charAt(indice) == '.') {
                            contadorPunto++;

                            for (int indice_2 = indice; indice_2 < palabra.split("").length; indice_2++) {
                                if (palabra.charAt(indice) == '0' || palabra.charAt(indice) == '1' || palabra.charAt(indice) == '2' || palabra.charAt(indice) == '3' || palabra.charAt(indice) == '4' || palabra.charAt(indice) == '5' || palabra.charAt(indice) == '6' || palabra.charAt(indice) == '7' || palabra.charAt(indice) == '8' || palabra.charAt(indice) == '9') {
                                    contador++;
                                }
                            }
                        }
                    }
                }

                if (palabra.split("").length == contador) {
                    if (crearToken) {
                        System.out.println("Se Encontro un Entero");
                        tokensIdentificados.agregarALaLista(new Token("Constante", palabra, fila, columna));
                    }
                } else if (palabra.split("").length == contador + contadorPunto) {
                    if (crearToken) {
                        System.out.println("Se Encontro un Decimal");
                        tokensIdentificados.agregarALaLista(new Token("Constante", palabra, fila, columna));
                    }
                } else {
                    if (crearToken) {
                        errores.agregarALaLista("Error Númerico");
                    }
                    return true;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    // Analiza Palabras Restantes
    private void analizarRestantes(String palabra, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, int fila, int columna) {
        String union = "";
        
        for (int i = 0; i < palabra.length(); i++) {
            union += palabra.charAt(i);
            
            if (!analizarAritmetica("" + palabra.charAt(i), false, tokensIdentificados, 1, 1)) {
                if (!analizarComparacion("" + palabra.charAt(i), false, tokensIdentificados, 1, 1)) {
                    if (!analizarAsignacion("" + palabra.charAt(i), false, tokensIdentificados, 1, 1)) {
                        if (!analizarPalabrasClave("" + palabra.charAt(i), false, tokensIdentificados, 1, 1)) {
                            if (!analizarOtro("" + palabra.charAt(i), false, tokensIdentificados, 1, 1)) {
                                // Nada
                            } else {
                                union = analizarIdentificadores(union, tokensIdentificados, errores, fila, columna);
                                analizarOtro("" + palabra.charAt(i), true, tokensIdentificados, fila, columna);
                            }
                        } else {
                            union = analizarIdentificadores(union, tokensIdentificados, errores, fila, columna);
                            analizarPalabrasClave("" + palabra.charAt(i), true, tokensIdentificados, fila, columna);
                        }
                    } else {                       
                        union = analizarIdentificadores(union, tokensIdentificados, errores, fila, columna);
                        analizarAsignacion("" + palabra.charAt(i), true, tokensIdentificados, fila, columna);
                    }
                } else {
                    union = analizarIdentificadores(union, tokensIdentificados, errores, fila, columna);
                    analizarComparacion("" + palabra.charAt(i), true, tokensIdentificados, fila, columna);
                }
            } else {
                union = analizarIdentificadores(union, tokensIdentificados, errores, fila, columna);
                analizarAritmetica("" + palabra.charAt(i), true, tokensIdentificados, fila, columna);
            }
        }
        
        if (!"".equals(union)) {
            analizarIdentificadores(union, tokensIdentificados, errores, fila, columna);
        }
    }

    private String analizarIdentificadores(String union, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores, int fila, int columna) {
        if (!analizarConstantes(union, true, tokensIdentificados, errores, fila, columna)) {
            if (union.charAt(0) == '0' || union.charAt(0) == '1' || union.charAt(0) == '2' || union.charAt(0) == '3' || union.charAt(0) == '4' || union.charAt(0) == '5' || union.charAt(0) == '6' || union.charAt(0) == '7' || union.charAt(0) == '8' || union.charAt(0) == '9') {
                errores.agregarALaLista("Error, un Identificado no puede empezar con un Número");
            } else {
                System.out.println("Se Encontro Identificador");
                tokensIdentificados.agregarALaLista(new Token("Identificador", union, fila, columna));
            }
        }
        return "";
    }
            
    // Método Extra
    public int analizarCadena(int fila, int columna, int columnaAMandar, String palabra, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores) {
        String union = "";
        int contadorDeComillas = 0;

        while (true) {

            if (!"\n".equals("" + palabra.charAt(columna))) {

                if (palabra.charAt(columna) == '"') {
                    contadorDeComillas++;
                    
                    if (contadorDeComillas == 2) {
                        System.out.println("Se Encontro una Cadena");
                        tokensIdentificados.agregarALaLista(new Token("Cadena",'"' + union + '"', fila, columnaAMandar));
                        columna++;
                        break;
                    }
                } else {
                    union += palabra.charAt(columna);
                }
            } else {
                errores.agregarALaLista("Error en la Escritura de la Cadena");
                break;
            }
            columna++;
        }
        return columna;
    }
}
