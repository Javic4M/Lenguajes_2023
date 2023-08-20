
package com.mycompany.parser.py.main.frame;

import com.mycompany.parser.py.main.crearImagen.CrearImagen;
import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class GraficaFronted {
    
    public int mostrarListaDeOpciones(JSpinner numeroDeToken,ListaElementos<Token> tokensIdentificados,JTextArea panelDeTokens, String tipoDeToken) {
        numeroDeToken.setValue(0);
        int contador = 1;
        
        for (int i = 1; i <= tokensIdentificados.getLongitud(); i++) {
            try {
                if (contador == 1) {
                    if (tipoDeToken.equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                        panelDeTokens.setText((contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerLexema()));
                        contador++;
                    }
                } else {
                    if (tipoDeToken.equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                        panelDeTokens.append(("\r\n" + contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerLexema()));
                        contador++;
                    }
                }
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error en Mostrar Archivo");
            }
        }
        contador--;
        
        return contador;
    }
    
    public void seleccionarToken(int contador, ListaElementos<Token> tokensIdentificados, String tipoDeToken, JButton activarGrafica, JButton seleccionar, JSpinner numeroDeTokenSpinner, int numeroDeToken) {
        if (numeroDeToken > 0 && numeroDeToken <= contador) {
            CrearImagen imagen = new CrearImagen();
            imagen.generarImagen(tokensIdentificados, tipoDeToken, numeroDeToken);
            activarGrafica.setEnabled(true);
            seleccionar.setEnabled(false);
            numeroDeTokenSpinner.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null,"Número fuera de Rango","Selección",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void graficarToken(JLabel tipoDeTokenTitulo, String tipoDeToken, ListaElementos<Token> tokensIdentificados, JLabel tipoDeTokenDescripcion, JLabel verGrafica, JButton activarGrafica, JButton seleccionar, JSpinner numeroDeTokenSpinner, int numeroDeToken) {
        tipoDeTokenTitulo.setText(tipoDeToken);
        int indiceToken = 1, contadorToken = 1;
        
        try {
            while (true) {
                if (tipoDeToken.equals(tokensIdentificados.obtenerContenido(indiceToken).obtenerTipoDeToken())) {
                    if (numeroDeToken == contadorToken) {
                        tipoDeTokenDescripcion.setText(" - Contenido: " + tokensIdentificados.obtenerContenido(indiceToken).obtenerLexema() + ",  Linea: " + tokensIdentificados.obtenerContenido(indiceToken).obtenerFila() + ",  Columna: " + tokensIdentificados.obtenerContenido(indiceToken).obtenerColumna());        
                        break;
                    } else {
                        contadorToken++;
                    }
                }
                indiceToken++;
            }
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        verGrafica.setIcon(new ImageIcon(new ImageIcon("imagenToken.png").getImage().getScaledInstance(-1, -1, java.awt.Image.SCALE_SMOOTH)));
        activarGrafica.setEnabled(false);
        seleccionar.setEnabled(true);
        numeroDeTokenSpinner.setEnabled(true);
        numeroDeTokenSpinner.setValue(0);
    }
}
