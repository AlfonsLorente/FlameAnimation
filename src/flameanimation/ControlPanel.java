/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author alfon
 */
public class ControlPanel extends JPanel {
    private JButton stop = new JButton("STOP");
    private JButton pause = new JButton("Pause");
    private GridBagConstraints constraints = new GridBagConstraints();  
    private MyFlame myFlame;


    public ControlPanel() {
        this.setLayout(new GridBagLayout());
        stopSetUp();
        pauseSetUp();
        
        
        
    }

    public void setMyFlame(MyFlame myFlame) {
        this.myFlame = myFlame;
    }
    
    
    
    
    @Override
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.setColor(Color.RED);
        
    }

    private void pauseSetUp() {
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;

        this.add(pause , constraints);
        pause.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                myFlame.setPause();
            }  
        });  
        
    }

    private void stopSetUp() {
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        this.add(stop , constraints);
        stop.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                myFlame.setStop();
            }  
        });  
        
        
        
    }
    
    
}
