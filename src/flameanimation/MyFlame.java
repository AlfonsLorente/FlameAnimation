/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

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

    //MAIN
    public static void main(String[] args) {
        
        myFlame = new MyFlame();
        while(true){
            viewer.repaint();
            try {
                Thread.sleep(600);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyFlame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //CONSTRUCTOR
    public MyFlame(){
        
        viewer = new Viewer();
        setMyFlame();
        this.add(viewer);
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
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(true);        
    }
    
    
}
