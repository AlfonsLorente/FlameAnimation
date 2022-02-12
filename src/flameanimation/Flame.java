/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alfon
 */
public class Flame extends BufferedImage implements Runnable {

    //VARIABLES
    protected int width;
    protected int height;
    private FlamePalette flamePalette;
    protected int coolAmount = 70;
    private boolean pausedFlame = false;
    private int rate = 50;
    protected int[][] pixels;
    protected int[][] sparks;
    private boolean alive = true;

    //CONSTRUCTOR
    /**
     * takes the values to set up the basic variables to construct the
     * bufferedimage superclass and the flame
     *
     * @param width - int
     * @param height - int
     * @param imageType - int
     */
    public Flame(int width, int height, int imageType) {
        super(width, height, imageType);
        this.width = width;
        this.height = height;
        this.pixels = new int[width][height];

    }

    //GETTERS AND SETTERS
    /**
     * set the palette variable
     *
     * @param flamePalette - FlamePalette
     */
    public void setPalette(FlamePalette flamePalette) {
        this.flamePalette = flamePalette;

    }

    /**
     * set the rate variable
     *
     * @param rate - int
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    /**
     * set the alive variable this will end execution of the thread
     *
     * @param alive - boolean
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * sets the coolAmount variable
     *
     * @param coolAmount - int
     */
    public void setCoolAmount(int coolAmount) {
        this.coolAmount = coolAmount;
    }

    /**
     * returns the pixels array
     *
     * @return - int[][]
     */
    public int[][] getPixels() {
        return pixels;
    }

    //PUBLIC METHODS
    /**
     * Controls the execution of the fire.
     */
    public void flameEvolve() {
        if (sparks == null) {
            createSparks();
        }
        createCool();
        temperatureEvolve();

    }

    /**
     * Called by the thread, it runs the flameEvolve function
     */
    @Override
    public void run() {

        while (alive) {
            if (!pausedFlame) {
                flameEvolve();

            }
            try {
                Thread.sleep(rate);
            } catch (InterruptedException ex) {
                Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    //PROTECTED METHODS
    /**
     * Creates cold points to prevent the fire to overburn
     */
    protected void createCool() {
        //go over the first pixel width row
        for (int x = 1; x < width - 1; x++) {
            int rand = (int) (Math.random() * 100);
            if (rand > coolAmount) {
                //Add random 255 pixels
                pixels[x][height - 1] = 0;
                pixels[x + 1][height - 1] = 0;
                pixels[x - 1][height - 1] = 0;

            } else {
                pixels[x][height - 1] = sparks[x][0];
            }
        }

    }

    /**
     * Creates 255 points to start the fire
     */
    protected void createSparks() {
        this.sparks = new int[width][1];

        //go over the first pixel width row
        for (int x = 0; x < width; x++) {

            sparks[x][0] = 255;

        }
    }
    
    //PRIVATE METHODS

    /**
     * Loops around all the flame image and sets the pixels to its color with
     * the flame palette is called by temperature evolve
     *
     * @param x - int
     * @param y - int
     */
    private void createFlameImage(int x, int y) {

        int color = flamePalette.getColor(pixels[x][y]);
        this.setRGB(x, y, color);

    }

    /**
     * This evolves the fire looking for the bottom pixels temperature and
     * setting a new one for the top one applying a formula.
     */
    private void temperatureEvolve() {
        int num;
        Random randNum = new Random();
        float rand;
        for (int y = height - 2; y >= 1; y--) {
            for (int x = 1; x < width - 1; x++) {

                //create a random float that will be multiplied by the formula
                rand = randNum.nextFloat() * (1.75f - 0.45f) + 0.45f;

                //Formula to calcule the top pixel (or bottom pixel, depends on which way you look at)
                num = (int) (((pixels[x][y + 1] * 2.5)
                        + (pixels[x + 1][y + 1] * 1.3)
                        + (pixels[x - 1][y + 1] * 1.3)
                        + (pixels[x][y] * 1.1)
                        + (pixels[x + 1][y] * 0.4)
                        + (pixels[x - 1][y] * 0.6)
                        + (pixels[x][y - 1] * 0.4)
                        + (pixels[x - 1][y - 1] * 0.1)
                        + (pixels[x + 1][y - 1] * 0.1)) / 9 * rand);

                //min and max pixels
                if (num < 2) {
                    num = 0;
                } else if (num > 255) {
                    num = 255;
                }
                pixels[x][y] = num;

                createFlameImage(x, y);

            }

        }

    }

}
