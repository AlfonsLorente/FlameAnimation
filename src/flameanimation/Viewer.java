/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alfon
 */
public class Viewer extends Canvas implements Runnable {

    //VARIABLES
    private int rate;
    private Flame flame;
    private BufferedImage image;
    private BufferedImage convolutedImage;
    private FlameAnimation flameAnimation;

    /**
     * enum that sets the fire state
     */
    enum FireState {
        EXIT,
        PAUSE,
        RESUME
    }

    //CONSTRUCTOR
    /**
     * sets variables up
     *
     * @param flame - Flame
     * @param image - BufferedImage
     * @param convolutedImage - BufferedImage
     * @param flameAnimation - FlameAnimation
     */
    public Viewer(Flame flame, BufferedImage image, BufferedImage convolutedImage, FlameAnimation flameAnimation) {
        this.image = image;
        this.convolutedImage = convolutedImage;
        this.flame = flame;
        this.flameAnimation = flameAnimation;

    }

    //GETTERS AND SETTERS
    /**
     * sets the flameAnimation
     *
     * @param flameAnimation - Flame Animation
     */
    public void setFlameAnimation(FlameAnimation flameAnimation) {
        this.flameAnimation = flameAnimation;
    }

    /**
     * gets the rate
     *
     * @return int
     */
    public int getRate() {
        return rate;
    }

    /**
     * sets the rate
     *
     * @param rate - int
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    /**
     * gets the normal image
     *
     * @return BufferedImage
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * sets the normal image
     *
     * @param image - BufferedImage
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * gets the convoluted image
     *
     * @return BufferedImage
     */
    public BufferedImage getConvolutedImage() {
        return convolutedImage;
    }

    /**
     * sets the convoluted image
     *
     * @param convolutedImage - BufferedImage
     */
    public void setConvolutedImage(BufferedImage convolutedImage) {
        this.convolutedImage = convolutedImage;
    }

    //PUBLICS METHODS:
    /**
     * Draw the flames and the background image
     */
    public void paint() {
        //The buffered strategy trys to prevent flickering
        //Uses the buffered strategy
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        if (bs == null) {
            return;
        }
        if (g == null) {
            return;
        }
        g = bs.getDrawGraphics();
        //Draw all the components
        g.setColor(Color.black);
        g.fillRect(0, 0, 1800, 1800);
        g.drawImage(image.getScaledInstance(340, -1, BufferedImage.SCALE_SMOOTH), 0, 0, this);
        g.drawImage(convolutedImage.getScaledInstance(340, -1, BufferedImage.SCALE_SMOOTH), 350, 0, this);
        g.drawImage(flame.getScaledInstance(345, -1, BufferedImage.SCALE_SMOOTH), 695, -445, null);
        g.drawImage(image.getScaledInstance(650, -1, BufferedImage.SCALE_SMOOTH), 200, 260, this);
        g.drawImage(flameAnimation.getScaledInstance(650, -1, BufferedImage.SCALE_SMOOTH), 200, 260, this);

        bs.show();
        g.dispose();
    }

    /**
     * Called by the thread, it runs the paint function
     */
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
        while (true) {
            try {
                Thread.sleep(rate);//rate that will slow down the thread
            } catch (InterruptedException ex) {
                Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);

            }
            paint();
        }
    }

}
