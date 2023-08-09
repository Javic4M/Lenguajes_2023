
package com.mycompany.parser.py.main.frame;

import com.mycompany.parser.py.main.crearVista.Archivo;
import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import javax.swing.ImageIcon;

public class Graficas extends javax.swing.JFrame {

    private ListaElementos<Token> tokensIdentificados = new ListaElementos<>();
    private ListaElementos<String> tokensAMostrar = new ListaElementos<>();
    private String tipoDeToken = "";
    private int contador = 1;
    
    public Graficas(ListaElementos<Token> tokensIdentificados) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.tokensIdentificados = tokensIdentificados;
        activarGrafica.setEnabled(false);
        panelDeTokens.setEditable(false);
        activarGrafica.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/com/mycompany/parser/py/main/frame/play.png")).getImage().getScaledInstance(-1, -1, java.awt.Image.SCALE_SMOOTH)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelDeTokens = new javax.swing.JTextArea();
        numeroDeToken = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        identificadores = new javax.swing.JButton();
        aritmeticos = new javax.swing.JButton();
        comparacion = new javax.swing.JButton();
        asignacion = new javax.swing.JButton();
        palabrasClave = new javax.swing.JButton();
        constantes = new javax.swing.JButton();
        otros = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        seleccionar = new javax.swing.JButton();
        activarGrafica = new javax.swing.JButton();
        panelDeGraficas = new javax.swing.JPanel();
        tipoDeTokenTitulo = new javax.swing.JLabel();
        verGrafica = new javax.swing.JLabel();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tokens"));

        panelDeTokens.setColumns(20);
        panelDeTokens.setRows(5);
        jScrollPane1.setViewportView(panelDeTokens);

        jLabel1.setText("Selecciona el Número del Token que desea");

        jLabel2.setText("visualizar:");

