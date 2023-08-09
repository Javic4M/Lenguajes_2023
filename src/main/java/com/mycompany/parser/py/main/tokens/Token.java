
package com.mycompany.parser.py.main.tokens;

public class Token {
    
    String tipoDeToken = "", contenido = "";
    int linea, columna;
    
    public Token(String tipoDeToken, String contenido, int linea, int columna) {
        this.tipoDeToken = tipoDeToken;
        this.contenido = contenido;
        this.linea = linea;
        this.columna =  columna;
    }

    public String obtenerTipoDeToken() {
        return tipoDeToken;
    }
    
    public String obtenerContenido() {
        return contenido;
    }
    
    public int obtenerLinea() {
        return linea;
    }

    public int obtenerColumna() {
        return columna;
    }
    
}
