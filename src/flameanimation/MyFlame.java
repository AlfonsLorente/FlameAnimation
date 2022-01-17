/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.*;
import javax.sound.sampled.*;


/**
 *
 * @author alfon
 */
public class MyFlame extends JFrame {
    //VARIABLES
    private boolean isPaused = false;
    private boolean isExit = false;
    private int flameCoolAmount = 80;
    private static Viewer viewer;
    private Thread thread;
    private int viewerRate = 20;
    private FlamePalette flamePalette;
    private Flame flame1;
    private Flame flame2;
    private ControlPanel controlPanel;
    private GridBagConstraints constraints = new GridBagConstraints();  
    private Color c1, c2, c3, c4, c5;
    private String audio = "MUSIC/zombies.wav";
    private Clip clip;
    private boolean audioPlaying = false;
    


    //MAIN
    public static void main(String[] args) {
        
        MyFlame myFlame = new MyFlame();
        
        
    }
    
    //CONSTRUCTOR
    public MyFlame(){
        
        //Set flame palette
        flamePalette = setFlamePalette(flamePalette);
        //Create flames
        flame1 = new Flame(500,850,BufferedImage.TYPE_INT_ARGB);
        flame1.setRate(90);
        flame1.setPalette(flamePalette);
        flame1.setCoolAmount(flameCoolAmount);
        //Create viewer
        viewer = new Viewer(flame1);
        this.setViewerRate(viewerRate);
        //Create control panel
        controlPanel = new ControlPanel();
        controlPanel.setMyFlame(this);
        //Set the jframe
        setMyFlame();
        //set Audio
        setUpAudio(audio);
       // this.add(viewer);
        setGridRules();
        //Start the viewer thread
        thread = new Thread(viewer);
        thread.start();
        //Set the jframe visible
        this.setVisible(true);
        
    }
    
    //GETTERS AND SETTERS
    
    public Color getC1() {
        return c1;
    }

    public void setC1(Color c1) {
        this.c1 = c1;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    public Color getC2() {
        return c2;
    }

    public void setC2(Color c2) {
        this.c2 = c2;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    public Color getC3() {
        return c3;
    }

    public void setC3(Color c3) {
        this.c3 = c3;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    public Color getC4() {
        return c4;
    }

    public void setC4(Color c4) {
        this.c4 = c4;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    public Color getC5() {
        return c5;
    }

    public void setC5(Color c5) {
        this.c5 = c5;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    public void setFlameCoolAmount(int flameCoolAmount) {
        this.flameCoolAmount = flameCoolAmount;
        flame1.setCoolAmount(flameCoolAmount);
    }

    
    
    
    //PUBLIC METHODS
    //setFlamePalette: Prepare the palette
    public FlamePalette setFlamePalette(FlamePalette palette){
        //Create the palette
        palette = new FlamePalette();
        c1 = Color.WHITE;
        c2 = Color.YELLOW;
        c3 = Color.ORANGE;
        c4 = Color.ORANGE.darker();
        c5 = Color.RED.darker();
        //set the palette colors
        palette.addTargetColor(new TargetColor(255, c1));
        palette.addTargetColor(new TargetColor(220, c2));
        palette.addTargetColor(new TargetColor(160, c3));
        palette.addTargetColor(new TargetColor(100, c4));
        palette.addTargetColor(new TargetColor(0, c5));
        return palette;

    }
    
    //setFlamePalette: Prepare the palette
    public void setFlamePalette(Color c1, Color c2, Color c3, Color c4, Color c5){
        //Set new palette colors
        FlamePalette palette = new FlamePalette();
        palette.addTargetColor(new TargetColor(255, c1));
        palette.addTargetColor(new TargetColor(220, c2));
        palette.addTargetColor(new TargetColor(140, c3));
        palette.addTargetColor(new TargetColor(100, c4));
        palette.addTargetColor(new TargetColor(0, c5));
        flamePalette = palette;
        flame1.setPalette(flamePalette);

    }
    

   
    
    //setStop: Not implemented
    public void setExit(){
         if(isExit == false){
            isExit = true;
            viewer.setFireThread(Viewer.FireState.EXIT);
        } 
    }

    //setPause: Not implemented
    public void setPause(){
        if(isPaused == false){
            isPaused = true;
            viewer.setFireThread(Viewer.FireState.PAUSE);
        } else{
            isPaused = false;
            viewer.setFireThread(Viewer.FireState.RESUME);

        }
        
    }
    
    
    
    
    //setViewerRate: Sets the framerate of the viewer
    public void setViewerRate(int rate){
        viewer.setRate(rate);
        
    }
    
    public void setFlameRate(int rate){
        flame1.setRate(rate);
    }

    //PRIVATE METHODS
    //setMyFlame: Sets the jframe
    private void setMyFlame() {
        this.setTitle("Flame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        this.setLayout (new GridBagLayout());
        this.setBounds(0, 0, 1360, 790);
        this.setResizable(false);        
    }
    
     //setGridRules: set up the gridbag layout rules
    private void setGridRules(){
        //Set the constraints up for the viewer
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        //Add the viewer with the contraints.
        this.add(viewer , constraints);
        
        //Set the constraints up for the control panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 1;
        //Add the control panel with the contraints.
        this.add (controlPanel, constraints);

    }
    
    
    public void setUpAudio(String audio){
       try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audio).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
       } catch(Exception e) {
         System.out.println(e.getMessage());
       }
     }
    
    public void songController(){
        if(audioPlaying){
            audioPlaying = false;
            clip.stop();
        }else{
            audioPlaying = true;
            clip.start();
        }
    }
    
    
}
