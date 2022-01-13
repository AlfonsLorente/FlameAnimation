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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author alfon
 */
public class ControlPanel extends JPanel {
    private JButton stop, pause;
    private JSlider flameSliderRate, viewerSliderRate;
    private JLabel flameLabelRate, viewerLabelRate;
    private GridBagConstraints constraints = new GridBagConstraints();  
    private MyFlame myFlame;
    


    public ControlPanel() {
        this.setLayout(new GridBagLayout());
       // this.setPreferredSize(preferredSize);
        stopSetUp();
        pauseSetUp();
        viewerRateSetUp();
        flameRateSetUp();
        
        
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
        pause = new JButton("Pause");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;
        constraints.ipady = 10;

        this.add(pause , constraints);
        pause.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                myFlame.setPause();
            }  
        });  
        
    }

    private void stopSetUp() {
        stop = new JButton("STOP");
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.ipady = 10;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        this.add(stop , constraints);
        
        
        
        
    }

    private void viewerRateSetUp() {
        viewerLabelRate = new JLabel("Viewer Rate: ");
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 1; // El área de texto empieza en la fila cero
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        this.add(viewerLabelRate, constraints);
        
        viewerSliderRate = new JSlider(JSlider.HORIZONTAL, 0, 200, 50);
        viewerSliderRate.setMinorTickSpacing(25);  
        viewerSliderRate.setMajorTickSpacing(50);  
        viewerSliderRate.setPaintTicks(true);  
        viewerSliderRate.setPaintLabels(true); 
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 1; // El área de texto empieza en la fila cero
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;
        this.add(viewerSliderRate, constraints);
        viewerSliderRate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                myFlame.setViewerRate(viewerSliderRate.getValue());
        
           }
        });
        
    }

    private void flameRateSetUp() {
        flameLabelRate = new JLabel("Flame Rate: ");
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 2; // El área de texto empieza en la fila cero
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        this.add(flameLabelRate, constraints);
        
        flameSliderRate = new JSlider(JSlider.HORIZONTAL, 0, 200, 50);
        flameSliderRate.setMinorTickSpacing(25);  
        flameSliderRate.setMajorTickSpacing(50);  
        flameSliderRate.setPaintTicks(true);  
        flameSliderRate.setPaintLabels(true); 
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 2; // El área de texto empieza en la fila cero
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;
        this.add(flameSliderRate, constraints);
        flameSliderRate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                myFlame.setFlameRate(flameSliderRate.getValue());
        
            }   
        });
    }



    
    
}
