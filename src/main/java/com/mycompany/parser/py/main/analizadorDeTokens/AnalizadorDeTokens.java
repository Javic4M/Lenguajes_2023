
package com.mycompany.parser.py.main.analizadorDeTokens;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Asignacion;
import com.mycompany.parser.py.main.tokens.Token;

public class AnalizadorDeTokens {
    
    private ListaElementos<String> erroresSintacticos = new ListaElementos<>();
    private ListaElementos<Asignacion> asignaciones = new ListaElementos<>();
    private ListaElementos<Token> tokensIdentificados;
    private int indice;
    private boolean condicionalIfActiva = false, salir = false, estructuraCompletada = false;

    public AnalizadorDeTokens(ListaElementos<Token> tokensIdentificados) {
        this.tokensIdentificados = tokensIdentificados;
    }
    
    public void analizarListaDeTokens() {
        indice = 1;

        while ((indice != (tokensIdentificados.getLongitud() + 1)) && !salir) {
            if (!verificarAsignacionOMetodo()) {
                if (!verificarDef()) {
                    if (!verificarIf()) {
                        if (!verificarFor()) {
                            if (!verificarWhile()) {
                                if (!veriricarPrint()) {
                                    if (!esUnComentario()) {
                                        try {
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("break")) {
                                                indice++;
                                                System.out.println("Se Encontro un Break");
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("return")) {
                                                boolean pasar = false;
                                                indice++;
                                                    
                                                while (!pasar && !salir) {
                                                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                                                        indice++; estructuraCompletada = true;
                                                        if  (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                                                            indice++;
                                                            
                                                            while (true) {
                                                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                                                    indice++;
                                                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                                                        indice++;
                                                                        System.out.println("Se encontro un return algo(algo)");
                                                                        break;
                                                                    } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico")) && estaEnLaMismaLinea()) {
                                                                        indice++;
                                                                    } else {
                                                                        salir = imprimirError(", se esperaba un signo Aritmetico, ) ó ,");
                                                                        break;
                                                                    }
                                                                } else {
                                                                    salir = imprimirError(", se esperaba un Identificador ó una Constante");
                                                                    break;
                                                                }
                                                            }
                                                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                                                                indice++;
                                                            } else {
                                                                break;
                                                            }
                                                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion")) && estaEnLaMismaLinea()) {
                                                            indice++;
                                                        } else {  
                                                            break;  
                                                        }
                                                    } else {
                                                        salir = imprimirError(", se esperaba un Identificador ó una Constante");
                                                    }
                                                }
                                            } else {
                                                salir = imprimirError(", se esperaba una estructura Reconocible");
                                                break;
                                            }
                                        } catch (ListaElementosExcepcion ex) {
                                            if (!estructuraCompletada) {
                                                System.out.println("Error en el Analizador Central de tipo: " + ex.getMessage());
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
    }
    
    private boolean verificarAsignacionOMetodo() {
        boolean estado = false;
        int indiceIdentificador = 0;
        estructuraCompletada = false;
        
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("var")) {
                indice++;
             }
            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                indiceIdentificador = indice;
                indice++;          
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Asignacion") && estaEnLaMismaLinea()) {
                    indice++;                    
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        ListaElementos<Integer> variables = new ListaElementos<>();
                        variables.agregarALaLista(indice);
                        indice++; estructuraCompletada = true;
                        
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("if") && estaEnLaMismaLinea()) {
                            estado = verificarIfEspeciales();
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico")) && estaEnLaMismaLinea()) {
                            indice++; estructuraCompletada = false;
                            
                            while (true) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                                        indice++;
                                    } else {
                                        break;
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                                    indice++;
                                    estado = analizarAgrupaciones(true, false, ")");
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                                        indice++;
                                    } else {
//                                        estado = analizarOperacionAsignacion(variables, indiceIdentificador);
                                        break;
                                    }
                                }
                            }
//                            while (!estado) {
//                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
//                                    variables.agregarALaLista(indice);
//                                    indice++; estructuraCompletada = true;
//                                    
//                                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
//                                        indice++; estructuraCompletada = false;               
//                                    } else {
//                                        estado = analizarOperacionAsignacion(variables, indiceIdentificador);
//                                    }
//                                } else {
//                                    estado = salir = imprimiError(", se esperaba un Identificador ó una Constante, recuerde no sumar Cadenas");
//                                }
//                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && estaEnLaMismaLinea()) {
                            indice++;
                            boolean segundoIdentificador = true;

                            while(!estado) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++; estructuraCompletada = true;
                                    
                                    if (segundoIdentificador && (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("and") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("or")) && estaEnLaMismaLinea()) {
                                        segundoIdentificador = false;
                                        indice++; estructuraCompletada = false;
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && estaEnLaMismaLinea()) {
                                        segundoIdentificador = true;
                                        indice++; estructuraCompletada = false;
                                    } else {
                                        System.out.println("Se encontro una Asignacion con Comparación");
                                        indice++;
                                        estado = true;
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba un Identificador ó una Constante");
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                            indice++;
                            estado = analizarAgrupaciones(true, false, ")");
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("is") && estaEnLaMismaLinea()) {
                            indice++;
                            
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                System.out.println("Se encontro una Asignacion is");
                                indice++; estructuraCompletada = true;
                                estado = true;
                            } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++;
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    System.out.println("Se encontro una Asignacion is not");
                                    indice++; estructuraCompletada = true;
                                    estado = true;
                                } else {
                                    estado = salir = imprimirError(", se esperaba un Identificador ó una Constante");
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba un Identificador, una Constante, un not, ó un and");
                            }
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not")) && estaEnLaMismaLinea()) {
                            boolean paso = false;

                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not") && estaEnLaMismaLinea()) {
                               indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in") && estaEnLaMismaLinea()) {
                                    indice++;
                                    paso = true;
                                } else {
                                    estado = salir = imprimirError(", se esperaba un in");
                                }
                            } else {
                                indice++;
                                paso = true;
                            }
                            
                            if (paso) {
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("[") && estaEnLaMismaLinea()) {
                                    indice++;
                                    estado = analizarAgrupaciones(true, false, "]");
                                } else {
                                    estado = salir = imprimirError(", se esperaba un [");
                                }
                            }
                        } else {
                            System.out.println("Es una Asignacion");
                            asignaciones.agregarALaLista(new Asignacion(tokensIdentificados.obtenerContenido(indice - 1).obtenerTipoDeEstructura(), tokensIdentificados.obtenerContenido(indice - 3).obtenerLexema(), tokensIdentificados.obtenerContenido(indice - 1).obtenerLexema()));
                            estado = true;
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("[") && estaEnLaMismaLinea()) {
                        indice++;
                        
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && estaEnLaMismaLinea()) {
                            estado = analizarAgrupaciones(false, true, "]");
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && tokensIdentificados.obtenerContenido(indice - 1).obtenerLexema().equals("[") && estaEnLaMismaLinea()) {
                            indice++;
                            boolean segundo = false;

                            while (!estado) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && !segundo && estaEnLaMismaLinea()) {
                                        segundo = true;
                                        indice++;
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && segundo && estaEnLaMismaLinea()) {
                                        segundo = false;
                                        indice++;
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}") && estaEnLaMismaLinea()) {
                                        segundo = false;
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") && estaEnLaMismaLinea()) {
                                            System.out.println("Se encontro una Asignacion = [{n,n,n}]");
                                            indice++;
                                            estado = true;
                                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && estaEnLaMismaLinea()) {
                                                indice++;
                                            } else {
                                                estado = salir = imprimirError(", se esperaba un {");
                                            }
                                        } else {
                                            estado = salir = imprimirError(", se esperaba un ] ó ,");
                                        }
                                    } else {
                                        estado = salir = imprimirError(", se esperaba :, } ó ,");
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba un Identificador ó una Constante");
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") && estaEnLaMismaLinea()) {
                            System.out.println("Se encontro una Asignacion = []");
                            indice++;
                            estado = true;
                        } else {
                            estado = salir = imprimirError(", se esperaba una Constante ó ]");
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                        indice++;
                        while (true) {
                            estado = analizarAgrupaciones(true, false, ")");
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                                    indice++;
                                } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++;
                                } else {
                                    estado = salir = imprimirError(", se esperaba un (");
                                }
                            } else {
                                break;
                            }
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && estaEnLaMismaLinea()) {
                        indice++;
                        boolean segundo = false;
                        
                        while (!estado) {
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                    indice++;
                                    segundo = true;
                                } else if (segundo && tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                    indice++;
                                    segundo = false;
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}") && estaEnLaMismaLinea()) {
                                    System.out.println("Se encontro una Asignacion = {n:n}");
                                    indice++;
                                    estado = true;
                                } else {
                                    estado = salir = imprimirError(", se esperaba un :, } ó ,");
                                }
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}") && estaEnLaMismaLinea()) {
                                System.out.println("Se encontro una Asignacion = {}");
                                indice++;
                                estado = true;
                            } else {
                                estado = salir = imprimirError(", se esperaba un Identificador, una Constante ó }");
                            }
                        }
                    } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{")) && estaEnLaMismaLinea()) {
                        indice++;
                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{")) && estaEnLaMismaLinea()) {
                            indice++;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{")) && estaEnLaMismaLinea()) {
                                indice++;
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{")) && estaEnLaMismaLinea()) {
                                    System.out.println("Se encontro una Asignacion = {}");
                                    indice++;
                                    estado = true;
                                } else {
                                    estado = salir = imprimirError(", se esperaba Constante ó {");
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba un signo de Comparación ó {");
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba una Constante ó {");
                        }
                    } else {
                        estado = salir = imprimirError(", se esperaba un Identificador");
                    }                
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                    indice++;
                    
                    while(!estado) {
                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                System.out.println("Se encontro Metodo");
                                estado = true;
                                indice++;
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                indice++;
                            } else {
                                estado = salir = imprimirError(", se esperaba un ) ó ,");
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                            System.out.println("Se encontro Metodo");
                            estado = true;
                            indice++;
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("[") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(")) && estaEnLaMismaLinea()) {
                            indice++;
                            
                            while (!estado) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                        indice++;
                                    } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) && estaEnLaMismaLinea()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                            System.out.println("Se encontro Metodo[(n,n)]");
                                            estado = true;
                                            indice++;
                                        } else {
                                            estado = salir = imprimirError(", se esperaba un )");
                                        }
                                    } else {
                                        estado = salir = imprimirError(", se esperaba un ] ó ,");
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba un Identificador ó una Constante");
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && estaEnLaMismaLinea()) {
                            indice++;
                            
                            while (!estado) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                        indice++;
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}") && estaEnLaMismaLinea()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                            System.out.println("Se encontro Metodo[{n:n}]");
                                            estado = true;
                                            indice++;
                                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && estaEnLaMismaLinea()) {
                                                indice++;
                                            } else {
                                                estado = salir = imprimirError(", se esperaba un {");
                                            }
                                        } else {
                                            estado = salir = imprimirError(", se esperaba un ) ó una ,");
                                        }
                                    } else {
                                        estado = salir = imprimirError(", se esperaba un } ó :");
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba un Identificador o una Constante");
                                }
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba un Identificador, una Constante, un ), un [ ó un {");
                        }
                    }
                } else {
                    if (estaEnLaMismaLinea()) {
                        estado = analizarAsignacionEspecial();
                    } else {
                        estado = salir = imprimirError(", se esperaba un =");
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {            
            if (!estructuraCompletada) {
                System.out.println("Error al Momento de definir la Asignación");
                estado = salir = true;
            }
        }
        return estado;
    }
    
    private boolean analizarAgrupaciones(boolean identificadorPermitido, boolean puntosPermitidos, String simbolo) {

        try {
            while (true) {
                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                    
                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && identificadorPermitido && estaEnLaMismaLinea()) {
                        indice++;
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && estaEnLaMismaLinea()) {
                        indice++;
                    } else {
                        salir = imprimirError(", se esperaba una Constante");
		        break;
                    }
                    
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) && estaEnLaMismaLinea()) {
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && puntosPermitidos && estaEnLaMismaLinea()) {
                            indice++;
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                            indice++;
                        } else {
                            salir = imprimirError(", se esperaba un :, un signo Aritmetico ó una Coma");
                            break;
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                        indice++;
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(simbolo) && estaEnLaMismaLinea()) {
                        System.out.println("Se encontro una Agrupacion");
                        indice++; estructuraCompletada = true;
                        break;
                    } else {
                        salir = imprimirError(", se esperaba una , ó un " + simbolo);
                        break;
                    }
                } else {
                    salir = imprimirError(", se esperaba un Identificador ó una Constante");
                    break;
                }
            }
        } catch (ListaElementosExcepcion ex) { 
            System.out.println("Error en el Agrupador");
        }
        return true;
    }
    
    private boolean analizarOperacionAsignacionm(ListaElementos<Integer> variables, int indiceIdentificador) {
        int contadorCadena = 0;
            
        try {
            for (int i = 1; i <= variables.getLongitud(); i++) {
                String comparar = tokensIdentificados.obtenerContenido(variables.obtenerContenido(i)).obtenerLexema();
                int contador_1 = 1;

                while (contador_1 <= asignaciones.getLongitud()) {
                    if (comparar.equals(asignaciones.obtenerContenido(contador_1).obtenerNombre())) {
                        if ("Cadena".equals(asignaciones.obtenerContenido(contador_1).obtenerTipoDeAsignacion())) {
                            contadorCadena++;
                        }
                    }
                    contador_1++;
                }
            }

            if (contadorCadena == variables.getLongitud() || contadorCadena == 0) {
                asignaciones.agregarALaLista(new Asignacion("Valor", tokensIdentificados.obtenerContenido(indiceIdentificador).obtenerLexema(), tokensIdentificados.obtenerContenido(indice - 1).obtenerLexema()));
                System.out.println("Se encontro una Asignacion con Operación");
            } else {
                salir = imprimirError(", no se puede sumar cadenas con valores numericos");
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Operador Asignacion");
        }
        return true;
    }
    
    private boolean analizarAsignacionEspecial() {
        estructuraCompletada = false;
        boolean estado = false;
        
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && estaEnLaMismaLinea()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("=") && estaEnLaMismaLinea()) {
                        indice++;
                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                indice++;
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    if(indice != tokensIdentificados.getLongitud()) {
                                        indice++;
                                        if(tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                                            indice++;
                                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                                indice++; estructuraCompletada = true;
                                                System.out.println("Se Encontro una Asignacion Especial con signo");
                                                estado = true;
                                            }
                                        } else {
                                            System.out.println("Se Encontro una Asignacion Especial");
                                            estado = true;
                                        }
                                    } else {
                                        System.out.println("Se Encontro una Asignacion Especial");
                                        indice++;
                                        estado = true;
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba un Identificador");
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba una ,");
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba un Identificador");
                        }
                    } else {
                        estado = salir = imprimirError(", se esperaba un =");
                    }
                } else {
                    estado = salir = imprimirError(", se esperaba un Identificador");
                }
            } else {
                estado = salir = imprimirError(", se esperaba una coma ó un signo de Asignacion");
            }
        } catch (ListaElementosExcepcion ex) {
            if (!estructuraCompletada) {
                System.out.println("Error en el Analizador Especial de tipo: " + ex.getMessage());
                estado = salir = true;
            }
        }
        return estado;
    }
    
    private boolean verificarDef() {
        boolean estado = false;
        estructuraCompletada = false;
        
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("def")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && estaEnLaMismaLinea()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                System.out.println("Se Encontro un def metodo():");
                                indice++; estructuraCompletada = true;
                                estado = true;
                            } else {
                                estado = salir = imprimirError(", se esperaba :");
                            }
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("*")) && estaEnLaMismaLinea()) {
                            boolean paso = false;
                            
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("*") && estaEnLaMismaLinea()) {
                                indice++;
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++;
                                    paso = true;
                                } else {
                                    estado = salir = imprimirError(", se esperaba un Identificador ó una Constante"); // Error
                                }
                            } else {
                                indice++;
                                paso = true;
                            }
                            
                            if (paso) {
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                    indice++;

                                    while(!estado) {
                                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                            indice++; estructuraCompletada = false;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                                indice++; estructuraCompletada = false;
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                                indice++; estructuraCompletada = false;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                                    System.out.println("metodo(Algo,Algo,Algo):");
                                                    indice++; estructuraCompletada = true;
                                                    estado = true;
                                                } else {
                                                    estado = salir = imprimirError(", se esperaba :"); // Error
                                                }
                                            } else {
                                                estado = salir = imprimirError(", se esperaba un )"); // Error
                                            }
                                        } else {
                                            estado = salir = imprimirError(", se esperaba un Identificador ó una Constante"); // Error
                                        }
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                        System.out.println("metodo(Algo):");
                                        indice++; estructuraCompletada = true;
                                        estado = true;
                                    } else {
                                        estado = salir = imprimirError(", se esperaba :"); // Error
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba un )"); // Error
                                }
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba un Identificador, una Constante ó un )"); // Error
                        }
                    } else {
                        estado = salir = imprimirError(", se esperaba un ("); // Error
                    }
                } else {
                    estado = salir = imprimirError(", se esperaba un Identificador"); // Error
                }
            }
        } catch (ListaElementosExcepcion ex) {
            if (!estructuraCompletada) {
                System.out.println("Error en Def de tipo: " + ex.getMessage());
                estado = salir = true;
            }
        }
        return estado;
    }
    
    private boolean verificarIf() {
        estructuraCompletada = false;
        boolean estado = false;
        
        try {         
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("if")) {
                int indiceIf = indice;                
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("True") && estaEnLaMismaLinea()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                        indice++; estructuraCompletada = true;
                        condicionalIfActiva = true;
                        System.out.println("Se encontro condicional if");
                        tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                        estado = true;
                    } else {
                        estado = salir = imprimirError(", se esperaba :"); // Error
                    }
                } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                    indice++;
                    estado = ifComplemento(indiceIf);
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not") && estaEnLaMismaLinea()) {
                    indice++;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        indice++;
                        estado = ifComplemento(indiceIf);
                    } else {
                        estado = salir = imprimirError(", se esperaba un Identicador ó una Constante"); // Error
                    }
                } else {
                    estado = salir = imprimirError(", se esperaba un Identicador, una Constante o un valor Booleano"); // Error
                }
            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("elif")) {
                if (condicionalIfActiva) {
                    indice++;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && estaEnLaMismaLinea()) {
                            indice++;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                    indice++; estructuraCompletada = true;
                                    condicionalIfActiva = true;
                                    System.out.println("Se encontro condicional elif");
                                    estado = true;
                                } else {
                                    estado = salir = imprimirError(", se esperaba un :"); // Error
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba un Identicador ó una Constante"); // Error
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba un signo de Comparación"); // Error
                        }
                    } else {
                        estado = salir = imprimirError(", se esperaba un Identicador ó una Constante"); // Error
                    }
                } else {
                    estado = salir = imprimirError(", No se a abierto ningun if"); // Error
                }
            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else")) {
                if (condicionalIfActiva) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                        indice++; estructuraCompletada = true;
                        condicionalIfActiva = false;
                        System.out.println("Se encontro condicional else");
                        estado = true;
                    } else {
                        estado = salir = imprimirError(", se esperaba :"); // Error
                    }
                } else {
                    estado = salir = imprimirError(", No se a abierto ningun if"); // Error
                }
            }
        } catch (ListaElementosExcepcion ex) {
            if (!estructuraCompletada) {
                System.out.println("Error en el Verificado if de tipo: " + ex.getMessage());
                estado = salir = true;
            }
        }
        return estado;
    }
    
    private boolean verificarIfEspeciales() {
        estructuraCompletada = false;
        boolean estado = false;
        
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("if") && estaEnLaMismaLinea()) {
                indice++;
                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else") && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && estaEnLaMismaLinea()) {
                            tokensIdentificados.obtenerContenido(indice - 6).establecerTipoDeEstructura("Especial");
                            System.out.println("Sentencia normal");
                            indice++; estructuraCompletada = true;
                            estado = true;
                        } else {
                            estado = salir = imprimirError(", se esperaba una Constante"); //Error
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && estaEnLaMismaLinea()) {
                        indice++;
                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else") && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && estaEnLaMismaLinea()) {
                                    tokensIdentificados.obtenerContenido(indice - 6).establecerTipoDeEstructura("Especial");
                                    System.out.println("Sentencia normal");
                                    indice++; estructuraCompletada = true;
                                    estado = true;
                                } else {
                                    estado = salir = imprimirError(", se esperaba una Constante"); //Error
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba un else"); //Error
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba un Identificador ó una Constante"); //Error
                        }
                    } else {
                        verificarIf();
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not") && estaEnLaMismaLinea()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else") && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && estaEnLaMismaLinea()) {
                                tokensIdentificados.obtenerContenido(indice - 7).establecerTipoDeEstructura("Especial");
                                System.out.println("Sentencia con not");
                                indice++; estructuraCompletada = true;
                                estado = true;
                            } else  {
                                estado = salir = imprimirError(", se esperaba una Constante"); //Error
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba una Else"); //Error
                        }
                    } else {
                        estado = salir = imprimirError(", se esperaba un Identificador"); //Error
                    }
                } else {
                    estado = salir = imprimirError(", se esperaba un Not o un Identificador"); //Error
                }
            }
        } catch (ListaElementosExcepcion ex) {
            if (!estructuraCompletada) {
                System.out.println("Error en el Analizador de Sentencias de tipo: " + ex);
                estado = salir = true;
            }
        }
        return estado;
    }
    
    private boolean ifComplemento(int indiceIf) {
        boolean estado = false;
        
        while (!estado) {
            try {
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && estaEnLaMismaLinea()) {
                    indice++; estructuraCompletada = false;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        indice++; estructuraCompletada = false;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                            condicionalIfActiva = true;
                            indice++; estructuraCompletada = true;
                            System.out.println("Se encontro condicional if");
                            tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                            estado = true;
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("and") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("or")) && estaEnLaMismaLinea()) {
                            indice++; estructuraCompletada = false;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++; estructuraCompletada = false;
                            } else {
                                estado = salir = imprimirError(", se esperaba un Identificador o una Constante"); // Error
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba un :"); // Error
                        }
                    } else {
                        estado = salir = imprimirError(", se esperaba un Identicadoro ó una Constante"); // Error
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                    indice++; estructuraCompletada = false;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                        indice++; estructuraCompletada = false;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                            System.out.println("Se Encontro un ciclo if ()");
                            condicionalIfActiva = true;
                            indice++; estructuraCompletada = true;
                            tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                            estado = true;
                        } else {
                            estado = salir = imprimirError(", se esperaba un :"); // Error
                        }
                    } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        indice++; estructuraCompletada = false;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                            indice++; estructuraCompletada = false;
                            
                            while(!estado) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++; estructuraCompletada = false;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                        indice++; estructuraCompletada = false;
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                        indice++; estructuraCompletada = false;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                            System.out.println("if(Algo,Algo,Algo)");
                                            indice++; estructuraCompletada = true;
                                            condicionalIfActiva = true;
                                            tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                                            estado = true;
                                        } else {
                                            estado = salir = imprimirError(", se esperaba un :"); // Error
                                        }
                                    } else {
                                        estado = salir = imprimirError(", se esperaba una , ó )"); // Error
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba un Identificador o una Constante"); // Error
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                            indice++; estructuraCompletada = false;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                System.out.println("if(Algo):");
                                indice++; estructuraCompletada = true;
                                condicionalIfActiva = true;
                                tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                                estado = true;
                            } else {
                                estado = salir = imprimirError(", se esperaba un :"); // Error
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba un ) ó ,"); // Error
                        }                            
                    } else {
                        estado = salir = imprimirError(", se esperaba un Identicador, una Constante ó )"); // Error
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                    indice++; estructuraCompletada = false;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        indice++; estructuraCompletada = false;
                    } else {
                        estado = salir = imprimirError(", se esperaba un Identicador o una Constante"); // Error
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                    indice++; estructuraCompletada = true;
                    condicionalIfActiva = true;
                    System.out.println("Se encontro condicional if");
                    tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                    estado = true;
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in") && estaEnLaMismaLinea()) {
                    indice++; estructuraCompletada = false;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        indice++; estructuraCompletada = false;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                            indice++; estructuraCompletada = true;
                            condicionalIfActiva = true;
                            System.out.println("Se encontro condicional if algo in algo:");
                            tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                            estado = true;
                        } else {
                            estado = salir = imprimirError(", se esperaba un :"); // Error
                        }
                    } else {
                        estado = salir = imprimirError(", se esperaba un Identicador o una Constante"); // Error
                    }
                } else{
                    estado = salir = imprimirError(", se esperaba un signo de asignacion, aritmetico, ( ó :"); // Error
                }
            } catch (ListaElementosExcepcion ex) {
                if (!estructuraCompletada) {
                    System.out.println("Error en el Verificado if de tipo: " + ex.getMessage());
                    estado = salir = true;
                }
            }
        }
        return estado;
    }
    
    private boolean verificarFor() {
        estructuraCompletada = false;
        boolean estado = false;
        
        try {         
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("for")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && estaEnLaMismaLinea()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in") && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && estaEnLaMismaLinea()) { // Hay que verificar que ya haya sido declara la variable
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                System.out.println("Se Encontro un ciclo For");
                                condicionalIfActiva = true;
                                indice++; estructuraCompletada = true;
                                estado = true;
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                        System.out.println("Se Encontro un ciclo For()");
                                        indice++; estructuraCompletada = true;
                                        estado = true;
                                    }
                                } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++;                     
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                        indice++;
                                        
                                        while(!estado) {
                                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                                indice++; estructuraCompletada = false;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                                    indice++; estructuraCompletada = false;
                                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                                    indice++; estructuraCompletada = false;
                                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                                        System.out.println("for metodo(Algo,Algo,Algo):");
                                                        indice++; estructuraCompletada = true;
                                                        estado = true;
                                                    } else {
                                                        estado = salir = imprimirError(", se esperaba :"); // Error
                                                    }
                                                } else {
                                                    estado = salir = imprimirError(", se esperaba , ó )"); // Error
                                                }
                                            } else {
                                                estado = salir = imprimirError(", se esperaba un Identificador o una Constante"); // Error
                                            }
                                        }
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                            System.out.println("for metodo(Algo):");
                                            condicionalIfActiva = true;
                                            indice++; estructuraCompletada = true;
                                            estado =  true;
                                        } else {
                                            estado = salir = imprimirError(", se esperaba :"); // Error
                                        }
                                    } else {
                                        estado = salir = imprimirError(", se esperaba , ó )"); // Error
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba ) , un Identificador ó una Constante"); // Error
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba ( ó :"); // Error
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba un Identificador"); // Error
                        }
                    } else {
                        estado = salir = imprimirError(", se esperaba in"); // Error
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("key") && estaEnLaMismaLinea()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("value") && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in") && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(".") && estaEnLaMismaLinea()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("items") && estaEnLaMismaLinea()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                                                indice++;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                                    indice++;
                                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                                        System.out.println("for key");
                                                        condicionalIfActiva = true;
                                                        indice++; estructuraCompletada = true;
                                                        estado =  true;
                                                    } else {
                                                        estado = salir = imprimirError(", se esperaba :"); // Error
                                                    }
                                                } else {
                                                    estado = salir = imprimirError(", se esperaba )"); // Error
                                                }
                                            } else {
                                                estado = salir = imprimirError(", se esperaba ("); // Error
                                            }
                                        } else {
                                            estado = salir = imprimirError(", se esperaba items"); // Error
                                        }
                                    } else {
                                        estado = salir = imprimirError(", se esperaba un Punto"); // Error
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba un Identificador"); // Error
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba in"); // Error
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba value"); // Error
                        }
                    } else {
                        estado = salir = imprimirError(", se esperaba una Coma"); // Error
                    }
                } else {
                    estado = salir = imprimirError(", se esperaba un Identificador"); // Error
                }
            }
        } catch (ListaElementosExcepcion ex) {
            if (!estructuraCompletada) {
                System.out.println("Error en el Verificado For de tipo: " + ex.getMessage());
                estado = salir = true;
            }
        }
        return estado;
    }

    private boolean verificarWhile() {
        estructuraCompletada = false;
        boolean estado = false;
        
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("while")) {
                indice++;
                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                    indice++;
                    while (!estado) {
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && estaEnLaMismaLinea()) {
                            indice++; estructuraCompletada = false;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++; estructuraCompletada = false;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                    indice++; estructuraCompletada = true;
                                    condicionalIfActiva = true;
                                    System.out.println("Se encontro condicional while comparacion:");
                                    estado = true;
                                } else {
                                    estado = salir = imprimirError(", se esperaba :"); // Error
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba un Identificador ó una Constante"); // Error
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                            indice++; estructuraCompletada = false;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                indice++; estructuraCompletada = false;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                    System.out.println("Se Encontro un ciclo while metodo():");
                                    indice++; estructuraCompletada = true;
                                    condicionalIfActiva = true;
                                    estado = true;
                                } else {
                                    estado = salir = imprimirError(", se esperaba un :");
                                }
                            } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++; estructuraCompletada = false;               
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                    indice++; estructuraCompletada = false;

                                    while(!estado) {
                                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                            indice++; estructuraCompletada = false;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                                indice++; estructuraCompletada = false;
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                                indice++; estructuraCompletada = false;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                                    System.out.println("while metodo(Algo,Algo,Algo)");
                                                    indice++; estructuraCompletada = true;
                                                    condicionalIfActiva = true;
                                                    estado = true;
                                                } else {
                                                    estado = salir = imprimirError(", se esperaba un :");
                                                }
                                            } else {
                                                estado = salir = imprimirError(", se esperaba una , ó )");
                                            }
                                        } else {
                                            estado = salir = imprimirError(", se esperaba un Identificador ó una Constante");
                                        }
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                    indice++; estructuraCompletada = false;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                        System.out.println("while metodo(Algo):");
                                        indice++; estructuraCompletada = true;
                                        condicionalIfActiva = true;
                                        estado = true;
                                    } else {
                                        estado = salir = imprimirError(", se esperaba un :");
                                    }                      
                                } else {
                                    estado = salir = imprimirError(", se esperaba una , ó )");
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba un Identificador, una Constante ó un )"); // Error
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                            indice++; estructuraCompletada = false;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++; estructuraCompletada = false;
                            } else {
                                estado = salir = imprimirError(", se esperaba un Identificador o una Constante"); // Error
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                            indice++; estructuraCompletada = true;
                            condicionalIfActiva = true;
                            System.out.println("Se encontro condicional while identificado:");
                            estado = true;
                        } else {
                            estado = salir = imprimirError(", se esperaba un signo de Comparación, Aritmetico, un ( ó :"); // Error
                        }
                    }
                } else {
                    estado = salir = imprimirError(", se esperaba un Identificador ó una Constante"); // Error
                }
            }
        } catch (ListaElementosExcepcion ex) {
            if (!estructuraCompletada) {
                System.out.println("Error en While de tipo: " + ex.getMessage());
                estado = salir = true;
            }
        }
        return estado;
    }

    private boolean veriricarPrint() {
        estructuraCompletada = false;
        boolean estado = false;
        
        try {         
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("print")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                    indice++;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("key") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("value")) && !tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("f") && estaEnLaMismaLinea()) { // Verificar que ya haya sido declarada
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                            System.out.println("Print(Identificador)");
                            indice++; estructuraCompletada = true;
                            estado = true;
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(".") && estaEnLaMismaLinea()) {
                            indice++;                         
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                            System.out.println("Print(Identificador.Accion())");
                                            indice++; estructuraCompletada = true;
                                            estado = true;
                                        } else {
                                            estado = salir = imprimirError(", se esperaba )"); // Error
                                        }
                                    } else {
                                        estado = salir = imprimirError(", se esperaba un )"); // Error
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba un ("); // Error
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba un Identificador"); // Error
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {      
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                    System.out.println("Print(Identificador())");
                                    indice++; estructuraCompletada = true;
                                    estado = true;
                                } else {
                                    estado = salir = imprimirError(", se esperaba un )"); // Error
                                }
                            } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++;
                                
                                while(!estado) {
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                        indice++; estructuraCompletada = false;
                                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                            indice++; estructuraCompletada = false;
                                        }
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                        indice++; estructuraCompletada = false;
                                        
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                            System.out.println("Print(Identificador(n,v))");
                                            indice++; estructuraCompletada = true;
                                            estado = true;
                                        } else {
                                            estado = salir = imprimirError(", se esperaba )"); // Error
                                        }
                                    } else {
                                        estado = salir = imprimirError(", se esperaba un Identificador, una Constante ó )"); // Error
                                    }
                                }
                            } else {
                                estado = salir = imprimirError(", se esperaba un Identificador, una Constante ó un )"); // Error
                            }
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("+")) && estaEnLaMismaLinea()) {
                            indice++;
                            
                            while (!estado) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("key") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("value")) && !tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("f") && estaEnLaMismaLinea()) { // Verificar que ya haya sido declarada
                                    indice++; estructuraCompletada = false;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                        indice++; estructuraCompletada = false;
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                                        indice++; estructuraCompletada = false;
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                        System.out.println("Print(Identificador,algo,algo)");
                                        indice++; estructuraCompletada = true;
                                        estado = true;
                                    } else {
                                        estado = salir = imprimirError(", se esperaba un (, ), . ó ,"); // Error
                                    }
                                } else {
                                    estado = salir = imprimirError(", se esperaba un Identificador ó una Constante"); // Error
                                }
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba un (, ), . ó ,"); // Error
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("f") && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                System.out.println("Print(f*Constatante)");
                                indice++; estructuraCompletada = true;
                                estado = true;
                            } else {
                                estado = salir = imprimirError(", se esperaba un )"); // Error
                            }
                        } else {
                            estado = salir = imprimirError(", se esperaba una Constante"); // Error
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                        System.out.println("Print(Identificador)");
                        indice++;
                        estado = true;                       
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            if (!estructuraCompletada) {
                System.out.println("Error en While de tipo: " + ex.getMessage());
                estado = salir = true;
            }
        }
        return estado;
    }
    
    private boolean esUnComentario() {
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

    private boolean estaEnLaMismaLinea() {
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerFila() == tokensIdentificados.obtenerContenido(indice - 1).obtenerFila()) {
                return true;
            } else {
                return false;
            }
        } catch (ListaElementosExcepcion ex) {
            return false;
        }
    }
    
    private boolean imprimirError(String mensaje) {
        try {
            erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + mensaje);
        } catch (ListaElementosExcepcion ex) {
            System.out.println("dfdfd");
        }
        return true;
    }
        
    public ListaElementos<String> obtenerErroresSintacticos() {
        return erroresSintacticos;
    }
}