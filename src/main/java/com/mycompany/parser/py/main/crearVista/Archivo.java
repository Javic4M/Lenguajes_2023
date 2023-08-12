
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
        int columna = 0, columnaAMandar = 0, fila = 1;
        
        while (true) {                        
            if (columna != cadena.length()) {
                
                if (cadena.charAt(columna) != ' ') {
                    if (cadena.charAt(columna) == '#') {
                        columna = crear.analizarComentarios(fila, columna, columnaAMandar, cadena, tokensIdentificados);
//                        
//                        while (true) {
//                            columna++;
//                            
//                            if ("\n".equals("" + cadena.charAt(columna))) {
//                                crear.analizarComentarios(fila, columnaAMandar, "#" + lista, tokensIdentificados);
//                                columna++;
//                                break;
//                            } else {
//                                lista += cadena.charAt(columna);
//                            }
//                        }
                        columnaAMandar = 0;
                        union = "";
                    } else if (cadena.charAt(columna) == '"') {
                        columna = crear.analizarCadena(fila, columna, columnaAMandar, cadena, tokensIdentificados, errores);
                        union = "";
                    } else if ("\n".equals("" + cadena.charAt(columna))) {
                        if (!"".equals(union)) {
                            crear.analizarCentral(fila, columnaAMandar, union, tokensIdentificados, errores);
                            union = "";
                        }
                        fila++; columna++;  columnaAMandar = 0;
                    } else {
                        union += cadena.charAt(columna);
                        columna++;  columnaAMandar++;
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
        int numeroIgual = 1, numeroDeIndice = 1;
        
        try {
            
            while (true) {
                if (tokensIdentificados.obtenerContenido(numeroDeIndice).obtenerTipoDeToken().equals(tipoDeToken)) {
                    if (numeroIgual == numeroDeToken) {
                        temporal = tokensIdentificados.obtenerContenido(numeroDeIndice).obtenerContenido();
                        break;
                    } else {
                        numeroIgual++;
                    }
                }
                numeroDeIndice++;
            }
            
            texto = "digraph G\n"
                    + "{\n"
                    + "     node[shape = circle]\n " + (temporal.charAt(temporal.length() - 1)) + "[shape = doublecircle]";
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
