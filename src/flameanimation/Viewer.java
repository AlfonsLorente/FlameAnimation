/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author alfon
 */
public class Viewer extends Canvas implements Runnable{
    //VARIABLES
    private int rate;
    private Flame flame;
    private BufferedImage image;
    private BufferedImage convolutedImage;
    private FlameAnimation flameAnimation;



    //fireState: enum that sets the fire state
    enum FireState {
        EXIT,
        PAUSE,
        RESUME
    }

    //CONSTRUCTOR
    public Viewer(Flame flame, BufferedImage image, BufferedImage convolutedImage, FlameAnimation flameAnimation){
        this.image = image;
        this.convolutedImage = convolutedImage;
        this.flame = flame;
        this.flameAnimation = flameAnimation;
        
        
    }

    public void setFlameAnimation(FlameAnimation flameAnimation) {
        this.flameAnimation = flameAnimation;
    }
    
    
    
    //GETTERS AND SETTERS
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
   
    
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getConvolutedImage() {
        return convolutedImage;
    }

    public void setConvolutedImage(BufferedImage convolutedImage) {
        this.convolutedImage = convolutedImage;
    }

    
    
    
    //PUBLICS METHODS:
    //paint: Draw the flames and the background image
    public void paint(){
        //The buffered strategy trys to prevent flickering
        //Uses the buffered strategy
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        if (bs==null){
            return;
        }
        if (g==null){
            return;
        }
        g = bs.getDrawGraphics();
        g.fillRect(700, 0, 350, 195);
        g.drawImage(image.getScaledInstance(350, -1, BufferedImage.SCALE_SMOOTH), 0, 0, this);
        g.drawImage(convolutedImage.getScaledInstance(350, -1, BufferedImage.SCALE_SMOOTH), 350, 0, this);
        g.drawImage(flame,720,-55,300,250,null);
        g.drawImage(image.getScaledInstance(1000, -1, BufferedImage.SCALE_SMOOTH), 20, 200, this);
        g.drawImage(flameAnimation.getScaledInstance(1000, -1, BufferedImage.SCALE_SMOOTH), 20, 200, this);


        bs.show();
        g.dispose();
    }
        
   

 

    //run: Called by the thread, it runs the paint function
    @Override
    public void run() {
        //Initial sleep to prevent pair error
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        //implements the buffer strategy
        createBufferStrategy(2);
        while(true){
            try {
                Thread.sleep(rate);
            } catch (InterruptedException ex) {
                Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
                
        }
            paint();
        }
    }

    
}
