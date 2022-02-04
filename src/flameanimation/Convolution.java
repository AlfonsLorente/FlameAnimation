/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author alfon
 */
public class Convolution{
    private BufferedImage image;
    private BufferedImage convolutedImage;
    private int[][] redList;
    private int[][] greenList;
    private int[][] blueList;
    private int[][] alphaList;
    
    private int[][] convolutedRedList;
    private int[][] convolutedGreenList;
    private int[][] convolutedBlueList;
    
    
    
    private float[][] kernel;
    private float kernelDiv = 0;

    private int width;
    private int height;
    private boolean modRed = true;
    private boolean modGreen = true;
    private boolean modBlue = true;
    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;
    private Type type;


    
    enum Type{
        SHARPEN,
        SMOOTH,
        RAISE,
        OUTLINE,
        EMBOSS,
        BLUR
    }
    
    
    public Convolution(BufferedImage image, Type type, boolean modRed, boolean modGreen, boolean modBlue){
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.convolutedImage = new BufferedImage(width-2, height-2, image.getType());
        this.modRed = modRed;
        this.modGreen = modGreen;
        this.modBlue = modBlue;

        this.redList = new int[width][height];
        this.greenList = new int[width][height];
        this.blueList = new int[width][height];
        this.alphaList = new int[width][height];
        
        this.convolutedRedList = new int[width-2][height-2];
        this.convolutedGreenList = new int[width-2][height-2];
        this.convolutedBlueList = new int[width-2][height-2];
        
        fillColorLists();
        this.type = type;
        
        startConvolution();
        
        
    }

    public BufferedImage getConvolutedImage() {
        return convolutedImage;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    
    

    
    public void blur(){
        kernel = new float[][] {
            {2, 1, 2},
            {1, -1, 1},
            {2, 1, 2}
        };
        kernelDiv = 11;
        applyConvolution();
    }
    
    public void sharpening(){
        kernel = new float[][] {
            {0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}
        };
        kernelDiv = 1;
        applyConvolution();
    }
    
    public void smoothing(){
        kernel = new float[][] {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
        };
        kernelDiv = 16;
        applyConvolution();
    }
    
    
    public void raised(){
        kernel = new float[][] {
            {0, 0, -2},
            {0, 2, 0},
            {1, 2, 0}
        };
        kernelDiv = 1;
        applyConvolution();
    }
    
    public void outline(){
        kernel = new float[][] {
            {-1, -1, -1},
            {-1, 8, -1},
            {-1, -1, -1}
        };
        kernelDiv = 1;
        applyConvolution();
    }
    
    
    public void emboss(){
        kernel = new float[][] {
            {-2, -1, 0},
            {-1, 1, 1},
            {0, 1, 2}
        };
        kernelDiv = 1;
        applyConvolution();
    }
    
    
        private void startConvolution() {
        switch(type){
            case SHARPEN:
                sharpening();
                break;
                
            case SMOOTH:
                smoothing();
                break;
              
            case RAISE:
                raised();
                break;
                
            case OUTLINE:
                outline();
                break;
                
            case EMBOSS:
                emboss();
                break;
                
            case BLUR:
                blur();
                break;
                
                
            default:
                return;
        }
    }

    
    private void fillColorLists() {
         for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                redList[i][j] = new Color(image.getRGB(i, j)).getRed();
                greenList[i][j] = new Color(image.getRGB(i, j)).getGreen();
                blueList[i][j] = new Color(image.getRGB(i, j)).getBlue();
                alphaList[i][j] = new Color(image.getRGB(i, j)).getAlpha();


            }
         }
    }
    


    private void applyConvolution() {
        
        for(int i = 1; i < width-1; i++){
            for(int j = 1; j < height-1; j++){
                if(modRed) evolveColor(i, j, RED);
                if(modGreen) evolveColor(i, j, GREEN);
                if(modBlue) evolveColor(i, j, BLUE);
                
            }
        }
        
        setUpConvolutedImage();
        
    }

    private void evolveColor(int i, int j, int colorMod) {
        int[][] colorList;
        int color;
        if(colorMod == RED) colorList = redList;
        else if(colorMod == GREEN) colorList = greenList;
        else colorList = blueList;
        
        color = Math.round((
                (colorList[i-1][j-1]*kernel[0][0]) + 
                (colorList[i-1][j-0]*kernel[0][1]) + 
                (colorList[i-1][j+1]*kernel[0][2]) + 
                (colorList[i+0][j-1]*kernel[1][0]) + 
                (colorList[i+0][j-0]*kernel[1][1]) + 
                (colorList[i+0][j+1]*kernel[1][2]) + 
                (colorList[i+1][j-1]*kernel[2][0]) + 
                (colorList[i+1][j-0]*kernel[2][1]) + 
                (colorList[i+1][j+1]*kernel[2][2])
                ));
        color = Math.round(color/kernelDiv);
        
        
        if(color > 255) color = 255;
        if(color < 0) color = 0;
        
        
        
        if(colorMod == RED) convolutedRedList[i-1][j-1] = color;
        else if(colorMod == GREEN) convolutedGreenList[i-1][j-1] = color;
        else convolutedBlueList[i-1][j-1] = color;
        
        
        
    }
    
        
    private void setUpConvolutedImage() {
        int pixel;
        for (int i = 0; i < width - 2; i++) {
            for (int j = 0; j < height - 2; j++) {
                
                if(modRed && modGreen && modBlue){
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (convolutedGreenList[i][j] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(modRed && modGreen && !modBlue){
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (convolutedGreenList[i][j] << 8) | blueList[i+1][j+1];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(modRed && !modGreen && modBlue){
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (greenList[i+1][j+1] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(!modRed && modGreen && modBlue){
                    pixel = (alphaList[i][j] << 24) | (redList[i+1][j+1] << 16) | (convolutedGreenList[i][j] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(modRed){
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (greenList[i+1][j+1] << 8) | blueList[i+1][j+1];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(modGreen) {
                    pixel = (alphaList[i][j] << 24) | (redList[i+1][j+1] << 16) | (convolutedGreenList[i][j] << 8) | blueList[i+1][j+1];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(modBlue) {
                    pixel = (alphaList[i][j] << 24) | (redList[i+1][j+1] << 16) | (greenList[i+1][j+1] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
            }
        }

    }
    
    
 
        
        

    
}
