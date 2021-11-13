/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private JPanel jPanel;

    //MAIN
    public static void main(String[] args) {
        
        myFlame = new MyFlame();
        
        
    }
    
    //CONSTRUCTOR
    public MyFlame(){

        viewer = new Viewer();
        setMyFlame();
        
        this.add(viewer);
        thread = new Thread(viewer);
        thread.start();
        this.setVisible(true);
        
    }
    
    //METHODS
    public void setFlamePalette(int flameNumber, FlamePalette palette){
        
    }
    
    public void setStop(boolean s){
        
    }

    
    public void setPause(boolean p){
        
    }
    
    public void setViewerRate(int rate){
        
    }

    private void setMyFlame() {
        
        this.setTitle("Flame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(70, 40, 1900, 1000);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);        
    }
    
    
}
