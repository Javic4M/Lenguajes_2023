
package com.mycompany.parser.py.main.crearVista;

import com.mycompany.parser.py.main.crearTokens.CrearTokens;
import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.tokens.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Archivo {
    
    @SuppressWarnings("unchecked")
    public ListaElementos<String> crearVisualizacionDeArchivo(String path) {
        ListaElementos<String> lista = new ListaElementos<>();
        
        File file = new File(path);

        if (file.exists()) {
            BufferedReader entrada = null;

            try { 
                entrada = new BufferedReader(new FileReader(file));
                String cadena;

                while ((cadena = entrada.readLine()) != null) {
                    lista.agregarALaLista(cadena);
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null,"No Existe el Archivo","Archivo Inexistente",JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
    public void organizarCadena(String buffer, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores) {
        CrearTokens crear = new CrearTokens();
        int columna = 0, columnaAMandar = 1, fila = 1;
        String cadena = "", union = "";
        
        for (int i = 0; i < buffer.length(); i++) {
            if (!"\n".equals("" + buffer.charAt(i))) {
                cadena += buffer.charAt(i);
            }
        }
        
        while (true) {                        
            if (columna != cadena.length()) {
                
                if (cadena.charAt(columna) != ' ') {
                    if (cadena.charAt(columna) == '#') {
                        if (!"".equals(union)) {
                            crear.analizarCentral(fila, columnaAMandar, union, tokensIdentificados, errores);
                            union = "";
                       }
                        columna = crear.analizarComentarios(fila, columna, columnaAMandar, cadena, tokensIdentificados);
                        columnaAMandar = 1;
                    } else if (cadena.charAt(columna) == '"' || "'".equals("" + cadena.charAt(columna))) {
                        if (!"".equals(union)) {
                            crear.analizarCentral(fila, columnaAMandar, union, tokensIdentificados, errores);
                            union = "";
                        }
                        columna = crear.analizarCadena(fila, columna, columnaAMandar, cadena, "" + cadena.charAt(columna), tokensIdentificados, errores);
                        if (crear.saberFinDeLinea()) {
                            columnaAMandar = 1;
                        }
                    } else if ("\r".equals("" + cadena.charAt(columna))) {
                        if (!"".equals(union)) {
                            crear.analizarCentral(fila, columnaAMandar, union, tokensIdentificados, errores);
                            union = "";
                        }
                        fila++; columna ++; columnaAMandar = 1;
                    } else {
                        union += cadena.charAt(columna);
                        columna++; columnaAMandar++;
                    }
                } else {
                    if (!"".equals(union)) {
                        crear.analizarCentral(fila, columnaAMandar, union, tokensIdentificados, errores);
                        union = "";
                    }
                    columna++; columnaAMandar++;
                }
            } else {
                if (!"".equals(union)) {
                    crear.analizarCentral(fila, columnaAMandar, union, tokensIdentificados, errores);
                }
                break;
            }
        }
    }
}
