
package com.mycompany.parser.py.main.frame;

import com.mycompany.parser.py.main.crearVista.Archivo;
import com.mycompany.parser.py.main.lista.ListaElementos;
import com.mycompany.parser.py.main.lista.ListaElementosExcepcion;
import com.mycompany.parser.py.main.tokens.Token;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Analizador extends javax.swing.JFrame {

    private ListaElementos<Token> tokensIdentificados = new ListaElementos<>();
    private ListaElementos<String> errores = new ListaElementos<>();
    private ListaElementos<String> lista;
    private String pathEntrante = "";
    
    public Analizador() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        panelDeTexto.setText("");
        activarReconocimientoDeTokens.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/com/mycompany/parser/py/main/frame/play.png")).getImage().getScaledInstance(-1, -1, java.awt.Image.SCALE_SMOOTH)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        visualizador = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelDeTexto = new javax.swing.JTextArea();
        panelDeErrores = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelErrores = new javax.swing.JTextArea();
        activarReconocimientoDeTokens = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        cargarArchivos = new javax.swing.JMenuItem();
        generarGrafica = new javax.swing.JMenu();
        visualizarGraficas = new javax.swing.JMenuItem();
        ayuda = new javax.swing.JMenu();
        consulta = new javax.swing.JMenuItem();
        acercaDe = new javax.swing.JMenu();
        informacion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        visualizador.setBorder(javax.swing.BorderFactory.createTitledBorder("Visualizador"));

        panelDeTexto.setColumns(20);
        panelDeTexto.setRows(5);
        jScrollPane1.setViewportView(panelDeTexto);

        javax.swing.GroupLayout visualizadorLayout = new javax.swing.GroupLayout(visualizador);
        visualizador.setLayout(visualizadorLayout);
        visualizadorLayout.setHorizontalGroup(
            visualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(visualizadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        visualizadorLayout.setVerticalGroup(
            visualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, visualizadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelDeErrores.setBorder(javax.swing.BorderFactory.createTitledBorder("Errores"));

        panelErrores.setColumns(20);
        panelErrores.setRows(5);
        jScrollPane2.setViewportView(panelErrores);

        javax.swing.GroupLayout panelDeErroresLayout = new javax.swing.GroupLayout(panelDeErrores);
        panelDeErrores.setLayout(panelDeErroresLayout);
        panelDeErroresLayout.setHorizontalGroup(
            panelDeErroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeErroresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelDeErroresLayout.setVerticalGroup(
            panelDeErroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeErroresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );

        activarReconocimientoDeTokens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activarReconocimientoDeTokensActionPerformed(evt);
            }
        });

        jMenu2.setText("Archivo");

        cargarArchivos.setText("Cargar Archivos");
        cargarArchivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarArchivosActionPerformed(evt);
            }
        });
        jMenu2.add(cargarArchivos);

        jMenuBar1.add(jMenu2);

        generarGrafica.setText("Generar Gráfica");

        visualizarGraficas.setText("Visualizar Gráficas");
        visualizarGraficas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visualizarGraficasActionPerformed(evt);
            }
        });
        generarGrafica.add(visualizarGraficas);

        jMenuBar1.add(generarGrafica);

        ayuda.setText("Ayuda");

        consulta.setText("Consulta");
        consulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaActionPerformed(evt);
            }
        });
        ayuda.add(consulta);

        jMenuBar1.add(ayuda);

        acercaDe.setText("Acerca de");

        informacion.setText("Informacion");
        informacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informacionActionPerformed(evt);
            }
        });
        acercaDe.add(informacion);

        jMenuBar1.add(acercaDe);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelDeErrores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(visualizador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(activarReconocimientoDeTokens, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(activarReconocimientoDeTokens, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(visualizador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDeErrores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarArchivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarArchivosActionPerformed
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filtrado = new FileNameExtensionFilter("txt", "txt");
        jFileChooser.setFileFilter(filtrado);
        
        int respuesta = jFileChooser.showOpenDialog(this);

        if (respuesta == JFileChooser.APPROVE_OPTION) {
            pathEntrante = jFileChooser.getSelectedFile().getPath();
            Archivo archivo = new Archivo();
            lista = archivo.crearVisualizacionDeArchivo(pathEntrante);
            mostrarArchivo();
        }
    }//GEN-LAST:event_cargarArchivosActionPerformed

    private void visualizarGraficasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualizarGraficasActionPerformed
        Graficas visualizar = new Graficas(tokensIdentificados);
        visualizar.setVisible(true);
    }//GEN-LAST:event_visualizarGraficasActionPerformed

    private void activarReconocimientoDeTokensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activarReconocimientoDeTokensActionPerformed
        if (!"".equals(obtenerTextoEscrito())) {
            tokensIdentificados = new ListaElementos<>();
            Archivo crear = new Archivo();
            crear.organizarCadena(obtenerTextoEscrito(), tokensIdentificados, errores);
            mostrarErrores();
        } else {
            JOptionPane.showMessageDialog(this,"Debes subir un Archivo o Escribir un Texto","Falta de Información",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_activarReconocimientoDeTokensActionPerformed

    private void consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaActionPerformed
        Ayuda consulta = new Ayuda();
        consulta.setVisible(true);
    }//GEN-LAST:event_consultaActionPerformed

    private void informacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informacionActionPerformed
        AcercaDe informacion = new AcercaDe();
        informacion.setVisible(true);
    }//GEN-LAST:event_informacionActionPerformed

    private void mostrarErrores() {
        for (int i = 1; i <= errores.getLongitud(); i++) {
            try {
                if (i == 1) {
                    panelErrores.append((i + "| " + errores.obtenerContenido(i)));
                } else {
                    panelErrores.append(("\n" + i + "| " + errores.obtenerContenido(i)));
                }
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error en Mostrar Archivo");
            }
        }
    }
    
    private void mostrarArchivo() {
        for (int i = 1; i <= lista.getLongitud(); i++) {
            try {
                if (i == 1) {
                    panelDeTexto.append((lista.obtenerContenido(i)));
                } else {
                    panelDeTexto.append(("\n" + lista.obtenerContenido(i)));
                }
            } catch (ListaElementosExcepcion ex) {
                System.out.println("Error en Mostrar Archivo");
            }
        }
    }
    
    public String obtenerTextoEscrito() {
        return panelDeTexto.getText();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu acercaDe;
    private javax.swing.JButton activarReconocimientoDeTokens;
    private javax.swing.JMenu ayuda;
    private javax.swing.JMenuItem cargarArchivos;
    private javax.swing.JMenuItem consulta;
    private javax.swing.JMenu generarGrafica;
    private javax.swing.JMenuItem informacion;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelDeErrores;
    private javax.swing.JTextArea panelDeTexto;
    private javax.swing.JTextArea panelErrores;
    private javax.swing.JPanel visualizador;
    private javax.swing.JMenuItem visualizarGraficas;
    // End of variables declaration//GEN-END:variables
}
