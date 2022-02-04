/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author alfon
 */
public class ControlPanel extends JPanel {
    //DECLARE VARIABLES
    private JButton stop, pause, paletteChooserButton;
    private JSlider flameSliderRate, viewerSliderRate, coolSlider;
    private JLabel title, flameLabelRate, viewerLabelRate, coolLabel, paletteChooserLabel;
    private MyFlame myFlame;
    private Border line = new LineBorder(Color.RED);
    private Border margin = new EmptyBorder(5, 15, 5, 15);
    private Border compound = new CompoundBorder(line, margin);
    private BufferedImage bufferedImage;
    private JTabbedPane tabs;
    private JPanel panelFlame, panelConvolution;
    
    

    //CONSTRUCTOR
    public ControlPanel() {
        //Get the background image from the files
        try {
            bufferedImage = (BufferedImage)ImageIO.read(new File("IMG/bgcontrolpanel.png"));
        } catch (IOException ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //SET UP THE CONTROLPANEL
        controlPanelSetUp();
        controlPanelFlameSetUp();
        controlPanelConvolutionSetUp();
        titleSetUp();
        stopSetUp();
        pauseSetUp();
        viewerRateSetUp();
        flameRateSetUp();
        createCoolSetUp();
        paletteChooserSetUp();
        audioButtonSetUp();
        tabsSetUp();

        
    }
    
    //PUBLIC METHODS
    //Getters and setters
    public void setMyFlame(MyFlame myFlame) {
        this.myFlame = myFlame;
    }
    

    
    //PRIVATE METHODS
    //contolPanelSetUp: sets the base of this class
    private void controlPanelSetUp() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        
    }
    
    private void controlPanelFlameSetUp() {
        panelFlame = new JPanel();
        panelFlame.setLayout(new GridBagLayout());
        panelFlame.setBackground(null);
        panelFlame.setVisible(true);
    }

    private void controlPanelConvolutionSetUp() {
        panelConvolution = new JPanel();
        panelConvolution.setLayout(new GridBagLayout());
        panelConvolution.setBackground(null);
        panelConvolution.setVisible(true);
    }

    
    
