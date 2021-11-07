/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author alfon
 */
public class Flame extends BufferedImage {
    //VARIABLES
    private int width;
    private int height;
    private File file = new File("IMG/out.jpg");
    //CONSTRUCTOR
    public Flame(int width, int height, int imageType) {
        super(width, height, imageType);
        this.width = width;
        this.height = height;
        this.doSomething();

    }
    
    public Flame(BufferedImage img){
        super(img.getWidth(),img.getHeight(),img.getType());
        setData(img.getData());
    }
    
    
    //PUBLIC METHODS
    public void flameEvolve(){
        
    }
    
    public void doSomething(){
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int a = (int)(Math.random()*256); //generating
                int r = (int)(Math.random()*256); //values
                int g = (int)(Math.random()*256); //less than
                int b = (int)(Math.random()*256); //256
                int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
                this.setRGB(x, y, p);
           }
        }
        // write image
        try
        {
            ImageIO.write(this, "jpg", file);
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
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

