
package com.mycompany.parser.py.main.tokens;

public class Token {
    
    String tipoDeToken = "", contenido = "";
    int fila, columna;
    
    public Token(String tipoDeToken, String contenido, int fila, int columna) {
        this.tipoDeToken = tipoDeToken;
        this.contenido = contenido;
        this.fila = fila;
        this.columna =  columna;
    }

    public String obtenerTipoDeToken() {
        return tipoDeToken;
    }
    
    public String obtenerContenido() {
        return contenido;
    }
    
    public int obtenerFila() {
        return fila;
    }

    public int obtenerColumna() {
        return columna;
    }
    
}
