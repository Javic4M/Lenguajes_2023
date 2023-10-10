
package com.mycompany.parser.py.main.tokens;

public class BloqueDeToken {
    
    private String cadena;
    private int bloque;
    private boolean metodo = false;

    public BloqueDeToken(String cadena, int bloque, boolean metodo) {
        this.cadena = cadena;
        this.bloque = bloque;
        this.metodo = metodo;
    }

    public String obtenerCadena() {
        return cadena;
    }

    public int obtenerBloque() {
        return bloque;
    }

    public void confirmarMetodo() {
        metodo = true;
    }
    
    public boolean esUnMetodo() {
        return metodo;
    }
}
