
package com.mycompany.parser.py.main.analizadorDeTokens;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;

public class AnalizadorDeTokens {
    
    private ListaElementos<String> erroresSintacticos = new ListaElementos<>();
    private ListaElementos<Token> tokensIdentificados;
    private int indice;
    private boolean condicionalIfActiva = false, salir = false;
    
    public AnalizadorDeTokens(ListaElementos<Token> tokensIdentificados) {
        this.tokensIdentificados = tokensIdentificados;
    }
    
    public void analizarListaDeTokens() {
        indice = 1;
        
        while ((indice != (tokensIdentificados.getLongitud() + 1)) && !salir) {
            if (!verificarAsignacionOMetodo()) {
                if (!verificarDef()) {
                    if (!verificarBloqueIf()) {
                        if (!verificarBloqueFor()) {
                            if (!verificarWhile()) {
                                if (!analizarPrint()) {
                                    if (!esUnComentario()) {
                                        try {
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("break")) {
                                                indice++;
                                                System.out.println("Se Encontro un Break");
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("return")) {
                                                indice++;
                                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                    indice++;
                                                    while (true) {
                                                        if ((indice != tokensIdentificados.getLongitud() + 1) && tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                            indice++;
                                                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                                indice++;
                                                            } else {
                                                                erroresSintacticos.agregarALaLista("Error en la fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identificador o una Constante");
                                                                break;
                                                            }
                                                        } else {
                                                            System.out.println("Se Encontro un return");
                                                            break;
                                                        }
                                                    }                                                   
                                                } else {
                                                    //ERROR
                                                }
                                            } else {
                                                erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice).obtenerFila() + ", estructura no Reconocida");
                                                break;
                                            }
                                        } catch (ListaElementosExcepcion ex) {
                                            System.out.println("Error en el Analizador Central de tipo: " + ex.getMessage());
                                            erroresSintacticos.agregarALaLista("Error");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean verificarAsignacionOMetodo() {
        boolean estado = false;
        
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("var")) {
                indice++;
            }
            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                indice++;            
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Asignacion") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                    indice++;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;                        
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("if") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            estado = verificarIfEspeciales();
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;

                            while (true) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                    } else {
                                        System.out.println("Se encontro una Asignacion con Operación");
                                        indice++;
                                        estado = true;
                                        break;
                                    }
                                } else {
                                    erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identificador o una Constante");
                                    estado = true;
                                    break;
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            
                            while(true) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("and") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("or")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                indice++;
                                            } else {
                                                System.out.println("Se encontro una Asignacion con Comparación");
                                                indice++;
                                                estado = true;
                                                break;
                                            }
                                        }
                                    } else {
                                        System.out.println("Se encontro una Asignacion con Comparación");
                                        indice++;
                                        estado = true;
                                        break;
                                    }
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            
                            while(true) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        System.out.println("Se encontro una Asignacion(n, n)");
                                        indice++;
                                        estado = true;
                                        break;
                                    }
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("is") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                System.out.println("Se encontro una Asignacion is");
                                indice++;
                                estado = true;
                            } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    System.out.println("Se encontro una Asignacion is not");
                                    indice++;
                                    estado = true;
                                }
                            }
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            boolean paso = false;
                            
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not")) {
                               indice++;
                               if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in")) {
                                   indice++;
                                   paso = true;
                               }
                            } else {
                                indice++;
                                paso = true;
                            }
                            if (paso) {
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("[") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;

                                    while (true) {
                                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                            if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                indice++;
                                            } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                System.out.println("Se encontro una Asignacion in");
                                                indice++;
                                                estado = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            System.out.println("Es una Asignacion");
                            estado = true;
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("[") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;

                        while(true) {
                            if  (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        System.out.println("Se encontro un Arreglo");
                                        indice++;
                                        estado = true;
                                        break;
                                    } else {
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                        }
                                    }
                                } else {
                                    erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un ] , } ó ,");
                                    estado = true;
                                    salir = true;
                                    break;
                                }
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && tokensIdentificados.obtenerContenido(indice - 1).obtenerLexema().equals("[") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                boolean salir = false;
                                
                                while (true) {
                                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                        }else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                System.out.println("Se encontro una Asignacion = [{n,n,n}]");
                                                indice++;
                                                estado = true;
                                                salir = true;
                                                break;
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                indice++;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                    indice++;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (salir) {
                                    break;
                                }
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                System.out.println("Se encontro una Asignacion = {}");
                                indice++;
                                estado = true;
                                break;
                            } else {
                                erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba una Constante");
                                estado = true;
                                salir = true;
                                break;
                            }
                        }
                        if (indice == tokensIdentificados.getLongitud()) {
                            indice++;
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        boolean segundo = false;
                        
                        while (true) {
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    segundo = true;
                                } else if (segundo && tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    segundo = false;
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    System.out.println("Se encontro una Asignacion = {n:n}");
                                    indice++;
                                    estado = true;
                                    break;
                                }
                            }
                        }
                    } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    System.out.println("Se encontro una Asignacion = {}");
                                    indice++;
                                    estado = true;
                                }
                            }
                        }
                    } else {
                        erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identificador , [ ó {");
                        estado = true;
                        salir = true;
                    }                
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                    indice++;
                    
                    while(true) {
                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                System.out.println("Se encontro Metodo");
                                estado = true;
                                indice++;
                                break;
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                            } else {
                                erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba una , ó ) )");
                                estado = true;
                                break;
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            System.out.println("Se encontro Metodo");
                            estado = true;
                            indice++;
                            break;
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("[") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            
                            while (true) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                    } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            System.out.println("Se encontro Metodo[(n,n)]");
                                            estado = true;
                                            indice++;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (estado) {
                                break;
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            
                            while (true) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            System.out.println("Se encontro Metodo[{n:n}]");
                                            estado = true;
                                            indice++;
                                            break;
                                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                indice++;
                                            }
                                        }
                                    }
                                }
                            }
                            if (estado) {
                                break;
                            }
                        } else {
                            erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un )");
                            estado = true;
                            break;
                        }
                    }
                } else {
                    if (tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice).obtenerFila()) {
                        estado = analizarAsignacionEspecial();
                    } else {
                        erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un =");
                        estado = true;
                        salir = true;
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error al Momento de definir la Asignación");
            estado = true;
            salir = true;
        }
        return estado;
    }
    
    private boolean verificarIfEspeciales() {
       
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("if") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            tokensIdentificados.obtenerContenido(indice - 6).establecerTipoDeEstructura("Especial");
                            System.out.println("Sentencia normal");
                            indice++;
                            return true;
                        } else {
                            erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba una Constante");
                            salir = true;
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    tokensIdentificados.obtenerContenido(indice - 6).establecerTipoDeEstructura("Especial");
                                    System.out.println("Sentencia normal");
                                    indice++;
                                    return true;
                                } else {
                                    erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba una Constante");
                                    salir = true;
                                }
                            }
                        }
                    } else {
                        verificarBloqueIf();
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                tokensIdentificados.obtenerContenido(indice - 7).establecerTipoDeEstructura("Especial");
                                System.out.println("Sentencia con not");
                                indice++;
                                return true;
                            } else  {
                                erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba una Constante");
                                salir = true;
                            }
                        } else {
                            erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un else");
                            salir = true;
                        }
                    } else {
                        erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identicador");
                        salir = true;
                    }
                } else {
                    erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Not o un Identificador}");
                    salir = true;
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizador de Sentencias de tipo: " + ex);
            salir = true;
            return true;
        }
        return false;
    }
    
    private boolean analizarAsignacionEspecial() {
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("=") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    if(indice != tokensIdentificados.getLongitud()) {
                                        indice++;
                                        if(tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("+") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("-") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("/") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("*") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                indice++;
                                                System.out.println("Se Encontro una Asignacion Especial con signo");
                                                return true;
                                            }
                                        } else {
                                            System.out.println("Se Encontro una Asignacion Especial");
                                            return true;
                                        }
                                    } else {
                                        System.out.println("Se Encontro una Asignacion Especial");
                                        indice++;
                                        return true;
                                    }
                                } else {
                                    errorAsignacionEspecial("Identificador");
                                }
                            } else {
                                errorAsignacionEspecial(",");
                            }
                        } else {
                            errorAsignacionEspecial("Identificador");
                        }
                    } else {
                        errorAsignacionEspecial("=");
                    }
                } else {
                    errorAsignacionEspecial("Identificador");
                }
            } else {
                errorAsignacionEspecial(", ó un signo de Asignacion");
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizador Especial de tipo: " + ex.getMessage());
            salir = true;
            return true;
        }
        return false;
    }
    
    private boolean errorAsignacionEspecial(String mensaje) {
        try {
            erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice).obtenerFila() + ", se esperaba un " + mensaje);
        } catch (ListaElementosExcepcion ex) {
            System.out.println("dfdfd");
        }
        salir = true;
        return true;
    }
    
    public boolean esUnComentario() {
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comentario")) {
                indice++;
                System.out.println("Se encontro Comentario");
                return true;
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizado de Comentario de tipo: " + ex.getMessage());
        }
        return false;
    }
    
    public boolean verificarBloqueIf() {
        try {         
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("if")) {
                int indiceIf = indice;
                
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("True") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        condicionalIfActiva = true;
                        System.out.println("Se encontro condicional if");
                        tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                        return true;
                    } else {
                        erroresSintacticos.agregarALaLista("Erro fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ",, se esperaba :");
                        salir = true;
                    }
                } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                    indice++;
                    while (true) {
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++; //Aqui puede esta otro signo igual
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    condicionalIfActiva = true;
                                    System.out.println("Se encontro condicional if");
                                    tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                                    return true;
                                } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("and") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("or")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                    }
                                } else {
                                    erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba :");
                                    salir = true;
                                    break;
                                }
                            } else {
                                erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identicadoro ó una Constante");
                                salir = true;
                                break;
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    System.out.println("Se Encontro un ciclo if ()");
                                    indice++;
                                    condicionalIfActiva = true;
                                    tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                                    return true;
                                }
                            } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;                     
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;

                                    while(true) {
                                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                indice++;
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                indice++;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                    System.out.println("if(Algo,Algo,Algo)");
                                                    indice++;
                                                    condicionalIfActiva = true;
                                                    tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        System.out.println("if(Algo):");
                                        indice++;
                                        condicionalIfActiva = true;
                                        tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                                        return true;
                                    } else {
                                        erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba :");
                                        salir = true;
                                        break;
                                    }                            
                                } else {
                                    erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un ) ó ,");
                                    salir = true;
                                    break;
                                }
                            } else {
                                erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identicador, una Constante ó )");
                                salir = true;
                                break;
                            }
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("+") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("-") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("*") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("/") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("%")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                            } else {
                                erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identicador o una Constante");
                                salir = true;
                                break;
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            condicionalIfActiva = true;
                            System.out.println("Se encontro condicional if");
                            tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                            return true;
                        } else{
                            erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un signo de asignacion, aritmetico, ( ó :");
                            salir = true;
                            break;
                        }
                    }
                } else {
                    erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identicador, Constante o un valor Booleano");
                    salir = true;
                }
            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("elif")) {
                if (condicionalIfActiva) {
                    indice++;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    condicionalIfActiva = true;
                                    System.out.println("Se encontro condicional elif");
                                    return true;
                                } else {
                                    erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un :");
                                    salir = true;
                                }
                            } else {
                                erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identicador ó una Constante");
                                salir = true;
                            }
                        } else {
                            erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un signo de Comparación");
                            salir = true;
                        }
                    } else {
                        erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identicador ó una Constante");
                        salir = true;
                    }
                } else {
                    System.out.println("Error al escribir elif no se a abierto ningun if");
                    
                    while (true) {
                        indice++;
                        
                        if (indice != tokensIdentificados.getLongitud()) {
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                indice++;
                                break;
                            } else if (indice == tokensIdentificados.getLongitud()) {
                                indice++;
                                break;
                            }
                        } else {
                            indice++;
                            break;
                        }
                    }
                }
            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else")) {
                if (condicionalIfActiva) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        condicionalIfActiva = false;
                        System.out.println("Se encontro condicional else");
                        return true;
                    } else {
                        erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba :");
                        salir = true;
                    }
                } else {
                    erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", No se a abierto ningun if");
                    
                    while (true) {
                        indice++;
                        
                        if (indice != tokensIdentificados.getLongitud()) {
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                indice++;
                                break;
                            } else if (indice == tokensIdentificados.getLongitud()) {
                                indice++;
                                break;
                            }
                        } else {
                            indice++;
                            break;
                        }
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Verificado if de tipo: " + ex.getMessage());
            salir = true;
            return true;
        }
        return false;
    }
    
    private boolean verificarBloqueFor() {
        try {         
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("for")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) { // Hay que verificar que ya haya sido declara la variable
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                System.out.println("Se Encontro un ciclo For");
                                indice++;
                                return true;
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        System.out.println("Se Encontro un ciclo For()");
                                        indice++;
                                        return true;
                                    }
                                } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    indice++;                     
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                        
                                        while(true) {
                                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                indice++;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                    indice++;
                                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                    indice++;
                                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                        System.out.println("for metodo(Algo,Algo,Algo):");
                                                        indice++;
                                                        return true;
                                                    } else {
                                                        erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba :");
                                                        salir = true;
                                                        break;
                                                    }
                                                }
                                            } else {
                                                erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identificador o una Constante");
                                                salir = true;
                                                break;
                                            }
                                        }
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            System.out.println("for metodo(Algo):");
                                            condicionalIfActiva = true;
                                            indice++;
                                            return true;
                                        } else {
                                            erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba :");
                                            salir = true;
                                        }
                                    } else {
                                        erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba , ó )");
                                        salir = true;
                                    }
                                } else {
                                    erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba ) , un Identificador ó una Constante");
                                    salir = true;
                                }
                            } else {
                                erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba ( ó :");
                                salir = true;
                            }
                        } else {
                            erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identificador");
                            salir = true;
                        }
                    } else {
                        erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + ", se esperaba un Identificador");
                        salir = true;
                    }
                } else {
                    System.out.println("ERROR EN EL FOR");
                    salir = true;
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Verificado For de tipo: " + ex.getMessage());
            salir = true;
            return true;
        }
        
        if (salir) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean analizarPrint() {
        try {         
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("print")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) { // Verificar que ya haya sido declarada
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                            System.out.println("Print(Identificador)");
                            indice++;
                            return true;
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(".")) {
                            indice++;                         
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(")) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                            System.out.println("Print(Identificador.Accion)");
                                            indice++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(")) {      
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                    System.out.println("Print(Identificador())");
                                    indice++;
                                    return true;
                                }
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                indice++;
                                
                                while(true) {
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",")) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                            indice++;
                                        }
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                        indice++;
                                        
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                            System.out.println("Print(Identificador(n,v))");
                                            indice++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else {                           
                            while(true) {
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",")) {
                                    indice++;

                                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                        indice++;
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico")) {
                                    indice++;

                                    while(true) {
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico")) {
                                                indice++;
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                                break;
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                    System.out.println("Print(Identificador,algo,algo)");
                                    indice++;
                                    return true;
                                }
                            }
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                        System.out.println("Print(Identificador)");
                        indice++;
                        return true;                       
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Verificado For de tipo: " + ex.getMessage());
        }
        return false;
    }
    
    public boolean verificarDef() {
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("def")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                System.out.println("Se Encontro un def metodo():");
                                indice++;
                                return true;
                            } else {
                                System.out.println("ERROR_DEF");
                                salir = true;
                            }
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;

                                while(true) {
                                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                System.out.println("metodo(Algo,Algo,Algo):");
                                                indice++;
                                                return true;
                                            } else {
                                                System.out.println("ERROR_DEF");
                                                salir = true;
                                                break;
                                            }
                                        } else {
                                            System.out.println("ERROR_DEF");
                                            salir = true;
                                            break;
                                        }
                                    } else {
                                        System.out.println("ERROR_DEF");
                                        salir = true;
                                        break;
                                    }
                                }
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                    System.out.println("metodo(Algo):");
                                    indice++;
                                    return true;
                                } else {
                                    System.out.println("ERROR_DEF");
                                    salir = true;
                                }
                            } else {
                                System.out.println("ERROR_DEF");
                                salir = true;
                            }
                        } else {
                            System.out.println("ERROR_DEF");
                            salir = true;
                        }
                    } else {
                        System.out.println("ERROR_DEF");
                        salir = true;
                    }
                } else {
                    System.out.println("ERROR_DEF");
                    salir = true;
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en Def de tipo: " + ex.getMessage());
            salir = true;
            return true;
        }
        return false;
    }
    
    public boolean verificarWhile() {
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("while")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                    indice++;
                    while (true) {
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(">") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("<") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(">=") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("<=") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("==") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("!=")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                indice++; //Aqui puede esta otro signo igual
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                    indice++;
                                    condicionalIfActiva = true;
                                    System.out.println("Se encontro condicional while comparacion:");
                                    return true;
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                    System.out.println("Se Encontro un ciclo while metodo():");
                                    indice++;
                                    condicionalIfActiva = true;
                                    return true;
                                }
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                indice++;                     
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",")) {
                                    indice++;

                                    while(true) {
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",")) {
                                                indice++;
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                                indice++;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                                    System.out.println("while metodo(Algo,Algo,Algo)");
                                                    indice++;
                                                    condicionalIfActiva = true;
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                        System.out.println("while metodo(Algo):");
                                        indice++;
                                        condicionalIfActiva = true;
                                        return true;
                                    }                               
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("+") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("-") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("*") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("/") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("%")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                indice++;
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                            indice++;
                            condicionalIfActiva = true;
                            System.out.println("Se encontro condicional while identificado:");
                            return true;
                        }
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en While de tipo: " + ex.getMessage());
        }
        return false;
    }
    
    public ListaElementos<String> obtenerErroresSintacticos() {
        return erroresSintacticos;
    }
}
