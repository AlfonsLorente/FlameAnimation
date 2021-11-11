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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author alfon
 */
public class Flame extends BufferedImage implements Runnable {
    //VARIABLES
    private int width;
    private int height;
    private Thread thread;
    private FlamePalette firePalete;
    private boolean endFlame;
    private boolean pausedFlame;
    private int sleep = 500;
    private int[][] pixels;
    private int X;
    private int Y;
    
//CONSTRUCTOR
    public Flame(int width, int height, int imageType) {
        super(width, height, imageType);
        this.width = width;
        this.height = height;
        this.pixels = new int[width][height];
        

    }
    
    
    
    //PUBLIC METHODS
    public void flameEvolve(){
        createSparks(0);
        //createSparks();
        //createCoolPoints();
        
        //temperatureEvolve()
        //createFireImage();
        
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
        
    }
    public void paint(Graphics g){
        g.drawImage(this, width, height, null);
    }
    
    public void setPalette(){
        
    }
    
    public void setRate(int rate){
        
    }
    
    //PRIVATE METHODS
    private void createCool(int colFrom, int colTo,int row,int percentage){
        
    }
    
    private void createSparks(int colFrom){
        //recorrer caselles de temperatura
        for (int x = colFrom; x < width; x++)
        {
            int varAux = (int) Math.random()*100;
            if(varAux > 60){
                pixels[x][0] = 255;
            }
        }

        //random que fica aleatoriament temperatura al maxim
    }
    
    private void createFlameIamge(){
         for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int a = 255; //generating
                int r = 0; //values
                int g = 0; //less than
                int b = 0; //256
                int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
                this.setRGB(x, y, p);
           }
        }
    }
    
    

    
    private void temperatureEvolve(){
        
    }
    
    /*
    private void fillTempreaturesTo0(){
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int a = 255; //generating
                int r = 0; //values
                int g = 0; //less than
                int b = 0; //256
                int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
                this.setRGB(x, y, p);
           }
        }
    }
*/
    

    @Override
    public void run() {
        
        while(!endFlame){
            if(!pausedFlame){
                flameEvolve();
                
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
            
}

