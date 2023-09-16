
package com.mycompany.parser.py.main.analizadorDeTokens;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;

public class AnalizadorDeTokens {
    
    private ListaElementos<String> erroresSintacticos;
    private ListaElementos<Token> tokensIdentificados;
    private int indice;
    private boolean condicionalIfActiva = false;
    
    public AnalizadorDeTokens(ListaElementos<Token> tokensIdentificados, int indice) {
        this.indice = indice;
        this.tokensIdentificados = tokensIdentificados;
    }
    
    public void analizarListaDeTokens() {
        System.out.println("Longitud: " + tokensIdentificados.getLongitud());
        
        while (indice != (tokensIdentificados.getLongitud() + 1)) {
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
                                                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificado") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("True") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("False")) {
                                                    indice++;
                                                    System.out.println("Se Encontro un return");
                                                }
                                            }
                                        } catch (ListaElementosExcepcion ex) {
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
    
    public boolean verificarAsignacionOMetodo() {
        boolean estado = false;
        
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("var")) {
                indice++;
            }
            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Asignacion")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                        if (indice != tokensIdentificados.getLongitud()) {
                            
                            if (tokensIdentificados.obtenerContenido(indice + 1).obtenerLexema().equals("if")) {
                                indice++;
                                estado = verificarIfEspeciales();
                            } else {
                                System.out.println("Es una Asignacion");
                                indice++;
                                estado = true;
                            }
                        } else {
                            System.out.println("Es una Asignacion");
                            indice++;
                            estado = true;
                        }
                    } else {
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("[") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("{")) {
                            indice++;
                            while(true) {
                                if  (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}")) {
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("]") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("}")) {
                                            System.out.println("Se encontro un Arreglo");
                                            indice++;
                                            estado = true;
                                            break;
                                        } else {
                                            indice++;
                                        }
                                    }
                                }
                                if (indice == tokensIdentificados.getLongitud()) {
                                    indice++;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    estado = analizarAsignacionEspecial();
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizador Mixco de tipo: " + ex.getMessage());
        }
        return estado;
    }
    
    private boolean verificarIfEspeciales() {
       
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("if")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else")) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                            System.out.println("Sentencia normal");
                            indice++;
                            return true;
                        }
                    } else {
                        verificarBloqueIf();
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("not")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("else")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                System.out.println("Sentencia con not");
                                indice++;
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizador de Sentencias de tipo: " + ex);
        }
        return false;
    }
    
    private boolean analizarAsignacionEspecial() {
        try {
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("=")) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",")) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                    if(indice != tokensIdentificados.getLongitud()) {
                                        indice++;
                                        if(tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("+") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("-") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("/") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("*")) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
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
                                }
                            }
                        }
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Analizador Especial de tipo: " + ex.getMessage());
        }
        return false;
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
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("True")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                        indice++;
                        condicionalIfActiva = true;
                        System.out.println("Se encontro condicional if");
                        return true;
                    }
                } else if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                    indice++;
                    while (true) {
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(">") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("<") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(">=") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("<=") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("==") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("!=")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                indice++; //Aqui puede esta otro signo igual
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                    indice++;
                                    condicionalIfActiva = true;
                                    System.out.println("Se encontro condicional if");
                                    return true;
                                }
                            }
                        } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                    System.out.println("Se Encontro un ciclo if ()");
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
                                                    System.out.println("if(Algo,Algo,Algo)");
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
                                        System.out.println("if(Algo):");
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
                            System.out.println("Se encontro condicional if");
                            return true;
                        }
                    }
                }
            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("elif")) {
                if (condicionalIfActiva) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(">") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("<")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                    indice++;
                                    condicionalIfActiva = true;
                                    System.out.println("Se encontro condicional elif");
                                    return true;
                                }
                            }
                        }
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
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                        indice++;
                        condicionalIfActiva = false;
                        System.out.println("Se encontro condicional else");
                        return true;
                    }
                } else {
                    System.out.println("Error al escribir else no se a abierto ningun if");
                    
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
        }
        return false;
    }
    
    private boolean verificarBloqueFor() {
        try {         
            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("for")) {
                indice++;
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("in")) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) { // Hay que verificar que ya haya sido declara la variable
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                System.out.println("Se Encontro un ciclo For");
                                indice++;
                                return true;
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(")) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                    indice++;
                                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                        System.out.println("Se Encontro un ciclo For()");
                                        indice++;
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
                                                        System.out.println("for metodo(Algo,Algo,Algo):");
                                                        indice++;
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                        indice++;
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                            System.out.println("for metodo(Algo):");
                                            indice++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en el Verificado For de tipo: " + ex.getMessage());
        }
        return false;
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
                        } else {                           
                            while(true) {
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",")) {
                                    indice++;

                                    if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                        indice++;
                                    }
                                } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("+") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("-") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("*") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("/")) {
                                    indice++;

                                    while(true) {
                                        if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador") || tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Constante")) {
                                            indice++;
                                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("+") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("-") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("*") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("/")) {
                                                indice++;
                                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(",") || tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                                break;
                                            } else {
                                                // Break con error
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
                if (tokensIdentificados.obtenerContenido(indice).obtenerTipoDeToken().equals("Identificador")) {
                    indice++;
                    if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals("(")) {
                        indice++;
                        if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                            indice++;
                            if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                System.out.println("Se Encontro un def metodo():");
                                indice++;
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
                                                System.out.println("metodo(Algo,Algo,Algo):");
                                                indice++;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            } else if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(")")) {
                                indice++;
                                if (tokensIdentificados.obtenerContenido(indice).obtenerLexema().equals(":")) {
                                    System.out.println("metodo(Algo):");
                                    indice++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en Def de tipo: " + ex.getMessage());
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
}
