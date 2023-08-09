
package com.mycompany.parser.py.main.lista;

import java.io.Serializable;

public class ListaElementosExcepcion extends Exception implements Serializable{
    
    public ListaElementosExcepcion(String string) {
        super(string);
    }
}