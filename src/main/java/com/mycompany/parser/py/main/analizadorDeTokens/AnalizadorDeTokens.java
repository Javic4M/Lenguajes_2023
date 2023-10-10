
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
                                                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                                                    indice++;
                                                                } else {
                                                                    salir = imprimiError(", se esperaba una Constante ó )");
                                                                    break;
                                                                }
                                                            } else {
                                                                salir = imprimiError(", se esperaba un Identificador ó una Constante");
                                                                break;
                                                            }
                                                        }
                                                    } else {  
                                                        while (true) {
                                                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion")) && estaEnLaMismaLinea()) {
                                                                indice++;
                                                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                                                    indice++;
                                                                } else {
                                                                    salir = imprimiError(", se esperaba un Identificador ó una Constante");
                                                                    break;
                                                                }
                                                            } else {
                                                                System.out.println("Se Encontro un return");
                                                                break;
                                                            }
                                                        }    
                                                    }
                                                } else {
                                                    salir = imprimiError(", se esperaba un Identificador ó una Constante");
                                                }
                                            } else {
                                                salir = imprimiError(", se esperaba una estructura Reconocible");
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
    
    public boolean verificarAsignacionOMetodo() {
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
                                    variables.agregarALaLista(indice);
                                    indice++; estructuraCompletada = true;
                                    
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                                        indice++; estructuraCompletada = false;               
                                    } else {
                                        analizarOperacionAsignacion(variables, indiceIdentificador);
                                        estado = true;
                                        break;
                                    }
                                } else {
                                    estado = salir = imprimiError(", se esperaba un Identificador ó una Constante, recuerde no sumar Cadenas");
                                    break;
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && estaEnLaMismaLinea()) {
                            indice++;
                            boolean segundoIdentificador = true;

                            while(true) {
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
                                        break;
                                    }
                                } else {
                                    estado = salir = imprimiError(", se esperaba un Identificador ó una Constante");
                                    break;
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                            indice++;
                            analizarAgrupaciones(true, false, ")");
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
                                    estado = salir = imprimiError(", se esperaba un Identificador ó una Constante");
                                }
                            } else {
                                estado = salir = imprimiError(", se esperaba un Identificador, una Constante, un not, ó un and");
                            }
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not")) && estaEnLaMismaLinea()) {
                            boolean paso = false;

                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not") && estaEnLaMismaLinea()) {
                               indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in") && estaEnLaMismaLinea()) {
                                    indice++;
                                    paso = true;
                                }
                            } else {
                                indice++;
                                paso = true;
                            }
                            
                            if (paso) {
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("[") && estaEnLaMismaLinea()) {
                                    indice++;
                                    analizarAgrupaciones(true, false, "]");
                                } else {
                                    estado = salir = imprimiError(", se esperaba un [");
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
                            analizarAgrupaciones(false, true, "]");
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && tokensIdentificados.obtenerContenido(indice - 1).obtenerLexema().equals("[") && estaEnLaMismaLinea()) {
                            indice++;
                            boolean segundo = false;

                            while (true) {
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
                                            break;
                                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && estaEnLaMismaLinea()) {
                                                indice++;
                                            } else {
                                                estado = salir = imprimiError(", se esperaba un {");
                                            }
                                        } else {
                                            estado = salir = imprimiError(", se esperaba un ] ó ,");
                                            break;
                                        }
                                    } else {
                                        estado = salir = imprimiError(", se esperaba :, } ó ,");
                                        break;
                                    }
                                } else {
                                    estado = salir = imprimiError(", se esperaba un Identificador ó una Constante");
                                    break;
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") && estaEnLaMismaLinea()) {
                            System.out.println("Se encontro una Asignacion = []");
                            indice++;
                            estado = true;
                        } else {
                            estado = salir = imprimiError(", se esperaba una Constante ó ]");
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && estaEnLaMismaLinea()) {
                        indice++;
                        boolean segundo = false;
                        
                        while (true) {
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
                                    break;
                                } else {
                                    estado = salir = imprimiError(", se esperaba un :, } ó ,");
                                    break;
                                }
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}") && estaEnLaMismaLinea()) {
                                System.out.println("Se encontro una Asignacion = {}");
                                indice++;
                                estado = true;
                                break;
                            } else {
                                estado = salir = imprimiError(", se esperaba un Identificador, una Constante ó }");
                                break;
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
                                    estado = salir = imprimiError(", se esperaba Constante ó {");
                                }
                            } else {
                                estado = salir = imprimiError(", se esperaba un signo de Comparación ó {");
                            }
                        } else {
                            estado = salir = imprimiError(", se esperaba una Constante ó {");
                        }
                    } else {
                        estado = salir = imprimiError(", se esperaba un Identificador");
                    }                
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                    indice++;
                    
                    while(true) {
                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                System.out.println("Se encontro Metodo");
                                estado = true;
                                indice++;
                                break;
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                indice++;
                            } else {
                                estado = salir = imprimiError(", se esperaba un ) ó ,");
                                break;
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                            System.out.println("Se encontro Metodo");
                            estado = true;
                            indice++;
                            break;
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("[") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(")) && estaEnLaMismaLinea()) {
                            indice++;
                            
                            while (true) {
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
                                            break;
                                        } else {
                                            estado = salir = imprimiError(", se esperaba un )");
                                            break;
                                        }
                                    } else {
                                        estado = salir = imprimiError(", se esperaba un ] ó ,");
                                        break;
                                    }
                                } else {
                                    estado = salir = imprimiError(", se esperaba un Identificador ó una Constante");
                                    break;
                                }
                            }
                            if (estado) {
                                break;
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && estaEnLaMismaLinea()) {
                            indice++;
                            
                            while (true) {
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
                                            break;
                                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{") && estaEnLaMismaLinea()) {
                                                indice++;
                                            } else {
                                                estado = salir = imprimiError(", se esperaba un {");
                                                break;
                                            }
                                        } else {
                                            estado = salir = imprimiError(", se esperaba un ) ó una ,");
                                            break;
                                        }
                                    } else {
                                        estado = salir = imprimiError(", se esperaba un } ó :");
                                        break;
                                    }
                                } else {
                                    estado = salir = imprimiError(", se esperaba un Identificador o una Constante");
                                    break;
                                }
                            }
                            if (estado) {
                                break;
                            }
                        } else {
                            estado = salir = imprimiError(", se esperaba un Identificador, una Constante, un ), un [ ó un {");
                            break;
                        }
                    }
                } else {
                    if (estaEnLaMismaLinea()) {
                        estado = analizarAsignacionEspecial();
                    } else {
                        estado = salir = imprimiError(", se esperaba un =");
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {            
            if (!estructuraCompletada) {
                System.out.println("Error al Momento de definir la Asignación");
            }
            estado = true;
            salir = true;
        }
        return estado;
    }
    
    public boolean analizarAgrupaciones(boolean identificadorPermitido, boolean puntosPermitidos, String simbolo) {
        boolean paso = true;

        try {
            while (true) {
                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && identificadorPermitido && estaEnLaMismaLinea()) {
                        indice++;
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && estaEnLaMismaLinea()) {
                        indice++;
                    } else {
                        salir = imprimiError(", se esperaba una Constante");
		        break;
                    }
                    
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) && estaEnLaMismaLinea() && estaEnLaMismaLinea()) {
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && puntosPermitidos && estaEnLaMismaLinea()) {
                            indice++;
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                            indice++;
                        } else {
                            salir = imprimiError(", se esperaba : ó ,----------");
                            break;
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(simbolo) && estaEnLaMismaLinea()) {
                        System.out.println("Se encontro una Agrupacino");
                        indice++; estructuraCompletada = true;
                        break;
                    } else {
                        salir = imprimiError(", se esperaba una , ó un " + simbolo);
                        break;
                    }
                } else {
                    salir = imprimiError(", se esperaba un Identificador ó una Constante");
                    break;
                }
            }
        } catch (ListaElementosExcepcion ex) { 
            System.out.println("Error en el Agrupador");
        }
        return true;
    }
    
    public boolean analizarOperacionAsignacion(ListaElementos<Integer> variables, int indiceIdentificador) {
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
                salir = imprimiError(", no se puede sumar cadenas con valores numericos");
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Operador Asignacion");
        }
        return true;
    }
    
    private boolean verificarIfEspeciales() {
       
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
                            indice++;
                            return true;
                        } else {
                            salir = imprimiError(", se esperaba una Constante"); //Error
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
                                    indice++;
                                    return true;
                                } else {
                                    salir = imprimiError(", se esperaba una Constante"); //Error
                                }
                            }
                        }
                    } else {
                        verificarBloqueIf();
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
                                indice++;
                                return true;
                            } else  {
                                salir = imprimiError(", se esperaba una Constante"); //Error
                            }
                        } else {
                            salir = imprimiError(", se esperaba una Else"); //Error
                        }
                    } else {
                        salir = imprimiError(", se esperaba un Identificador"); //Error
                    }
                } else {
                    salir = imprimiError(", se esperaba un Not o un Identificador"); //Error
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
                                    return imprimiError(", se esperaba un Identificador");
                                }
                            } else {
                                return imprimiError(", se esperaba una ,");
                            }
                        } else {
                            return imprimiError(", se esperaba un Identificador");
                        }
                    } else {
                        return imprimiError(", se esperaba un =");
                    }
                } else {
                    return imprimiError(", se esperaba un Identificador");
                }
            } else {
                return imprimiError(", se esperaba una coma ó un signo de Asignacion");
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizador Especial de tipo: " + ex.getMessage());
            salir = true;
            return true;
        }
        return false;
    }
    
    private boolean imprimiError(String mensaje) {
        try {
            erroresSintacticos.agregarALaLista("Error fila: " + tokensIdentificados.obtenerContenido(indice - 1).obtenerFila() + mensaje);
        } catch (ListaElementosExcepcion ex) {
            System.out.println("dfdfd");
        }
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
                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("True") && estaEnLaMismaLinea()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                        indice++;
                        condicionalIfActiva = true;
                        System.out.println("Se encontro condicional if");
                        tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                        return true;
                    } else {
                        salir = imprimiError(", se esperaba :"); // Error
                    }
                } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                    return ifComplemento(indiceIf);
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not") && estaEnLaMismaLinea()) {
                    indice++;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        return ifComplemento(indiceIf);
                    }
                } else {
                    salir = imprimiError(", se esperaba un Identicador, una Constante o un valor Booleano"); // Error
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
                                    indice++;
                                    condicionalIfActiva = true;
                                    System.out.println("Se encontro condicional elif");
                                    return true;
                                } else {
                                    salir = imprimiError(", se esperaba un :"); // Error
                                }
                            } else {
                                salir = imprimiError(", se esperaba un Identicador ó una Constante"); // Error
                            }
                        } else {
                            salir = imprimiError(", se esperaba un signo de Comparación"); // Error
                        }
                    } else {
                        salir = imprimiError(", se esperaba un Identicador ó una Constante"); // Error
                    }
                } else {
                    salir = imprimiError(", No se a abierto ningun if"); // Error
                }
            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else")) {
                if (condicionalIfActiva) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                        indice++;
                        condicionalIfActiva = false;
                        System.out.println("Se encontro condicional else");
                        return true;
                    } else {
                        salir = imprimiError(", se esperaba :"); // Error
                    }
                } else {
                    salir = imprimiError(", No se a abierto ningun if"); // Error
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Verificado if de tipo: " + ex.getMessage());
            salir = true;
            return true;
        }
        return false;
    }
    
    public boolean ifComplemento(int indiceIf) {
        indice++;
        while (true) {
            try {
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && estaEnLaMismaLinea()) {
                    indice++;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        indice++; //Aqui puede esta otro signo igual
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                            indice++;
                            condicionalIfActiva = true;
                            System.out.println("Se encontro condicional if");
                            tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                            return true;
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("and") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("or")) && estaEnLaMismaLinea()) {
                            indice++;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++;
                            }
                        } else {
                            salir = imprimiError(", se esperaba un :"); // Error
                            break;
                        }
                    } else {
                        salir = imprimiError(", se esperaba un Identicadoro ó una Constante"); // Error
                        break;
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                            System.out.println("Se Encontro un ciclo if ()");
                            indice++;
                            condicionalIfActiva = true;
                            tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                            return true;
                        }
                    } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                            indice++;
                            
                            while(true) {
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                        indice++;
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                            System.out.println("if(Algo,Algo,Algo)");
                                            indice++;
                                            condicionalIfActiva = true;
                                            tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                System.out.println("if(Algo):");
                                indice++;
                                condicionalIfActiva = true;
                                tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                                return true;
                            } else {
                                salir = imprimiError(", se esperaba un :"); // Error
                                break;
                            }
                        } else {
                            salir = imprimiError(", se esperaba un ) ó ,"); // Error
                            break;
                        }                            
                    } else {
                        salir = imprimiError(", se esperaba un Identicador, una Constante ó )"); // Error
                        break;
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                    indice++;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        indice++;
                    } else {
                        salir = imprimiError(", se esperaba un Identicador o una Constante"); // Error
                        break;
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                    indice++;
                    condicionalIfActiva = true;
                    System.out.println("Se encontro condicional if");
                    tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                    return true;
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in") && estaEnLaMismaLinea()) {
                    indice++;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                            indice++;
                            condicionalIfActiva = true;
                            System.out.println("Se encontro condicional if algo in algo:");
                            tokensIdentificados.obtenerContenido(indiceIf).establecerTipoDeEstructura("Normal");
                            return true;
                        }
                    }
                } else{
                    salir = imprimiError(", se esperaba un signo de asignacion, aritmetico, ( ó :"); // Error
                    break;
                }
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error en el Verificado if de tipo: " + ex.getMessage());
                salir = true;
                return true;
            }
        }
        return false;
    }
    
    private boolean verificarBloqueFor() {
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
                                indice++;
                                return true;
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                        System.out.println("Se Encontro un ciclo For()");
                                        indice++;
                                        return true;
                                    }
                                } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++;                     
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                        indice++;
                                        
                                        while(true) {
                                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                                indice++;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                                    indice++;
                                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                                    indice++;
                                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                                        System.out.println("for metodo(Algo,Algo,Algo):");
                                                        indice++;
                                                        return true;
                                                    } else {
                                                        salir = imprimiError(", se esperaba :"); // Error
                                                        break;
                                                    }
                                                }
                                            } else {
                                                salir = imprimiError(", se esperaba un Identificador o una Constante"); // Error
                                                break;
                                            }
                                        }
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                            System.out.println("for metodo(Algo):");
                                            condicionalIfActiva = true;
                                            indice++;
                                            return true;
                                        } else {
                                            salir = imprimiError(", se esperaba :"); // Error
                                        }
                                    } else {
                                        salir = imprimiError(", se esperaba , ó )"); // Error
                                    }
                                } else {
                                    salir = imprimiError(", se esperaba ) , un Identificador ó una Constante"); // Error
                                }
                            } else {
                                salir = imprimiError(", se esperaba ( ó :"); // Error
                            }
                        } else {
                            salir = imprimiError(", se esperaba un Identificador"); // Error
                        }
                    } else {
                        salir = imprimiError(", se esperaba in"); // Error
                    }
                } else {
                    salir = imprimiError(", se esperaba un Identificador"); // Error
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Verificado For de tipo: " + ex.getMessage());
            salir = true;
            return true;
        }
        return salir;
    }
    
    public boolean analizarPrint() {
        try {         
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("print")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                    indice++;
                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && !tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("f") && estaEnLaMismaLinea()) { // Verificar que ya haya sido declarada
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                            System.out.println("Print(Identificador)");
                            indice++;
                            return true;
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(".") && estaEnLaMismaLinea()) {
                            indice++;                         
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                            System.out.println("Print(Identificador.Accion)");
                                            indice++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {      
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                    System.out.println("Print(Identificador())");
                                    indice++;
                                    return true;
                                }
                            } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++;
                                
                                while(true) {
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                        indice++;
                                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                            indice++;
                                        }
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                        indice++;
                                        
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                            System.out.println("Print(Identificador(n,v))");
                                            indice++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("+")) && estaEnLaMismaLinea()) {
                                                           
                            while(true) {
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                    indice++;
                                    if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                        indice++;
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                                    indice++;

                                    while(true) {
                                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                                                indice++;
                                            } else if ((tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) && estaEnLaMismaLinea()) {
                                                break;
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                    System.out.println("Print(Identificador,algo,algo)");
                                    indice++;
                                    return true;
                                }
                            }
                        } else {
                            salir = imprimiError(", se esperaba un (, ), . ó ,"); // Error
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("f") && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                System.out.println("Print(f*Constatante)");
                                indice++;
                                return true;
                            }
                        }
                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
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
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") && estaEnLaMismaLinea()) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                System.out.println("Se Encontro un def metodo():");
                                indice++;
                                return true;
                            } else {
                                salir = imprimiError(", se esperaba :"); // Error
                            }
                        } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("*")) && estaEnLaMismaLinea()) {
                            boolean paso = false;
                            
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("*") && estaEnLaMismaLinea()) {
                                indice++;
                                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                    indice++;
                                    paso = true;
                                }
                            } else {
                                indice++;
                                paso = true;
                            }
                            
                            if (paso) {
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                    indice++;

                                    while(true) {
                                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                                indice++;
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                                indice++;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                                    System.out.println("metodo(Algo,Algo,Algo):");
                                                    indice++;
                                                    return true;
                                                } else {
                                                    salir = imprimiError(", se esperaba :"); // Error
                                                    break;
                                                }
                                            } else {
                                                salir = imprimiError(", se esperaba un )"); // Error
                                                break;
                                            }
                                        } else {
                                            salir = imprimiError(", se esperaba un Identificador ó una Constante"); // Error
                                            break;
                                        }
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                        System.out.println("metodo(Algo):");
                                        indice++;
                                        return true;
                                    } else {
                                        salir = imprimiError(", se esperaba :"); // Error
                                    }
                                } else {
                                    salir = imprimiError(", se esperaba un )"); // Error
                                }
                            }
                        } else {
                            salir = imprimiError(", se esperaba un Identificador, una Constante ó un )"); // Error
                        }
                    } else {
                        salir = imprimiError(", se esperaba un ("); // Error
                    }
                } else {
                    salir = imprimiError(", se esperaba un Identificador"); // Error
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
                if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                    indice++;
                    while (true) {
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Comparacion") && estaEnLaMismaLinea()) {
                            indice++;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                    indice++;
                                    condicionalIfActiva = true;
                                    System.out.println("Se encontro condicional while comparacion:");
                                    return true;
                                } else {
                                    salir = imprimiError(", se esperaba :"); // Error
                                    break;
                                }
                            } else {
                                salir = imprimiError(", se esperaba un Identificador ó una Constante"); // Error
                                break;
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(") && estaEnLaMismaLinea()) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                    System.out.println("Se Encontro un ciclo while metodo():");
                                    indice++;
                                    condicionalIfActiva = true;
                                    return true;
                                }
                            } else if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++;                     
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                    indice++;

                                    while(true) {
                                        if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") && estaEnLaMismaLinea()) {
                                                indice++;
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                                indice++;
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                                    System.out.println("while metodo(Algo,Algo,Algo)");
                                                    indice++;
                                                    condicionalIfActiva = true;
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")") && estaEnLaMismaLinea()) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                                        System.out.println("while metodo(Algo):");
                                        indice++;
                                        condicionalIfActiva = true;
                                        return true;
                                    }                               
                                }
                            } else {
                                salir = imprimiError(", se esperaba un Identificador, una Constante ó un )"); // Error
                                break;
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Aritmetico") && estaEnLaMismaLinea()) {
                            indice++;
                            if ((tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) && estaEnLaMismaLinea()) {
                                indice++;
                            } else {
                                salir = imprimiError(", se esperaba un Identificador o una Constante"); // Error
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":") && estaEnLaMismaLinea()) {
                            indice++;
                            condicionalIfActiva = true;
                            System.out.println("Se encontro condicional while identificado:");
                            return true;
                        } else {
                            salir = imprimiError(", se esperaba un signo de Comparación, Aritmetico, un ( ó :"); // Error
                            break;
                        }
                    }
                } else {
                    salir = imprimiError(", se esperaba un Identificador ó una Constante"); // Error
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en While de tipo: " + ex.getMessage());
        }
        return false;
    }
    
    public boolean estaEnLaMismaLinea() {
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
    
    public ListaElementos<String> obtenerErroresSintacticos() {
        return erroresSintacticos;
    }
}
