
package com.mycompany.parser.py.main.tokens;

public class Token {
    
    private String tipoDeToken = "", patron = "", lexema = "", tabulaciones = "", caracterSiguiente = "", tipoDeEstructura =  "";
    private int fila, columna, bloque = 0;
    private boolean esUnMetodo = false;
    
    public Token(String tipoDeToken, String lexema, int fila, int columna, int bloque, String tabulaciones, String caracterSiguiente) {
        this.tipoDeToken = tipoDeToken;
        this.lexema = lexema;
        this.fila = fila;
        this.columna =  columna;
        this.bloque = bloque;
        this.tabulaciones = tabulaciones;
        this.caracterSiguiente = caracterSiguiente;
        agregarPatron();
    }

    public void establecerTipoDeEstructura(String tipoDeif) {
        this.tipoDeEstructura = tipoDeif;
    }
    
    public String obtenerTipoDeEstructura() {
        return tipoDeEstructura;
    }
    
    public void guardarCaracterSiguiente(String caracterSiguiente) {
        this.caracterSiguiente = caracterSiguiente;
    }
    
    public String obtenerTipoDeToken() {
        return tipoDeToken;
    }
    
    public String obtenerLexema() {
        return lexema;
    }
    
    public String obtenerLexemaCompuesto() {
        return tabulaciones + lexema;
    }
    
    public String obtenerLexemaCompuesto_2() {
        return tabulaciones + lexema + caracterSiguiente;
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
    
    public String obtenerTabulaciones() {
        return tabulaciones;
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
