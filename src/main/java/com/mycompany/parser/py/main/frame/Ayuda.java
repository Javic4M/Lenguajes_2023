package com.mycompany.parser.py.main.frame;

public class Ayuda extends javax.swing.JFrame {
    
    public Ayuda() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        panelDeConsulta.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelDeConsulta = new javax.swing.JTextArea();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Ayuda");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Consulta"));

        panelDeConsulta.setColumns(20);
        panelDeConsulta.setRows(5);
        panelDeConsulta.setText("\n   - En la ventana principal podrá visualizar el código que usted ingrese de manera escrita o que \n     aparezca al momento de cargar un archivo de texto en la sección de (Archivo) en la opción \n     (Cargar Archivos). \n          Dentro del área Visualizador usted podrá realizar todos los cambios que considere necesarios \n     para el funcionamiento del código fuente luego deberá de presionar el botón (Play) para generar \n     los tokens y verificar que no haya ningún error léxico que sea necesario corregir,  luego de esto \n     podrá dirigirse al apartado de (Generar Gráfica) para visualizar los tokens creados.\n\n   - En el aparta de (Generar Gráfica) usted podrá visualizar todos los tokens que el programa haya \n      reconocido, para ver la gráfica deberá de seleccionar primero una de las categorías de tokens que \n      se encuentran en la parte izquierda de la pantalla posteriormente se le mostrara una lista con los \n      tokens que se encontraron en esa categoría, tome en cuenta que si no aparece ningún token \n      significa que no existen tokens de ese tipo en el texto examinado, luego deberá de seleccionar el \n      número de tokens que desea visualizar y deberá de darle en el botón Play para observarlo.\n           El proceso se repite las veces que considere necesarios o luego de haber visualizado todos los \n      tokens.");
        jScrollPane1.setViewportView(panelDeConsulta);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(262, 262, 262))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea panelDeConsulta;
    // End of variables declaration//GEN-END:variables
}
