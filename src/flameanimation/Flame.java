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
    private int width;
    private int height;
    private FlamePalette flamePalete;
    private boolean endFlame = false;
    private boolean pausedFlame = false;
    private int rate = 150;
    private int[][] pixels;

  
    
//CONSTRUCTOR
    public Flame(int width, int height, int imageType) {
        super(width, height, imageType);
        this.width = width;
        this.height = height;
        this.pixels = new int[width][height];
        //thread = new Thread(this);
        //thread.start();
       

    }
    
    
    
    //PUBLIC METHODS
    public void flameEvolve(){
        createSparks(0);
        createCool(0);
        
        temperatureEvolve();
        createFlameImage();

        
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
        this.rate = rate;
    }
    
    //PRIVATE METHODS
    private void createCool(int colFrom){
        //go over the first pixel width row
        for (int x = colFrom; x < width; x++)
        {
            int varAux = (int) (Math.random()*100);
            if(varAux > 85){
                //Add random 255 pixels
                pixels[x][height-1] = 0;
            }
        }
        
    }
    
    private void createSparks(int colFrom){
        //go over the first pixel width row
        for (int x = colFrom; x < width; x++)
        {
            int varAux = (int) (Math.random()*100);
            if(x > width/3 && x < (width*2/3)){
               if(varAux > 50){
                //Add random 255 pixels
                pixels[x][height-1] = 255;
            } 
            }else if (x > width/10 && x < (width*8/9)){
                if(varAux > 75){
                //Add random 255 pixels
                pixels[x][height-1] = 255;
                }
            }else{
                 if(varAux > 85){
                //Add random 255 pixels
                pixels[x][height-1] = 255;
            }
            
            }
        }

    }
    
    private void createFlameImage(){
         for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                    int a; //generating
                    int r = 255; //values
                    int g = pixels[x][y]; //less thanWW
                    int b = 30; //256
                    int p;
                    if(g < 20){
                         a = 0; //generating
                         p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
                        this.setRGB(x, y,p);    

                    }else{
                        a = 210; //generating
                         p = (a<<24) | (r<<16) | (g<<8) | b;
                        this.setRGB(x, y, p);    
                    }
                
                
                
           }
        }
    }
    
    

    
    private void temperatureEvolve(){
        int num;
        for (int y=height-2; y>=0; y--){
            for (int x=1; x < width-1; x++){
                num =(pixels[x][y+1] + pixels[x+1][y+1] + pixels[x-1][y+1])/3;
                if(num != 0 && num < 230){
                    num = num + ((int)(Math.random()*14.7) - (int)(Math.random()*15));
                }
                
                if(num < 10) {
                    num = 0;
                }
                pixels[x][y] = num;
            }
                

        }
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
            
}

