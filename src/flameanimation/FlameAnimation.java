/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author alfon
 */
public class FlameAnimation extends Flame {

    //VARIABLES
    private BufferedImage convolutedImage;
    private float luminanceMin;

    //CONSTRUCTORS
    /**
     * sets the super variables, the convoluted image and a luminance minimum
     * @param width - int
     * @param height - height
     * @param imageType - int
     * @param convolutedImage - BufferedImage 
     */
    public FlameAnimation(int width, int height, int imageType, BufferedImage convolutedImage) {
        super(width, height, imageType);
        this.convolutedImage = convolutedImage;
        luminanceMin = 0.80f;

    }

    
    //GETTERS AND SETTERS
    
    /**
     * set a new luminanceMin value
     * @param luminanceMin - float
     */
    public void setLuminanceMin(float luminanceMin) {
        this.luminanceMin = luminanceMin;
        createSparks();//need to change the spark array
    }

    /**
     * change the convolutedImage
     * @param convolutedImage - BufferedImage
     */
    public void setConvolutedImage(BufferedImage convolutedImage) {
        this.width = convolutedImage.getWidth();
        this.height = convolutedImage.getHeight();
        this.convolutedImage = convolutedImage;
        this.cleanFlame();//clean the actual fire 
        createSparks(); // create a new sparks array

    }

    //PROTECTED METHODS
    
    /**
     * create the sparks array depending on the luminance
     */
    @Override
    protected void createSparks() {
        //variables
        sparks = new int[width][height];
        float luminance;
        int red;
        int green;
        int blue;
        //loop image
        for (int i = 1; i < this.getWidth() - 1; i++) {
            for (int j = 1; j < this.getHeight() - 1; j++) {
                //get each color
                red = new Color(convolutedImage.getRGB(i, j), true).getRed();
                green = new Color(convolutedImage.getRGB(i, j), true).getGreen();
                blue = new Color(convolutedImage.getRGB(i, j), true).getBlue();
                //set formula
                luminance = (red * 0.2116f + green * 0.7152f + blue * 0.0722f) / 255;
                //set sparks to 255 or 0
                if (luminance > luminanceMin) {
                    sparks[i][j] = 255;

                } else {
                    sparks[i][j] = 0;
                }
            }

        }
    }

    
    /**
     * creates cool for the flame animation 
     */
    @Override
    protected void createCool() {
        int rand;
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                rand = (int) (Math.random() * 100);
                if (rand < coolAmount && sparks[i][j] == 255) {

                        pixels[i][j] = 255;

                }
     

            }

        }

    }
    
    
    //PRIVATE METHODS

    /**
     * sets all pixels to 0
     */
    private void cleanFlame() {
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                pixels[i][j] = 0;
            }
        }
    }

}
