
package com.mycompany.parser.py.main.tokens;

public class Token {
    
    private String tipoDeToken = "", patron = "", lexema = "";
    private int fila, columna;
    private int bloque = 0;
    private boolean esUnMetodo = false;
    
    public Token(String tipoDeToken, String lexema, int fila, int columna) {
        this.tipoDeToken = tipoDeToken;
        this.lexema = lexema;
        this.fila = fila;
        this.columna =  columna;
        agregarPatron();
    }

    public String obtenerTipoDeToken() {
        return tipoDeToken;
    }
    
    public String obtenerLexema() {
        return lexema;
    }
    
    public int obtenerFila() {
        return fila;
    }

    public int obtenerColumna() {
        return columna;
    }
    
    public String obtenerPatron() {
        return patron;
    }
    
    public void colocarBloque(int bloque) {
        this.bloque = bloque;
    }
    
    public int obtenerBloque() {
        return bloque;
    }
    
    public void confirmarMetodo() {
        esUnMetodo = true;
    }
    
    public boolean obtenerSiEsMetodo() {
        return esUnMetodo;
    }
    
    private void agregarPatron() {
        if ("Identificador".equals(tipoDeToken)) {
            patron = "([\\w]|_)+(\\w|\\d)*";
        } else if ("Aritmetico".equals(tipoDeToken)) {
            patron = lexema;
        } else if ("Comparacion".equals(tipoDeToken)) {
            patron = lexema;
        } else if ("Asignacion".equals(tipoDeToken)) {
            patron = lexema;
        } else if ("Constante".equals(tipoDeToken)) {
            patron = lexema;
        } else if ("Comentario".equals(tipoDeToken)) {
            patron = lexema;
        } else if ("Palabra Clave".equals(tipoDeToken)) {
            patron = lexema;
        } else if ("Otros".equals(tipoDeToken)) {
            patron = lexema;
        }
    }
}
