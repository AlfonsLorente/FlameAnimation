/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Color;
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
    protected int width;
    protected int height;
    private FlamePalette flamePalette;
    protected int coolAmount = 70;
    private boolean pausedFlame = false;
    private int rate = 50;
    protected int[][] pixels;

  
    
//CONSTRUCTOR
    public Flame(int width, int height, int imageType) {
        super(width, height, imageType);
        this.width = width;
        this.height = height;
        this.pixels = new int[width][height];


    }
    
    //GETTERS AND SETTERS
    public void setPalette(FlamePalette flamePalette){
        this.flamePalette = flamePalette;
        
    }
    
    public void setRate(int rate){
        this.rate = rate;
    }

    public void setCoolAmount(int coolAmount) {
        this.coolAmount = coolAmount;
    }

    public int[][] getPixels() {
        return pixels;
    }
    
    
    
    
    //PUBLIC METHODS
    //flameEvolve: Controls the execution of the fire.
    public void flameEvolve(){
        createSparks(0);
        createCool(0);
        temperatureEvolve();
        createFlameImage();

        
    }
   
    
    //run: Called by the thread, it runs the flameEvolve function
    @Override
    public void run() {
        
        while(true){
            if(!pausedFlame){
                flameEvolve();
                
            }
            try {
                Thread.sleep(rate);
            } catch (InterruptedException ex) {
                Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    
    
    
    
    //PRIVATE METHODS
    //createCool: Creates cold points to prevent the fire to overburn
    protected void createCool(int colFrom){
        //go over the first pixel width row
        for (int x = colFrom; x < width; x++)
        {
            int varAux = (int) (Math.random()*100);
            if(varAux > coolAmount){
                //Add random 255 pixels
                pixels[x][height-1] = 0;
            }
        }
        
    }
    
    //createSparks: Creates 255 points to start the fire
    protected void createSparks(int colFrom){
        //go over the first pixel width row
        for (int x = colFrom; x < width; x++)
        {
            int rand = (int) (Math.random()*100);
            //This creates more sparks at the center of the fire than at the outsides
            if(x > width/3 && x < (width*2/3)){
               if(rand > 45){
                    //Add random 255 pixels
                    pixels[x][height-1] = 255;
            } 
            }else if (x > width/10 && x < (width*8/9)){
                if(rand > 60){
                    pixels[x][height-1] = 255;
                }
            }else{
                 if(rand > 70){
                    pixels[x][height-1] = 255;
                }
            }
        }
    }
    
    //createFlameImage: Loops around all the flame image and sets the pixels to its color with the flame palette
    private void createFlameImage(){
         for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int color = flamePalette.getColor(pixels[x][y]);
                this.setRGB(x, y, color);

            }
        }
    }
    
    

    //temperatureEvolve: This evolves the fire looking for the bottom pixels temperature and setting a new one for the top one.
    private void temperatureEvolve(){
        int num;
        for (int y=height-2; y>=0; y--){
            for (int x=width-2; x >= 1; x--){
                //Formula to calcule the top pixel (or bottom pixel, depends on which way you look at)
                num =(pixels[x][y+1] + pixels[x+1][y+1] + pixels[x-1][y+1] + 
                        pixels[x][y] + pixels[x+1][y] + pixels[x-1][y])/6 ;

                if(num != 0 && num < 245){
                    //Setting random values to give some live/reality to the flame
                    num = num + ((int)(Math.random()*1) - (int)(Math.random()*1.3));
                }
                //Avoids posibles errors created with the random before done
                if(num < 5) {
                    num = 0;
                }
                pixels[x][y] = num;

            }

        }
    }    
            
}

