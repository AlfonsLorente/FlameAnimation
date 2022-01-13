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
import javax.swing.JColorChooser;
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
    private JButton stop, pause, paletteChooserButton;
    private JSlider flameSliderRate, viewerSliderRate, coolSlider;
    private JLabel flameLabelRate, viewerLabelRate, coolLabel, paletteChooserLabel;
    private MyFlame myFlame;
    


    public ControlPanel() {
        controlPanelSetUp();
       
        stopSetUp();
        pauseSetUp();
        viewerRateSetUp();
        flameRateSetUp();
        paletteChooserSetUp();
        createCool();
        
        
        
    }

    public void setMyFlame(MyFlame myFlame) {
        this.myFlame = myFlame;
    }
    
    
    
    
    
    
    @Override
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.setColor(Color.BLACK);
        
        
    }

    private void pauseSetUp() {
        GridBagConstraints constraints = new GridBagConstraints();  

        pause = new JButton("Pause");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;
        constraints.gridwidth = 5;
        constraints.ipady = 10;

        this.add(pause , constraints);
        pause.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                myFlame.setPause();
            }  
        });  
        constraints.gridwidth = 1;

        
    }

    private void stopSetUp() {
        GridBagConstraints constraints = new GridBagConstraints();  

        stop = new JButton("STOP");
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.ipady = 10;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        this.add(stop , constraints);
        this.add(stop , constraints);
        stop.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                myFlame.setExit();
            }  
        });  
        
        
        
    }

    private void viewerRateSetUp() {
        GridBagConstraints constraints = new GridBagConstraints();  
        viewerLabelRate = new JLabel("Viewer Rate: ");
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 1; // El área de texto empieza en la fila cero
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        this.add(viewerLabelRate, constraints);
        
        viewerSliderRate = new JSlider(JSlider.HORIZONTAL, 0, 200, 50);
        viewerSliderRate.setMinorTickSpacing(25);  
        viewerSliderRate.setMajorTickSpacing(50);  
        constraints.gridwidth = 5;
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
        GridBagConstraints constraints = new GridBagConstraints();  
        flameLabelRate = new JLabel("Flame Rate: ");
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 2; // El área de texto empieza en la fila cero
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        this.add(flameLabelRate, constraints);
        
        flameSliderRate = new JSlider(JSlider.HORIZONTAL, 0, 200, 50);
        flameSliderRate.setMinorTickSpacing(25);
        flameSliderRate.setMajorTickSpacing(50);  
        constraints.gridwidth = 5;
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
    
    
    private void createCool() {
        GridBagConstraints constraints = new GridBagConstraints();  
        coolLabel = new JLabel("Flame Intensity: ");
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 3; // El área de texto empieza en la fila cero
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        this.add(coolLabel, constraints);
        
        coolSlider = new JSlider(JSlider.HORIZONTAL, 20, 100, 20);
        coolSlider.setMinorTickSpacing(10);
        coolSlider.setMajorTickSpacing(20);  
        constraints.gridwidth = 5;
        coolSlider.setPaintTicks(true);  
        coolSlider.setPaintLabels(true); 
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 3; // El área de texto empieza en la fila cero
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;
        this.add(coolSlider, constraints);
        coolSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                myFlame.setFlameCoolAmount(coolSlider.getValue());
        
            }   
        });
    }
    

    private void controlPanelSetUp() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.red);
        this.setVisible(true);
    }

    private void paletteChooserSetUp() {
        GridBagConstraints constraints = new GridBagConstraints();  
        paletteChooserLabel = new JLabel("Palette Chooser: ");
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 4; // El área de texto empieza en la fila cero
        constraints.weightx = 1;
        constraints.weighty = 0.1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        this.add(paletteChooserLabel, constraints);
        
        setPaletteButton(1);
        setPaletteButton(2);
        setPaletteButton(3);
        setPaletteButton(4);
        setPaletteButton(5);

    }

    private void setPaletteButton(int i) {
        GridBagConstraints constraints = new GridBagConstraints();  
        paletteChooserButton = new JButton(""+i);
        constraints.gridx = i; // El área de texto empieza en la columna cero.
        constraints.gridy = 4; // El área de texto empieza en la fila cero
        constraints.weightx = 0;
        constraints.weighty = 0.1;
       // constraints.
        this.add(paletteChooserButton, constraints);
        paletteChooserButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Color color;
                switch(i){
                    case 1:
                        color = JColorChooser.showDialog(ControlPanel.this, "Choose a color", myFlame.getC1());
                        if (color != null){
                            myFlame.setC1(color);
                        }  
                        break;
                    case 2:
                        color = JColorChooser.showDialog(ControlPanel.this, "Choose a color", myFlame.getC2());                       
                        if (color != null){
                            myFlame.setC2(color);
                        }                          
                        break;
                    case 3:
                        color = JColorChooser.showDialog(ControlPanel.this, "Choose a color", myFlame.getC3());
                          if (color != null){
                            myFlame.setC3(color);
                        }                          
                        break;
                    case 4:
                        color = JColorChooser.showDialog(ControlPanel.this, "Choose a color", myFlame.getC4());
                        if (color != null){
                            myFlame.setC4(color);
                        }                          
                        break;
                    case 5:
                        color = JColorChooser.showDialog(ControlPanel.this, "Choose a color", myFlame.getC5());
                        if (color != null){
                            myFlame.setC5(color);
                        }                          
                        break;
                }
            }  
              
        });  
    }



    
    
}
