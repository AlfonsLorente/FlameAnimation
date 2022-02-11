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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author alfon
 */
public class ControlPanel extends JPanel implements ActionListener{
    //DECLARE VARIABLES
    private JButton stop, pause, paletteChooserButton, fileChooserButton;
    private JSlider flameSliderRate, viewerSliderRate, coolSlider;
    private JLabel title, flameLabelRate, viewerLabelRate, coolLabel, paletteChooserLabel, colorChooserLabel;
    private MyFlame myFlame;
    private Border line = new LineBorder(Color.RED);
    private Border margin = new EmptyBorder(5, 15, 5, 15);
    private Border compound = new CompoundBorder(line, margin);
    private BufferedImage bufferedImage;
    private JTabbedPane tabs;
    private JPanel panelFlame, panelConvolution;
    private JMenu convolutionMenu;
    private JMenuItem i1, i2, i3, i4, i5, i6, i7;
    private JMenuBar convolutionMenuBar = new JMenuBar();
    private JCheckBox redCheckbox, greenCheckbox, blueCheckbox;
    private JFileChooser fileChooser;
    private JLabel luminanceLabel;
    private JSlider luminanceSlider;
    
    

    //CONSTRUCTOR
    public ControlPanel() {
        
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
        flameAnimationLuminance();
        
        audioButtonSetUp();
        fileChooserSetUp();
        userTextInputSetUp();
        convoluterSetUp();
        
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
        constraints.gridwidth = 6;
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
        viewerSliderRate.setValue(55);
        
        //set up constraints
        constraints.gridwidth = 6;
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
        flameSliderRate.setValue(55);
        //set up the constraints
        constraints.gridwidth = 6;
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
        coolSlider.setValue(45);
        //set up slider constraints
        constraints.gridwidth = 6;
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
    
     private void flameAnimationLuminance() {
         //Declare the grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();  
        //set up the label
        luminanceLabel = new JLabel("Luminance min: ");
        luminanceLabel.setForeground(Color.WHITE);
        //set up constraints
        constraints.gridx = 0; 
        constraints.gridy = 5;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        panelFlame.add(luminanceLabel, constraints);
        
        //set up the slider
        luminanceSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 20);
        luminanceSlider.setMinorTickSpacing(10);
        luminanceSlider.setMajorTickSpacing(20);  
        luminanceSlider.setPaintTicks(true);  
        luminanceSlider.setPaintLabels(true); 
        luminanceSlider.setPaintTicks(true);  
        luminanceSlider.setPaintLabels(true); 
        luminanceSlider.setOpaque(false);
        luminanceSlider.setForeground(Color.WHITE);
        luminanceSlider.setBackground(Color.red);
        luminanceSlider.setValue(80);
        //set up slider constraints
        constraints.gridwidth = 6;
        constraints.gridx = 1; 
        constraints.gridy = 5;
        constraints.weightx = 0.9;
        constraints.weighty = 0.1;
        panelFlame.add(luminanceSlider, constraints);
        //add slider change listener
        luminanceSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                myFlame.setLuminanceMin(luminanceSlider.getValue());
        
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
        constraints.gridy = 6; 
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



    
    private void fileChooserSetUp() {
        GridBagConstraints constraints = new GridBagConstraints();
        fileChooserButton = new JButton("Choose an image");
        fileChooserButton.setBorderPainted(true);
        fileChooserButton.setFocusPainted(false);
        fileChooserButton.setContentAreaFilled(true);
        fileChooserButton.setBackground(Color.WHITE);
        fileChooserButton.setForeground(Color.BLACK);
        fileChooserButton.setBorder(compound);
         //set up constraints
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 3;
        
        panelConvolution.add(fileChooserButton, constraints);
        fileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser("IMG/", FileSystemView.getFileSystemView());
                int r = fileChooser.showSaveDialog(null);
 
            // if the user selects a file
            if (r == JFileChooser.APPROVE_OPTION){
                // set the label to the path of the selected file
                myFlame.changeImage(fileChooser.getSelectedFile().getAbsolutePath()); 
            }
                
            }
        });
        
    }
    
   
    private void userTextInputSetUp() {
        GridBagConstraints constraintsLabel = new GridBagConstraints();
        JLabel textInputTitle = new JLabel("Set your own filter: ");
        
        fileChooserButton.setContentAreaFilled(true);
        textInputTitle.setForeground(Color.WHITE);
         //set up constraints
        constraintsLabel.gridx = 0;
        constraintsLabel.gridy = 1;
        constraintsLabel.weightx = 1;
        constraintsLabel.weighty = 0.5;
        constraintsLabel.gridwidth = 3;
        panelConvolution.add(textInputTitle, constraintsLabel);
        
        
        JTextField textInput1 = setTextInput(0,2);
        JTextField textInput2 = setTextInput(1,2);
        JTextField textInput3 = setTextInput(2,2);
        JTextField textInput4 = setTextInput(0,3);
        JTextField textInput5 = setTextInput(1,3);
        JTextField textInput6 = setTextInput(2,3);
        JTextField textInput7 = setTextInput(0,4);
        JTextField textInput8 = setTextInput(1,4);
        JTextField textInput9 = setTextInput(2,4);
        
        
        GridBagConstraints constraintsDivText = new GridBagConstraints();
        JLabel divText = new JLabel("Division: ");
        divText.setForeground(Color.WHITE);
         //set up constraints
        constraintsDivText.gridx = 0;
        constraintsDivText.gridy = 5;
        constraintsDivText.weightx = 1;
        constraintsDivText.weighty = 0.5;
        constraintsDivText.gridwidth = 1;
        panelConvolution.add(divText, constraintsDivText);
        
        JTextField divInput = setTextInput(1,5);
        
        
        GridBagConstraints constraintsButton = new GridBagConstraints();
        JButton sendTextInput = new JButton("Send");
        sendTextInput.setBorderPainted(true);
        sendTextInput.setFocusPainted(false);
        sendTextInput.setContentAreaFilled(true);
        sendTextInput.setBackground(Color.WHITE);
        sendTextInput.setForeground(Color.BLACK);
        sendTextInput.setBorder(compound);
         //set up constraints
        constraintsButton.gridx = 2;
        constraintsButton.gridy = 5;
        constraintsButton.weightx = 1;
        constraintsButton.weighty = 0.5;
        constraintsButton.gridwidth = 1;
        
        panelConvolution.add(sendTextInput, constraintsButton);
        sendTextInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float[][] kernel = new float[3][3];
                kernel[0][0] = Float.parseFloat(textInput1.getText().toString());
                kernel[0][1] = Float.parseFloat(textInput2.getText().toString());
                kernel[0][2] = Float.parseFloat(textInput3.getText().toString());
                kernel[1][0] = Float.parseFloat(textInput4.getText().toString());
                kernel[1][1] = Float.parseFloat(textInput5.getText().toString());
                kernel[1][2] = Float.parseFloat(textInput6.getText().toString());
                kernel[2][0] = Float.parseFloat(textInput7.getText().toString());
                kernel[2][1] = Float.parseFloat(textInput8.getText().toString());
                kernel[2][2] = Float.parseFloat(textInput9.getText().toString());
                float div = Float.parseFloat(divInput.getText().toString());
                myFlame.changeConvolutionKernel(kernel, div);
                
                
            }
        });
        
    }
    
    
    private JTextField setTextInput(int x, int y ){
        GridBagConstraints constraints = new GridBagConstraints();
        JTextField tf = new JTextField("1");
        tf.setHorizontalAlignment(JTextField.CENTER);
        //set up constraints
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.weightx = 1;
        constraints.weighty = 0.3;
        constraints.gridwidth = 1;
        constraints.ipadx = 30;
        constraints.ipady = 30;

        panelConvolution.add(tf, constraints);
        return tf;
        
        
    }

    
    
    
    //stopSetUp
    private void convoluterSetUp() {
        setConvolutionList();
        setConvolutionColor();

    }

    //setConvolutionList: Adds the menu of convolution types
    private void setConvolutionList() {
        GridBagConstraints constraints = new GridBagConstraints();
        convolutionMenu = new JMenu("Convolution type");
        i1 = new JMenuItem("Sharpen");
        i2 = new JMenuItem("Smooth");
        i3 = new JMenuItem("Raise");
        i4 = new JMenuItem("Outline");
        i5 = new JMenuItem("Emboss");
        i6 = new JMenuItem("Blur");
        i7 = new JMenuItem("Center Points");

        convolutionMenu.add(i1);
        convolutionMenu.add(i2);
        convolutionMenu.add(i3);
        convolutionMenu.add(i4);
        convolutionMenu.add(i5);
        convolutionMenu.add(i6);
        convolutionMenu.add(i7);

        convolutionMenu.setForeground(Color.BLACK);


        convolutionMenuBar.add(convolutionMenu);
        convolutionMenuBar.setBorderPainted(true);
        convolutionMenuBar.setBackground(Color.WHITE);
        convolutionMenuBar.setForeground(Color.BLACK);
        convolutionMenuBar.setBorder(compound);

        //set up constraints
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 3;
        panelConvolution.add(convolutionMenuBar, constraints);

        i1.addActionListener(this);
        i2.addActionListener(this);
        i3.addActionListener(this);
        i4.addActionListener(this);
        i5.addActionListener(this);
        i6.addActionListener(this);
        i7.addActionListener(this);


    }

    //actionPerformed: Get the menu item clicked on the convolution menu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Sharpen")) {
            myFlame.changeConvolutedImage(Convolution.Type.SHARPEN);

        } else if (e.getActionCommand().equals("Smooth")) {
            myFlame.changeConvolutedImage(Convolution.Type.SMOOTH);

        } else if (e.getActionCommand().equals("Raise")) {
            myFlame.changeConvolutedImage(Convolution.Type.RAISE);

        } else if (e.getActionCommand().equals("Outline")) {
            myFlame.changeConvolutedImage(Convolution.Type.OUTLINE);

        } else if (e.getActionCommand().equals("Emboss")) {
            myFlame.changeConvolutedImage(Convolution.Type.EMBOSS);

        } else if (e.getActionCommand().equals("Blur")) {
            myFlame.changeConvolutedImage(Convolution.Type.BLUR);

        }else if (e.getActionCommand().equals("Center Points")) {
            myFlame.changeConvolutedImage(Convolution.Type.CENTERPOINTS);

        }
    }

    //setConvolutionColor: changes the color that will be convoluted with checkbox
    private void setConvolutionColor() {
        GridBagConstraints cLabel = new GridBagConstraints();
        colorChooserLabel = new JLabel("Colors to convolute");
        colorChooserLabel.setForeground(Color.white);
        //set up constraints
        cLabel.gridx = 0;
        cLabel.gridy = 7;
        cLabel.weightx = 0.1;
        cLabel.weighty = 0;
        cLabel.gridwidth = 3;
        panelConvolution.add(colorChooserLabel, cLabel);
        
        GridBagConstraints cCheckBox = new GridBagConstraints();
        redCheckbox = new JCheckBox("Red");
        redCheckbox.setBackground(null);
        redCheckbox.setForeground(Color.WHITE);
        redCheckbox.setSelected(true);
        
        //set up constraints
        cCheckBox.gridx = 0;
        cCheckBox.gridy = 8;
        cCheckBox.weightx = 0.1;
        cCheckBox.weighty = 0.3;
        panelConvolution.add(redCheckbox, cCheckBox);
        redCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1){
                    myFlame.changeConvolutedImage("red", true);
                }else{
                    myFlame.changeConvolutedImage("red", false);

                }
            }
        });
        
        greenCheckbox = new JCheckBox("Green");
        greenCheckbox.setBackground(null);
        greenCheckbox.setForeground(Color.WHITE);
        greenCheckbox.setSelected(true);
        cCheckBox.gridx = 1;
        panelConvolution.add(greenCheckbox, cCheckBox);
        greenCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1){
                    myFlame.changeConvolutedImage("green", true);
                }else{
                    myFlame.changeConvolutedImage("green", false);

                }
            }
        });

        blueCheckbox = new JCheckBox("Blue");
        blueCheckbox.setBackground(null);
        blueCheckbox.setForeground(Color.WHITE);
        blueCheckbox.setSelected(true);
        cCheckBox.gridx = 2;
        panelConvolution.add(blueCheckbox, cCheckBox);
        blueCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1){
                    myFlame.changeConvolutedImage("blue", true);
                }else{
                    myFlame.changeConvolutedImage("blue", false);

                }
            }
        });
    }

   

  
    
    
    
    

}




    
    

