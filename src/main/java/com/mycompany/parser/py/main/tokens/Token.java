
package com.mycompany.parser.py.main.tokens;

public class Token {
    
    String tipoDeToken = "", patron = "", lexema = "", espacio = " ";
    int fila, columna;
    
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
    
    public void cambiarEspacio(String espacio) {
        this.espacio = espacio;
    }
    
    public String obtenerEspacio() { 
        return espacio;
    }
    
    private void agregarPatron() {
        if ("Identificador".equals(tipoDeToken)) {
            patron = "Letra seguida de Otras Letras o Números";
        } else if ("Aritmetico".equals(tipoDeToken)) {
            patron = "(0-9)*(";
        } else if ("Comparacion".equals(tipoDeToken)) {
            patron = "Signo";
        } else if ("Asignacion".equals(tipoDeToken)) {
            patron = "Letra ";
        } else if ("Palabra Clave".equals(tipoDeToken)) {
            patron = lexema;
        } else if ("Otros".equals(tipoDeToken)) {
            patron = "Letra ";
        }
    }
}
