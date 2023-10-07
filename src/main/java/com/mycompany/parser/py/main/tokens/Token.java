
package com.mycompany.parser.py.main.tokens;

public class Token {
    
    private String tipoDeToken = "", patron = "", lexema = "", tabulaciones = "", caracterSiguiente = "", tipoDeEstructura =  "";
    private int fila, columna, bloque = 1, noDeInvocaciones = 0;
    
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
    
    public void guardarCaracterSiguiente(String caracterSiguiente) {
        this.caracterSiguiente = caracterSiguiente;
    }
    
    public void aumentarInvocacion() {
        noDeInvocaciones++;
    }
    
    public int obtenerNoDeInvocaciones() {
        return noDeInvocaciones;
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
    
    public String obtenerTabulaciones() {
        return tabulaciones;
    }
    
    public int obtenerBloque() {
        return bloque + 1;
    }
    
    public void establecerTipoDeEstructura(String tipoDeEstructura) {
        this.tipoDeEstructura = tipoDeEstructura;
    }
    
    public String obtenerTipoDeEstructura() {
        return tipoDeEstructura;
    }
    
    public String obtenerLexemaCompuesto() {
        if (!"\r".equals(caracterSiguiente)) {
            return tabulaciones + lexema + caracterSiguiente;
        } else {
            return tabulaciones + lexema + "\n";
        }
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
