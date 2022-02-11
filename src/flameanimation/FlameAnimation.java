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

    private BufferedImage convolutedImage;
    private float luminanceMin;

    public FlameAnimation(int width, int height, int imageType, BufferedImage convolutedImage) {
        super(width, height, imageType);
        this.convolutedImage = convolutedImage;
        luminanceMin = 0.45f;

    }

    public void setLuminanceMin(float luminanceMin) {
        this.luminanceMin = luminanceMin;
        createSparks();
    }

    public void setConvolutedImage(BufferedImage convolutedImage) {
        this.width = convolutedImage.getWidth();
        this.height = convolutedImage.getHeight();
        this.convolutedImage = convolutedImage;
        this.cleanFlame();
        createSparks();

    }

    @Override
    public void createSparks() {
        sparks = new int[width][height];
        float luminance;
        int red;
        int green;
        int blue;
        for (int i = 1; i < this.getWidth() - 1; i++) {
            for (int j = 1; j < this.getHeight() - 1; j++) {
                red = new Color(convolutedImage.getRGB(i, j), true).getRed();
                green = new Color(convolutedImage.getRGB(i, j), true).getGreen();
                blue = new Color(convolutedImage.getRGB(i, j), true).getBlue();
                luminance = (red * 0.2116f + green * 0.7152f + blue * 0.0722f) / 255;
                if (luminance > luminanceMin) {
                    sparks[i][j] = 255;

                } else {
                    sparks[i][j] = 0;
                }
            }

        }
    }

    @Override
    protected void createCool() {
        int rand;
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                
                rand = (int) (Math.random() * 100);

                if (rand < coolAmount && sparks[i][j] == 255) {

                        pixels[i][j] = sparks[i][j];

                }
                
              /*

                if (sparks[i][j] == 255 && pixels[i][j] == 0) {
                        pixels[i][j] = 255;
                }
                else if (coolAmount > rand && sparks[i][j] == 255) {
                   
                        pixels[i][j] = 0;
                    
                    
                }
                */            

            }

        }

    }

    private void cleanFlame() {
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                pixels[i][j] = 0;
            }
        }
    }

}
