
package com.mycompany.parser.py.main.jpanelimagen;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelImagen extends JLabel {
    
    private String tipo;
    private int x, y;
    
    public JPanelImagen(JPanel panel, String tipo) {
        this.tipo = tipo;
        this.x = panel.getWidth();
        this.y = panel.getHeight();
        this.setSize(x,y);
    }
    
    public void paint(Graphics g) {
        ImageIcon imagen = new ImageIcon(getClass().getResource("/com/mycompany/parser/py/main/frame/" + tipo + ".png"));
        g.drawImage(imagen.getImage(), 0, 0, x, y, null);
    }
}
