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
    private Thread thread;
    private Graphics graphics;
    private BufferStrategy bs;


    //CONSTRUCTORS
    //1 FLAME
    public Viewer(Flame flame){
        try{
            image = ImageIO.read(new File("IMG/hoguera.jpeg"));
        }catch(IOException e){
            e.getMessage();
        }
        this.flame1 = flame;
        thread = new Thread(flame);
        thread.start();
        
    }
    
    //2 FLAMES
    public Viewer(Flame flame1, Flame flame2){
        try{
            image = ImageIO.read(new File("IMG/hoguera.jpeg"));
        }catch(IOException e){
            e.getMessage();
        }
        this.flame1 = flame1;
        thread = new Thread(flame1);
        thread.start();
        this.flame2 = flame2;
        thread = new Thread(flame2);
        thread.start();

        
    }
    
    //GETTERS AND SETTERS
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    
    //PUBLICS METHODS:
    //paint: Draw the flames and the background image
    @Override
    public void paint(Graphics g){
        //The buffered strategy trys to prevent flickering
        bs = this.getBufferStrategy();
        if (bs==null){
            this.createBufferStrategy(2);
        }else{
            graphics = bs.getDrawGraphics();
            graphics.drawImage(image, 0,0, null);
            graphics.drawImage(flame1,630,0,700,850,null);
            if(flame2 != null){
                graphics.drawImage(flame2,630,0,700,850,null);
            }
            bs.show();
            graphics.dispose();
        }
        
    } 

 

    //run: Called by the thread, it runs the paint function
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(rate);
            } catch (InterruptedException ex) {
                Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }
            paint(graphics);
        }
    }

    
}
