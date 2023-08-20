package com.mycompany.parser.py.main.lista;

import java.io.Serializable;

public class Nodo<T> implements Serializable {
    
    private T contenido = null;
    private Nodo<T> siguiente;
    
    public Nodo(T contenido) {
        this.contenido = contenido;
    }

    public T getContenido() {
        return contenido;
    }

    public void setContenido(T contenido) {
        this.contenido = contenido;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
