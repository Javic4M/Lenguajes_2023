
package com.mycompany.parser.py.main.crearImagen;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CrearImagen {
    
    public void generarImagen(ListaElementos<Token> tokensIdentificados, String tipoDeToken, int numeroDeToken) {
        try {
            crearArchivo(obtenerContenido(tokensIdentificados, tipoDeToken, numeroDeToken));
            ProcessBuilder process = new ProcessBuilder("dot", "-Tpng", "-o", "imagenToken.png", "Imagen_Analizador.dot");
            process.redirectErrorStream(true);
            process.start();
        } catch (IOException ex) {
            System.out.println("Error en generar imagen");
        }
    }
        
    private void crearArchivo(String contenido) {
        FileWriter fichero = null;
        PrintWriter escritor = null;
        
        try {
            fichero = new FileWriter("Imagen_Analizador.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(contenido);
            escritor.close();
            fichero.close();
        } catch (IOException ex) {
            System.out.println("Error en el Generador de Imagen");
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException ex) {
                System.out.println("Error en el Finally");
            }
        }
    }
    
    private String obtenerContenido(ListaElementos<Token> tokensIdentificados, String tipoDeToken, int numeroDeToken) {
        String texto = "", temporal = "", temporal_2 = "";
        int numeroIgual = 1, numeroDeIndice = 1;
        
        try {
            
            while (true) {
                if (tokensIdentificados.obtenerContenido(numeroDeIndice).obtenerTipoDeToken().equals(tipoDeToken)) {
                    if (numeroIgual == numeroDeToken) {
                        temporal = tokensIdentificados.obtenerContenido(numeroDeIndice).obtenerLexema();
                        break;
                    } else {
                        numeroIgual++;
                    }
                }
                numeroDeIndice++;
            }
            
            for (int i = 0; i < temporal.length(); i++) {
                if (temporal.charAt(i) != '"') {
                    temporal_2 += temporal.charAt(i);
                }
            }
            
            texto = "digraph G {\n"
                    + "     node[shape = circle]\n\" " + (temporal.charAt(temporal.length() - 1)) + " \"[shape = doublecircle]";
            int indice = 0;
            
            while (indice != temporal.length()) {
                if (indice == temporal.length() - 1) {
                    texto = texto + "\" " + temporal.charAt(indice) + " \"\n";
                } else {
                    texto = texto + "\" " + temporal.charAt(indice) + " \"->";
                }
                indice++;
            }
            texto += "rankdir = LR;\n" + "}";
        } catch (ListaElementosExcepcion ex) {
            System.out.println("Error en la lista");
        }
        return texto;
    }
}
