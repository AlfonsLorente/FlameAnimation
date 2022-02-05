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

    public FlameAnimation(int width, int height, int imageType, BufferedImage convolutedImage) {
        super(width, height, imageType);
        this.convolutedImage = convolutedImage;

    }

    public void setConvolutedImage(BufferedImage convolutedImage) {
        this.cleanFlame();
        super.width = convolutedImage.getWidth();
        super.height = convolutedImage.getHeight();
        this.convolutedImage = convolutedImage;
    }

    
    
    @Override
    protected void createSparks(int colFrom) {
        float luminance;
        int red;
        int green;
        int blue;
        for (int i = 1; i < this.getWidth()-1; i++) {
            for (int j = 1; j < this.getHeight()-1; j++) {
                red = new Color(convolutedImage.getRGB(i, j), true).getRed();
                green = new Color(convolutedImage.getRGB(i, j), true).getGreen();
                blue = new Color(convolutedImage.getRGB(i, j), true).getBlue();
                luminance = (red * 0.2116f + green * 0.7152f + blue * 0.0722f) / 255;

                int rand = (int) (Math.random() * 100);
                if (luminance > 0.8) {
                    if (rand > 70) {
                        pixels[i][j] = 255;

                    }
                }
            }

        }
    }
    
    @Override
    protected void createCool(int colFrom) {
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                int rand = (int) (Math.random() * 100);
                if (pixels[i][j] == 255) {
                    if (rand > coolAmount) {
                        pixels[i][j] = 0;
                    }
                }

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