    private void tabsSetUp() {
        GridBagConstraints constraints = new GridBagConstraints();  
        
        tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.addTab("Fire", panelFlame);
        tabs.addTab("Convolution", panelConvolution);
        tabs.setForeground(Color.WHITE);
        tabs.setBackground(Color.RED.darker().darker().darker().darker());
        constraints.gridx = 0; 
        constraints.gridy = 1; 
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.BOTH;
        //set the title
        this.add(tabs, constraints);

        

    }

    
    //titleSetUp: set up the title
    private void titleSetUp() {
        //Declare the grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();  
        //Set up title
        title = new JLabel("WITCHES N'STITCHES");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 25));
        title.setBackground(Color.BLACK);
        title.setOpaque(true);
        //set up constraints
        constraints.gridx = 0; 
        constraints.gridy = 0; 
        constraints.weightx = 1;
        constraints.weighty = 0.1;
        constraints.gridwidth = 1;
        //set the title
        this.add(title, constraints);
    }

    
    //stopSetUp
    private void stopSetUp() {
        //Declare the grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();  
        //set up stop
        stop = new JButton("Exit");
        stop.setBorderPainted(true);
        stop.setFocusPainted(false);
        stop.setContentAreaFilled(true);
        stop.setBackground(Color.WHITE);
        stop.setForeground(Color.BLACK);
        stop.setBorder(compound);
        
        //set up constraints
        constraints.gridx = 0; 
        constraints.gridy = 0; 
        constraints.ipady = 10;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        panelFlame.add(stop , constraints);
        //add the button listener
        stop.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                myFlame.setExit();
            }  
        });  
        
        
        
    }

    
    //pauseSetUp: set up pause
    private void pauseSetUp() {
        //Declare the grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();  
        //set up pause
        pause = new JButton("Pause");
        pause.setBorderPainted(true);
        pause.setFocusPainted(false);
        pause.setContentAreaFilled(true);
        pause.setBackground(Color.WHITE);
        pause.setForeground(Color.BLACK);
        pause.setBorder(compound);
        
        //set up contraints
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1; 
        constraints.gridy = 0; 
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;
        constraints.gridwidth = 5;
        constraints.ipady = 10;
        //add the pause button
        panelFlame.add(pause , constraints);
        //add the button listener
        pause.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                myFlame.setPause();
            }  
        });  

        
    }

    
    
    
    //viewerRateSetUp: Set up the viewer rate slider
    private void viewerRateSetUp() {
        //Declare the grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();  
        //set up the label
        viewerLabelRate = new JLabel("Viewer Rate: ");
        viewerLabelRate.setForeground(Color.white);
        //set up the constrains
        constraints.gridx = 0;
        constraints.gridy = 1; 
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        //add the label
        panelFlame.add(viewerLabelRate, constraints);
        
        //set up the slider
        viewerSliderRate = new JSlider(JSlider.HORIZONTAL, 0, 200, 50);
        viewerSliderRate.setMinorTickSpacing(25);  
        viewerSliderRate.setMajorTickSpacing(50);  
        viewerSliderRate.setPaintTicks(true);  
        viewerSliderRate.setPaintLabels(true); 
        viewerSliderRate.setOpaque(false);
        viewerSliderRate.setForeground(Color.WHITE);
        viewerSliderRate.setBackground(Color.red);
        viewerSliderRate.setValue(20);
        
        //set up constraints
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;
        //add the slide
        panelFlame.add(viewerSliderRate, constraints);
        //set the slide change listener
        viewerSliderRate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                myFlame.setViewerRate(viewerSliderRate.getValue());
        
           }
        });
        
    }

    //flameRateSetUp: set the flame rate slider up
    private void flameRateSetUp() {
        //Declare the grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();  
        //set up the label
        flameLabelRate = new JLabel("Flame Rate: ");
        flameLabelRate.setForeground(Color.white);
        //set up constraints
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        //add the label
        panelFlame.add(flameLabelRate, constraints);
        
        //set up the slider
        flameSliderRate = new JSlider(JSlider.HORIZONTAL, 0, 200, 50);
        flameSliderRate.setMinorTickSpacing(25);
        flameSliderRate.setMajorTickSpacing(50); 
        flameSliderRate.setPaintTicks(true);  
        flameSliderRate.setPaintLabels(true); 
        flameSliderRate.setOpaque(false);
        flameSliderRate.setForeground(Color.WHITE);
        flameSliderRate.setBackground(Color.red);
        flameSliderRate.setValue(50);
        //set up the constraints
        constraints.gridwidth = 5;
        constraints.gridx = 1; 
        constraints.gridy = 2;
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;
        //add the flame slider
        panelFlame.add(flameSliderRate, constraints);
        //set the slide change listener
        flameSliderRate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                myFlame.setFlameRate(flameSliderRate.getValue());
        
            }   
        });
    }
    
    //createCool: set up the cool slider
    private void createCoolSetUp() {
        //Declare the grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();  
        //set up the label
        coolLabel = new JLabel("Flame Intensity: ");
        coolLabel.setForeground(Color.WHITE);
        //set up constraints
        constraints.gridx = 0; 
        constraints.gridy = 3;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        panelFlame.add(coolLabel, constraints);
        
        //set up the slider
        coolSlider = new JSlider(JSlider.HORIZONTAL, 20, 100, 20);
        coolSlider.setMinorTickSpacing(10);
        coolSlider.setMajorTickSpacing(20);  
        coolSlider.setPaintTicks(true);  
        coolSlider.setPaintLabels(true); 
        coolSlider.setPaintTicks(true);  
        coolSlider.setPaintLabels(true); 
        coolSlider.setOpaque(false);
        coolSlider.setForeground(Color.WHITE);
        coolSlider.setBackground(Color.red);
        coolSlider.setValue(80);
        //set up slider constraints
        constraints.gridwidth = 5;
        constraints.gridx = 1; 
        constraints.gridy = 3;
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;
        panelFlame.add(coolSlider, constraints);
        //add slider change listener
        coolSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                myFlame.setFlameCoolAmount(coolSlider.getValue());
        
            }   
        });
    }
    

    //paletteChooserSetUp: set up the palette chooser
    private void paletteChooserSetUp() {
        //Declare the grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();  
        //set up label
        paletteChooserLabel = new JLabel("Palette Chooser: ");
        paletteChooserLabel.setForeground(Color.white);
        //set up label constraints
        constraints.gridx = 0;
        constraints.gridy = 4; 
        constraints.weightx = 1;
        constraints.weighty = 0.1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panelFlame.add(paletteChooserLabel, constraints);
        
        //Set the palette button changers
        setPaletteButton(1);
        setPaletteButton(2);
        setPaletteButton(3);
        setPaletteButton(4);
        setPaletteButton(5);

    }
    //setPaletteButton: sets the palette chooser butons up
    private void setPaletteButton(int i) {
        //Declare the grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();  
        //create the buttons
        paletteChooserButton = new JButton(""+i);
        paletteChooserButton.setBorderPainted(true);
        paletteChooserButton.setFocusPainted(false);
        paletteChooserButton.setContentAreaFilled(true);
        paletteChooserButton.setBackground(Color.WHITE);
        paletteChooserButton.setForeground(Color.BLACK);
        paletteChooserButton.setBorder(compound);
        //its constraints
        constraints.gridx = i;
        constraints.gridy = 4; 
        constraints.weightx = 0;
        constraints.weighty = 0.1;
        panelFlame.add(paletteChooserButton, constraints);
        
        //Add the button listener for every palette button
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

    private void audioButtonSetUp() {
       //Declare the grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();  
        //set up audio
        JButton audio = new JButton();
        audio.setBorderPainted(false);
        audio.setFocusPainted(false);
        audio.setContentAreaFilled(false);
        audio.setIcon( new ImageIcon("IMG/nota.png") );
        //set up constraints
        constraints.gridx = 1; 
        constraints.gridy = 5; 
        constraints.ipady = 10;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridwidth = 3;
        constraints.weightx = 0;
        constraints.weighty = 0.1;
        panelFlame.add(audio , constraints);
        //add the button listener
        audio.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                myFlame.songController();
            }  
        });  
    }






    
    
}
