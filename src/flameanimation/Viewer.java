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
public class Viewer extends Canvas{
    //VARIABLES
    private int rate;
    private Flame flame;
    private BufferedImage image;

    //CONSTRUCTOR
    public Viewer(){
        try{
            image = ImageIO.read(new File("IMG/FBM.png"));
        }catch(IOException e){
            e.getMessage();
        }
        flame = new Flame(600,500,BufferedImage.TYPE_INT_ARGB);
        
        
    }

    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(image, 0,0, null);
        g.drawImage(flame,200,100,1000,550,null);
    } 

    
    //GETTERS AND SETTERS
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    
    
}
