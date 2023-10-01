
package com.mycompany.parser.py.main.tokens;

public class InformacionBloques {
    
    private String tipoDeBloque;
    private String bloque;
    private int fila, identacion;

    public InformacionBloques(String bloque, int fila) {
        this.tipoDeBloque = tipoDeBloque;
        this.bloque = bloque;
        this.fila = fila;
    }
    
    public String obtenerTipoDeBloque() {
        return tipoDeBloque;
    }
    
    public String obtenerBloque() {
        return bloque;
    }

    public int obtenerFila() {
        return fila;
    }

    public int obtenerIdentacion() {
        return identacion;
    }
}
