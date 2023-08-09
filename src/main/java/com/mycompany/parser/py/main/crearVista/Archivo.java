
package com.mycompany.parser.py.main.crearVista;

import com.mycompany.parser.py.main.crearTokens.CrearTokens;
import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    public void organizarCadena(String cadena, ListaElementos<Token> tokensIdentificados, ListaElementos<String> errores) {
        CrearTokens crear = new CrearTokens();
        String union = "";
        int indice = 0;

        while (true) {                        
            if (indice != cadena.length()) {
                if (cadena.charAt(indice) == '"') {
                    indice = crear.analizarCadena(cadena, indice, '"', tokensIdentificados, errores);

                    while (true) {
                        if ("\n".equals("" + cadena.charAt(indice))) {
                            indice++;
                            break;
                        } else {
                            union = "";
                            indice++;
                        }
                    }
                    union = "";
                } else if (cadena.charAt(indice) != ' ') {

                    if (cadena.charAt(indice) == '#') {
                        crear.analizarComentarios(cadena);
                        
                        while (true) {
                            if ("\n".equals("" + cadena.charAt(indice))) {
                                indice++;
                                break;
                            } else {
                                union = "";
                                indice++;
                            }
                        }
                        union = "";
                    } else if ("\n".equals("" + cadena.charAt(indice))) {
                        crear.analizarCentral(union, tokensIdentificados, errores);
                        union = "";
                        indice++;
                    } else {
                        union += cadena.charAt(indice);
                        indice++;
                    }
                } else {
                    crear.analizarCentral(union, tokensIdentificados, errores);
                    union = "";
                    indice++;
                }

            } else {
                if (!"".equals(union)) {
                    crear.analizarCentral(union, tokensIdentificados, errores);
                }
                break;
            }
        }
    }
  
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
        String texto = "", temporal = "";
        int numero = 1;
        
        try {
            
            while (true) {
                if (tokensIdentificados.obtenerContenido(numero).obtenerTipoDeToken().equals(tipoDeToken)) {
                    if (numero == numeroDeToken) {
                        temporal = tokensIdentificados.obtenerContenido(numero).obtenerContenido();
                        break;
                    } else {
                        numero++;
                    }
                }
            }
            
            texto = "digraph G\n"
                    + "{\n"
                    + "     node[shape = circle]\n " + (temporal.charAt(temporal.length() - 1)) + "[shape = doublecircle]"
                    + "     node[style = filled]\n"
                    + "     node[fillcolor = \"#00933\"]\n"
                    + "     node[color = \"#EEEEE\"]\n"
                    + "     node[color = \"#31CEF0\"]\n";
            int indice = 0;
            
            while (indice != temporal.length()) {
                if (indice == temporal.length() - 1) {
                    texto += temporal.charAt(indice) + "\n";
                } else {
                    texto += temporal.charAt(indice) + "->";
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
