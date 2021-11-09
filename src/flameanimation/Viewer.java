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

/**
 *
 * @author alfon
 */
public class Viewer extends Canvas{
    //VARIABLES
    private int rate;
    private Flame flame;
    private Dimension screenSize;

    //CONSTRUCTOR
    public Viewer() throws IOException{
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //double width = screenSize.getWidth();
        //double height = screenSize.getHeight();
        //this.setBackground (Color.BLACK);    
        this.setSize(screenSize);
        //new Flame(700, 500, BufferedImage.TYPE_INT_ARGB);
        flame = new Flame(ImageIO.read(new File("IMG/donut.png")));
    }

    
    
    //GETTERS AND SETTERS
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    
    
}
