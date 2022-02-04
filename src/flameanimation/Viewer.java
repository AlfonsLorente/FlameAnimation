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
    private Thread fireThread;
    private String imageSrc;
    private BufferedImage convolutedImage;


    void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    //fireState: enum that sets the fire state
    enum FireState {
        EXIT,
        PAUSE,
        RESUME
    }

    //CONSTRUCTOR
    public Viewer(Flame flame){
        try{
            image = ImageIO.read(new File("IMG/hoguera.jpeg"));
        }catch(IOException e){
            e.getMessage();
        }
        this.flame = flame;
        fireThread = new Thread(flame);
        fireThread.start();
        
    }
    
    
    
    //GETTERS AND SETTERS
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
    //setFireThread: sets the state of the fire
    public void setFireThread(FireState state){
        switch(state){
            case EXIT:
                System.exit(0);
                break;
            case PAUSE:
                fireThread.suspend();
                break;
            case RESUME:
                fireThread.resume();
                break;
        }
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
        g.drawImage(image.getScaledInstance(350, -1, BufferedImage.SCALE_SMOOTH), 0, 0, this);
        g.drawImage(convolutedImage.getScaledInstance(350, -1, BufferedImage.SCALE_SMOOTH), 350, 0, this);
        g.fillRect(700, 0, 350, 195);
        g.drawImage(flame,720,-55,300,250,null);
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
