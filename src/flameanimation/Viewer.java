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
    private int rate = 40;
    private Flame flame;
    private Flame flame2;
    private BufferedImage image;
    private Thread thread;
    private Graphics graphics;
    private BufferStrategy bs;


    //CONSTRUCTOR
    public Viewer(){

        try{
            image = ImageIO.read(new File("IMG/hoguera.jpeg"));
        }catch(IOException e){
            e.getMessage();
        }

        flame = new Flame(500,850,BufferedImage.TYPE_INT_ARGB);
        thread = new Thread(flame);
        thread.start();
        flame2 = new Flame(500,850,BufferedImage.TYPE_INT_ARGB);
        thread = new Thread(flame2);
        thread.start();


        
        
    }

    
    @Override
    public void paint(Graphics g){
        bs = this.getBufferStrategy();
        if (bs==null){
            this.createBufferStrategy(2);
        }else{
            graphics = bs.getDrawGraphics();
            g.drawImage(image, 0,0, null);
            
            g.drawImage(flame,630,0,700,850,null);
            g.drawImage(flame2,630,0,700,850,null);
            bs.show();
            g.dispose();
        }
        
        
        
    } 

    
    //GETTERS AND SETTERS
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(rate);
            } catch (InterruptedException ex) {
                Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }

            if(graphics == null){
                System.out.println("Error de graphics");
            }
            else{
                paint(graphics);
            }
        }
    }

    

    
    
}
