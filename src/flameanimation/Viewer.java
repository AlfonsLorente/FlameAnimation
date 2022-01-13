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
    private Flame flame1;
    private Flame flame2;
    private BufferedImage image;
    private Thread fireThread;

    enum FireState {
        EXIT,
        PAUSE,
        RESUME
    }

    //CONSTRUCTORS
    //1 FLAME
    public Viewer(Flame flame){
        try{
            image = ImageIO.read(new File("IMG/hoguera.jpeg"));
        }catch(IOException e){
            e.getMessage();
        }
        this.flame1 = flame;
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
        g.drawImage(image.getScaledInstance(1350, -1, BufferedImage.SCALE_SMOOTH), -130, 0, this);
        g.drawImage(flame1,360,0,400,600,null);
        if(flame2 != null){
            g.drawImage(flame2,480,0,400,600,null);
        }
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