        identificadores.setText("Identificadores");
        identificadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identificadoresActionPerformed(evt);
            }
        });

        aritmeticos.setText("Aritmeticos");
        aritmeticos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aritmeticosActionPerformed(evt);
            }
        });

        comparacion.setText("Comparación");
        comparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comparacionActionPerformed(evt);
            }
        });

        asignacion.setText("Asignación");
        asignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asignacionActionPerformed(evt);
            }
        });

        palabrasClave.setText("Palabras Clave");
        palabrasClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                palabrasClaveActionPerformed(evt);
            }
        });

        constantes.setText("Constantes");
        constantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                constantesActionPerformed(evt);
            }
        });

        otros.setText("Otros");
        otros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otrosActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        seleccionar.setText("Seleccionar");
        seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(constantes)
                    .addComponent(identificadores)
                    .addComponent(aritmeticos)
                    .addComponent(comparacion)
                    .addComponent(asignacion)
                    .addComponent(palabrasClave)
                    .addComponent(otros))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(numeroDeToken, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(seleccionar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(identificadores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aritmeticos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comparacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(asignacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(palabrasClave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(constantes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(otros)
                .addGap(2, 8, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numeroDeToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(seleccionar)))
                    .addComponent(jSeparator1))
                .addContainerGap())
        );

        activarGrafica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activarGraficaActionPerformed(evt);
            }
        });

        panelDeGraficas.setBorder(javax.swing.BorderFactory.createTitledBorder("Token"));

        tipoDeTokenTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tipoDeTokenTitulo.setText(" ");

        javax.swing.GroupLayout panelDeGraficasLayout = new javax.swing.GroupLayout(panelDeGraficas);
        panelDeGraficas.setLayout(panelDeGraficasLayout);
        panelDeGraficasLayout.setHorizontalGroup(
            panelDeGraficasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeGraficasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(verGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelDeGraficasLayout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(tipoDeTokenTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDeGraficasLayout.setVerticalGroup(
            panelDeGraficasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeGraficasLayout.createSequentialGroup()
                .addComponent(tipoDeTokenTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDeGraficas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(activarGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(activarGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDeGraficas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void identificadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificadoresActionPerformed
        mostrarLista(1);
    }//GEN-LAST:event_identificadoresActionPerformed

    private void aritmeticosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aritmeticosActionPerformed
        mostrarLista(2);
    }//GEN-LAST:event_aritmeticosActionPerformed

    private void comparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comparacionActionPerformed
        mostrarLista(3);
    }//GEN-LAST:event_comparacionActionPerformed

    private void asignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asignacionActionPerformed
        mostrarLista(4);
    }//GEN-LAST:event_asignacionActionPerformed

    private void palabrasClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_palabrasClaveActionPerformed
        mostrarLista(5);
    }//GEN-LAST:event_palabrasClaveActionPerformed

    private void constantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_constantesActionPerformed
        mostrarLista(6);
    }//GEN-LAST:event_constantesActionPerformed

    private void otrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otrosActionPerformed
        mostrarLista(7);
    }//GEN-LAST:event_otrosActionPerformed

    private void activarGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activarGraficaActionPerformed
        tipoDeTokenTitulo.setText(tipoDeToken);
        verGrafica.setIcon(new ImageIcon(new ImageIcon("imagenToken.png").getImage().getScaledInstance(-1, -1, java.awt.Image.SCALE_SMOOTH)));
        activarGrafica.setEnabled(false);
        seleccionar.setEnabled(true);
        numeroDeToken.setEnabled(true);
    }//GEN-LAST:event_activarGraficaActionPerformed

    private void seleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionarActionPerformed
        Archivo archivo = new Archivo();
        archivo.generarImagen(tokensIdentificados, tipoDeToken, obtenerNumeroDeToken());
        activarGrafica.setEnabled(true);
        seleccionar.setEnabled(false);
        numeroDeToken.setEnabled(false);
        numeroDeToken.setValue(0);
    }//GEN-LAST:event_seleccionarActionPerformed
 
    private void mostrarLista(int indice) {
        numeroDeToken.setValue(0);
        contador = 1;
        
        for (int i = 1; i <= tokensIdentificados.getLongitud(); i++) {
            try {
                if (contador == 1) {
                    if (indice == 1) {
                        if ("Identificador".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.setText((contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Identificador";
                            contador++;
                        }
                    } else if (indice == 2) {
                        if ("Aritmetico".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.setText((contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Aritmetico";
                            contador++;
                        }
                    } else if (indice == 3) {
                        if ("Comparacion".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.setText((contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Comparacion";
                            contador++;
                        }
                    } else if (indice == 4) {
                        if ("Asignacion".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.setText((contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Asignacion";
                            contador++;
                        }
                    } else if (indice == 5) {
                        if ("Palabras Clave".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.setText((contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Palabras Clave";
                            contador++;
                        }
                    } else if (indice == 6) {
                        if ("Constante".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.setText((contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Constante";
                            contador++;
                        }
                    } else {
                        if ("Otros".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.setText((contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Otros";
                            contador++;
                        }
                    }
                } else {
                    if (indice == 1) {
                        if ("Identificador".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.append(("\n" + contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Identificador";
                            contador++;
                        }
                    } else if (indice == 2) {
                        if ("Aritmetico".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.append(("\n" + contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Aritmetico";
                            contador++;
                        }
                    } else if (indice == 3) {
                        if ("Comparacion".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.append(("\n" + contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Comparacion";
                            contador++;
                        }
                    } else if (indice == 4) {
                        if ("Asignacion".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.append(("\n" + contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Asignacion";
                            contador++;
                        }
                    } else if (indice == 5) {
                        if ("Palabras Clave".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.append(("\n" + contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Palabras Clave";
                            contador++;
                        }
                    } else if (indice == 6) {
                        if ("Constante".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.append(("\n" + contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Constante";
                            contador++;
                        }
                    } else {
                        if ("Otros".equals(tokensIdentificados.obtenerContenido(i).obtenerTipoDeToken())) {
                            panelDeTokens.append(("\n" + contador + "| " + tokensIdentificados.obtenerContenido(i).obtenerContenido()));
                            tipoDeToken = "Otros";
                            contador++;
                        }
                    }
                }
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error en Mostrar Archivo");
            }
        }
    }

    public int obtenerNumeroDeToken() {
        return (int) numeroDeToken.getValue();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton activarGrafica;
    private javax.swing.JButton aritmeticos;
    private javax.swing.JButton asignacion;
    private javax.swing.JButton comparacion;
    private javax.swing.JButton constantes;
    private javax.swing.JButton identificadores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner numeroDeToken;
    private javax.swing.JButton otros;
    private javax.swing.JButton palabrasClave;
    private javax.swing.JPanel panelDeGraficas;
    private javax.swing.JTextArea panelDeTokens;
    private javax.swing.JButton seleccionar;
    private javax.swing.JLabel tipoDeTokenTitulo;
    private javax.swing.JLabel verGrafica;
    // End of variables declaration//GEN-END:variables
}
