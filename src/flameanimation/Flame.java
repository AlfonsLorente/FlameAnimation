/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author alfon
 */
public class Flame extends BufferedImage {
    //VARIABLES
    private int width;
    private int height;
    private File file = new File("IMG/fuegoProvisional.jpg");
    //CONSTRUCTOR
    public Flame(int width, int height, int imageType) {
        super(width, height, imageType);
        this.width = width;
        this.height = height;
    }
    
    public Flame(BufferedImage img){
        super(img.getWidth(),img.getHeight(),img.getType());
        setData(img.getData());
    }
    
    
    //PUBLIC METHODS
    public void flameEvolve(){
        
    }
    public void paint(Graphics g){
        g.drawImage(this, width, height, null);
    }
    
    public void setPalette(){
        
    }
    
    public void setRate(int rate){
        
    }
    
    //PRIVATE METHODS
    private void createCoal(int colFrom, int colTo,int row,int percentage){
        
    }
    
    private void createSparks(int colFrom, int colTo,int row,int percentage){
        
    }
    
    private void createFlameIamge(){
        
    }
    

    
    private void temperatureEvolve(){
        
    }
            
}

