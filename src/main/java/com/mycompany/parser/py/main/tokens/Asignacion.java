
package com.mycompany.parser.py.main.tokens;

public class Asignacion {
    
    private String tipoDeAsignacion;
    private String nombre;
    private String informacion;

    public Asignacion(String tipoDeAsignacion, String nombre, String informacion) {
        this.tipoDeAsignacion = tipoDeAsignacion;
        this.nombre = nombre;
        this.informacion = informacion;
    }

    public String obtenerTipoDeAsignacion() {
        return tipoDeAsignacion;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public String obtenerInformacion() {
        return informacion;
    }
}
