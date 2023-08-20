
package com.mycompany.parser.py.main.frame;

import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.tokens.Token;
import javax.swing.ImageIcon;

public class Graficas extends javax.swing.JFrame {

    private ListaElementos<Token> tokensIdentificados = new ListaElementos<>();
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
        identificadores = new javax.swing.JButton();
        aritmeticos = new javax.swing.JButton();
        comparacion = new javax.swing.JButton();
        asignacion = new javax.swing.JButton();
        palabrasClave = new javax.swing.JButton();
        constantes = new javax.swing.JButton();
        comentarios = new javax.swing.JButton();
        otros = new javax.swing.JButton();
        seleccionar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        activarGrafica = new javax.swing.JButton();
        panelDeGraficas = new javax.swing.JPanel();
        tipoDeTokenTitulo = new javax.swing.JLabel();
        verGrafica = new javax.swing.JLabel();
        tipoDeTokenDescripcion = new javax.swing.JLabel();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tokens"));

        panelDeTokens.setColumns(20);
        panelDeTokens.setRows(5);
        jScrollPane1.setViewportView(panelDeTokens);

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

        comentarios.setText("Comentarios");
        comentarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comentariosActionPerformed(evt);
            }
        });

        otros.setText("Otros");
        otros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otrosActionPerformed(evt);
            }
        });

        seleccionar.setText("Seleccionar");
        seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionarActionPerformed(evt);
            }
        });

        jLabel3.setText("Selecciona el Número del");

        jLabel4.setText("Token que desea visualizar:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(palabrasClave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(identificadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(constantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(aritmeticos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comparacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(otros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comentarios)
                            .addComponent(asignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(numeroDeToken, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(seleccionar, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addContainerGap())))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(identificadores)
                    .addComponent(aritmeticos)
                    .addComponent(comparacion)
                    .addComponent(asignacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(palabrasClave)
                    .addComponent(constantes)
                    .addComponent(otros)
                    .addComponent(comentarios))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numeroDeToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(seleccionar))
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        activarGrafica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activarGraficaActionPerformed(evt);
            }
        });

        panelDeGraficas.setBorder(javax.swing.BorderFactory.createTitledBorder("Token"));

        tipoDeTokenTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tipoDeTokenTitulo.setText(" ");

        tipoDeTokenDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout panelDeGraficasLayout = new javax.swing.GroupLayout(panelDeGraficas);
        panelDeGraficas.setLayout(panelDeGraficasLayout);
        panelDeGraficasLayout.setHorizontalGroup(
            panelDeGraficasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeGraficasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDeGraficasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(verGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelDeGraficasLayout.createSequentialGroup()
                        .addComponent(tipoDeTokenTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tipoDeTokenDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDeGraficasLayout.setVerticalGroup(
            panelDeGraficasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeGraficasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDeGraficasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tipoDeTokenTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tipoDeTokenDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
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
                .addComponent(panelDeGraficas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void identificadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificadoresActionPerformed
        tipoDeToken = "Identificador"; mostrarLista();
    }//GEN-LAST:event_identificadoresActionPerformed

    private void aritmeticosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aritmeticosActionPerformed
        tipoDeToken = "Aritmetico"; mostrarLista();
    }//GEN-LAST:event_aritmeticosActionPerformed

    private void comparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comparacionActionPerformed
        tipoDeToken = "Comparacion"; mostrarLista();
    }//GEN-LAST:event_comparacionActionPerformed

    private void asignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asignacionActionPerformed
        tipoDeToken = "Asignacion"; mostrarLista();
    }//GEN-LAST:event_asignacionActionPerformed

    private void palabrasClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_palabrasClaveActionPerformed
        tipoDeToken = "Palabra Clave"; mostrarLista();
    }//GEN-LAST:event_palabrasClaveActionPerformed

    private void constantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_constantesActionPerformed
        tipoDeToken = "Constante"; mostrarLista();
    }//GEN-LAST:event_constantesActionPerformed

    private void otrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otrosActionPerformed
        tipoDeToken = "Otros"; mostrarLista();
    }//GEN-LAST:event_otrosActionPerformed

    private void activarGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activarGraficaActionPerformed
        GraficaFronted grafica = new GraficaFronted();
        grafica.graficarToken(tipoDeTokenTitulo, tipoDeToken, tokensIdentificados, tipoDeTokenDescripcion, verGrafica, activarGrafica, seleccionar, numeroDeToken, obtenerNumeroDeToken(), identificadores, aritmeticos, comparacion, asignacion, palabrasClave, constantes, otros, comentarios);     
    }//GEN-LAST:event_activarGraficaActionPerformed

    private void seleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionarActionPerformed
        GraficaFronted grafica = new GraficaFronted();
        grafica.seleccionarToken(contador, tokensIdentificados, tipoDeToken, activarGrafica, seleccionar, numeroDeToken, obtenerNumeroDeToken(), identificadores, aritmeticos, comparacion, asignacion, palabrasClave, constantes, otros, comentarios);
    }//GEN-LAST:event_seleccionarActionPerformed

    private void comentariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comentariosActionPerformed
        tipoDeToken = "Comentario"; mostrarLista();
    }//GEN-LAST:event_comentariosActionPerformed
 
    private void mostrarLista() {
        panelDeTokens.setText("");
        GraficaFronted grafica = new GraficaFronted();
        contador = grafica.mostrarListaDeOpciones(numeroDeToken, tokensIdentificados, panelDeTokens, tipoDeToken);
    }

    public int obtenerNumeroDeToken() {
        return (int) numeroDeToken.getValue();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton activarGrafica;
    private javax.swing.JButton aritmeticos;
    private javax.swing.JButton asignacion;
    private javax.swing.JButton comentarios;
    private javax.swing.JButton comparacion;
    private javax.swing.JButton constantes;
    private javax.swing.JButton identificadores;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner numeroDeToken;
    private javax.swing.JButton otros;
    private javax.swing.JButton palabrasClave;
    private javax.swing.JPanel panelDeGraficas;
    private javax.swing.JTextArea panelDeTokens;
    private javax.swing.JButton seleccionar;
    private javax.swing.JLabel tipoDeTokenDescripcion;
    private javax.swing.JLabel tipoDeTokenTitulo;
    private javax.swing.JLabel verGrafica;
    // End of variables declaration//GEN-END:variables
}
