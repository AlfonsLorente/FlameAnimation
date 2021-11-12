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
    private int rate = 100;
    private Flame flame;
    //private Flame flame2;
    private BufferedImage image;
    private Thread thread;
    private Graphics graphics;


    //CONSTRUCTOR
    public Viewer(){

        try{
            image = ImageIO.read(new File("IMG/FBM.png"));
        }catch(IOException e){
            e.getMessage();
        }
        flame = new Flame(600,500,BufferedImage.TYPE_INT_ARGB);
        thread = new Thread(flame);
        thread.start();
        /*flame2 = new Flame(600,500,BufferedImage.TYPE_INT_ARGB);
        thread = new Thread(flame2);
        thread.start();*/


        
        
    }

    
    @Override
    public void paint(Graphics g){
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        if (bs==null){
            return;
        }
        if (g==null){
            return;
        }
        g.drawImage(image, 0,0, null);
        g.drawImage(flame,200,0,1000,600,null);
        //g.drawImage(flame2,200,0,1000,600,null);

        
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
       createBufferStrategy(2);  
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
