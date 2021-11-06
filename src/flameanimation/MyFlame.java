/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import javax.swing.JFrame;

/**
 *
 * @author alfon
 */
public class MyFlame extends JFrame {
    //VARIABLES
    private boolean isPaused;
    private boolean isStoped;
    private Viewer viewer;
    
    //MAIN
    public static void main(String[] args) {
        new MyFlame();
    }
    
    //CONSTRUCTOR
    public MyFlame(){
        viewer = new Viewer();
        this.setTitle("Flame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(true);        
        this.setVisible(true); 
        this.add(viewer);
        
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
    
    
}
