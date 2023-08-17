package com.mycompany.parser.py.main.crearTokens;

import java.util.HashMap;

public class TablaDeSimbolos {
    
    private HashMap<String, String> tablaSimbolos;

    public TablaDeSimbolos() {
        this.tablaSimbolos = new HashMap<>();
        tablaSimbolos.put("+", "Aritmetico");
        tablaSimbolos.put("-", "Aritmetico");
        tablaSimbolos.put("*", "Aritmetico");
        tablaSimbolos.put("/", "Aritmetico");
        tablaSimbolos.put("//", "Aritmetico");
        tablaSimbolos.put("%", "Aritmetico");
        tablaSimbolos.put("==", "Comparacion");
        tablaSimbolos.put("!=", "Comparacion");
        tablaSimbolos.put("<=", "Comparacion");
        tablaSimbolos.put(">=", "Comparacion");
        tablaSimbolos.put("<", "Comparacion");
        tablaSimbolos.put(">", "Comparacion");      
        tablaSimbolos.put("=", "Asignacion");
        tablaSimbolos.put("+=", "Asignacion");
        tablaSimbolos.put("-=", "Asignacion");
        tablaSimbolos.put("*=", "Asignacion");
        tablaSimbolos.put("/=", "Asignacion");   
        tablaSimbolos.put("(", "Otro");
        tablaSimbolos.put(")", "Otro");
        tablaSimbolos.put("{", "Otro");
        tablaSimbolos.put("}", "Otro");
        tablaSimbolos.put("[", "Otro");
        tablaSimbolos.put("]", "Otro");       
        tablaSimbolos.put("and", "Palabra Clave");
        tablaSimbolos.put("as", "Palabra Clave");
        tablaSimbolos.put("assert", "Palabra Clave");
        tablaSimbolos.put("break", "Palabra Clave");
        tablaSimbolos.put("class", "Palabra Clave");
        tablaSimbolos.put("continue", "Palabra Clave");
        tablaSimbolos.put("def", "Palabra Clave");
        tablaSimbolos.put("del", "Palabra Clave");
        tablaSimbolos.put("elif", "Palabra Clave");
        tablaSimbolos.put("else", "Palabra Clave");
        tablaSimbolos.put("except", "Palabra Clave");
        tablaSimbolos.put("finally", "Palabra Clave");
        tablaSimbolos.put("for", "Palabra Clave");
        tablaSimbolos.put("from", "Palabra Clave");
        tablaSimbolos.put("global", "Palabra Clave");
        tablaSimbolos.put("if", "Palabra Clave");
        tablaSimbolos.put("import", "Palabra Clave");
        tablaSimbolos.put("in", "Palabra Clave");
        tablaSimbolos.put("is", "Palabra Clave");
        tablaSimbolos.put("lambda", "Palabra Clave");
        tablaSimbolos.put("None", "Palabra Clave");
        tablaSimbolos.put("nonlocal", "Palabra Clave");
        tablaSimbolos.put("not", "Palabra Clave");
        tablaSimbolos.put("or", "Palabra Clave");
        tablaSimbolos.put("pass", "Palabra Clave");
        tablaSimbolos.put("raise", "Palabra Clave");
        tablaSimbolos.put("return", "Palabra Clave");
        tablaSimbolos.put("try", "Palabra Clave");
        tablaSimbolos.put("while", "Palabra Clave");
        tablaSimbolos.put("with", "Palabra Clave");
        tablaSimbolos.put("yield", "Palabra Clave");
        tablaSimbolos.put("True", "Constante");
        tablaSimbolos.put("False", "Constante");
    }
    
    public HashMap<String, String> obtenerTablaDeSimbolos() {
        return tablaSimbolos;
    }
}
