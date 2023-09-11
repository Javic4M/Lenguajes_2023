
package com.mycompany.parser.py.main.frame;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReportesFronted {
    
    public void mostrarTablaDeSimbolos(ListaElementos<Token> tokensIdentificados, JTable tablaReporte, DefaultTableModel tablaModelo) {
        String[] titulos = {"Token", "Patrón", "Lexema", "Linea", "Columna"};
        tablaModelo.setColumnIdentifiers(titulos);
        tablaReporte.setModel(tablaModelo);
        
        if (tokensIdentificados.getLongitud() != 0) { 
            for (int i = 1; i <= tokensIdentificados.getLongitud(); i++) {
                try {
                    if (!"Espacio".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken()) && !"Error".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                        tablaModelo.addRow(new Object[] {tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken(), tokensIdentificados.obtenerContenido(i).obtenerPatron(), tokensIdentificados.obtenerContenido(i).obtenerLexema(), tokensIdentificados.obtenerContenido(i).obtenerFila(), tokensIdentificados.obtenerContenido(i).obtenerColumna()});
                    }
                } catch (ListaElementosExcepcion ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null,"No hay Tokens para Mostrar", "Falta de Tokens", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
