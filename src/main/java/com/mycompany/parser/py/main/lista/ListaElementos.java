package com.mycompany.parser.py.main.lista;

import java.io.Serializable;

public class ListaElementos <T> implements Serializable {
    
    private Nodo<T> inicio;
    private Nodo<T> ultimo;
    private int longitud = 0;
    
    public void agregarALaLista(T contenido) {
        
        if (estaVacia()) {
            inicio = new Nodo<>(contenido);
            ultimo = inicio;
        } else {
            Nodo<T> nuevo = new Nodo<>(contenido);
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }
        longitud++;
    }
    
    public void eliminar(int indice) throws ListaElementosExcepcion {
        
        if (indice < 1 || indice > longitud) {
            throw new ListaElementosExcepcion("Número fuera de Rango");
        } else {          
            if (estaVacia()){
		throw new ListaElementosExcepcion("La Lista esta Vacia");
            } else {		
                if (longitud == 1 && indice == 1) {
                    inicio = ultimo = null;
                } else if (longitud != 1 && indice == 1) {
                    inicio = inicio.getSiguiente();
                } else if (indice == longitud) {
                    Nodo<T> penultimo = encontrarPorIndiceUsoInterno(longitud - 2);
                    penultimo.setSiguiente(null);
                    ultimo = penultimo;
                } else {
                    Nodo<T> anterior = encontrarPorIndiceUsoInterno(indice - 2);
                    Nodo<T> aEliminar = encontrarPorIndiceUsoInterno(indice - 1);
                    anterior.setSiguiente(aEliminar.getSiguiente());
                }
                longitud--;
            }	
        }
    }
    
    private Nodo<T> encontrarPorIndiceUsoInterno(int indice) {
        Nodo<T> actual = inicio;

        for (int i = 0; i < indice; i++) {
            @SuppressWarnings("unchecked")
            Nodo<T> siguiente = actual.getSiguiente();
            actual = siguiente;
        }
        return actual;
    }
    
    public Nodo<T> encontrarPorIndice(int indice) {
        Nodo<T> actual = inicio;
        indice--;
        
        for (int i = 0; i < indice; i++) {
            @SuppressWarnings("unchecked")
            Nodo<T> siguiente = actual.getSiguiente();
            actual = siguiente;
        }
        return actual;
    }
    
    public T obtenerContenido(int indice) throws ListaElementosExcepcion {
        T contenido = null;
        
        if (indice < 1 || indice > longitud) {
            throw new ListaElementosExcepcion("Número fuera de Rango");
        } else {
            Nodo<T> obtener = encontrarPorIndiceUsoInterno(indice - 1);
            contenido = obtener.getContenido();
        }
        return contenido;
    }
    
    public boolean estaVacia() {
        return longitud == 0;
    }
    
    public int getLongitud() {
        return longitud;
    }
}