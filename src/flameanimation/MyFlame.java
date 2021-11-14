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
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author alfon
 */
public class MyFlame extends JFrame {
    //VARIABLES
    private boolean isPaused;
    private boolean isStoped;
    private static Viewer viewer;
    private static MyFlame myFlame;
    private Thread thread;
    private int viewerRate = 40;
    private FlamePalette flamePalette;
    private Flame flame1;
    private Flame flame2;

    //MAIN
    public static void main(String[] args) {
        
        myFlame = new MyFlame();
        
        
    }
    
    //CONSTRUCTOR
    public MyFlame(){
        //Set flame palette
        flamePalette = setFlamePalette(flamePalette);
        //Create flames
        flame1 = new Flame(500,850,BufferedImage.TYPE_INT_ARGB);
        flame1.setPalette(flamePalette);
        flame2 = new Flame(500,850,BufferedImage.TYPE_INT_ARGB);
        flame2.setPalette(flamePalette);
        //Create viewer
        viewer = new Viewer(flame1, flame2);
        this.setViewerRate(viewerRate);
        setMyFlame();
        
        this.add(viewer);
        thread = new Thread(viewer);
        thread.start();
        this.setVisible(true);
        
    }
    
    //METHODS
    public FlamePalette setFlamePalette(FlamePalette palette){
        palette = new FlamePalette();
        palette.addTargetColor(new TargetColor(255, Color.WHITE));
        palette.addTargetColor(new TargetColor(220, Color.YELLOW));
        palette.addTargetColor(new TargetColor(170, Color.ORANGE));
        palette.addTargetColor(new TargetColor(120, Color.ORANGE.darker()));
        palette.addTargetColor(new TargetColor(90, Color.RED));
        palette.addTargetColor(new TargetColor(0, Color.BLACK.brighter()));
        return palette;

    }
    
    public void setStop(boolean s){
        
    }

    
    public void setPause(boolean p){
        
    }
    
    public void setViewerRate(int rate){
        viewer.setRate(rate);
        
    }

    private void setMyFlame() {
        
        this.setTitle("Flame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 1900, 1000);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);        
    }
    
    
}
