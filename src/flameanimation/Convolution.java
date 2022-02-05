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
        this.convolutedImage = new BufferedImage(width, height, image.getType());
        this.modRed = modRed;
        this.modGreen = modGreen;
        this.modBlue = modBlue;

        this.redList = new int[width][height];
        this.greenList = new int[width][height];
        this.blueList = new int[width][height];
        this.alphaList = new int[width][height];
        
        this.convolutedRedList = new int[width][height];
        this.convolutedGreenList = new int[width][height];
        this.convolutedBlueList = new int[width][height];
        
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

    public int[][] getRedList() {
        if(modRed){
            return convolutedRedList;
        }else{
            return redList;

        }
    }

    public int[][] getGreenList() {
        if(modRed){
            return convolutedGreenList;
        }else{
            return greenList;

        }
    }

    public int[][] getBlueList() {
        if(modRed){
            return convolutedBlueList;
        }else{
            return blueList;

        }
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
                redList[i][j] = new Color(image.getRGB(i, j), true).getRed();
                greenList[i][j] = new Color(image.getRGB(i, j), true).getGreen();
                blueList[i][j] = new Color(image.getRGB(i, j), true).getBlue();
                alphaList[i][j] = new Color(image.getRGB(i, j), true).getAlpha();


            }
         }
    }
    


    private void applyConvolution() {
        
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(modRed) evolveColor(i, j, RED);
                if(modGreen) evolveColor(i, j, GREEN);
                if(modBlue) evolveColor(i, j, BLUE);
                
            }
        }
        
        setUpConvolutedImage();
        
    }

    private void evolveColor(int i, int j, int colorMod) {
        int[][] colorList;
        int color = 0;
        if(colorMod == RED) colorList = redList;
        else if(colorMod == GREEN) colorList = greenList;
        else colorList = blueList;
        
        if(i == 0 && j == 0){
            color = Math.round((
                (colorList[i+0][j-0]*kernel[1][1]) + 
                (colorList[i+0][j+1]*kernel[1][2]) + 
                (colorList[i+1][j-0]*kernel[2][1]) + 
                (colorList[i+1][j+1]*kernel[2][2])
                ));
            color = Math.round(color/kernelDiv);
        
            
        }else if(i == width-1 && j == height-1){
            color = Math.round((
                (colorList[i-1][j-1]*kernel[0][0]) + 
                (colorList[i-1][j-0]*kernel[0][1]) + 
                (colorList[i+0][j-1]*kernel[1][0]) + 
                (colorList[i+0][j-0]*kernel[1][1]) 
                ));
            color = Math.round(color/kernelDiv);
        }else if(i == 0 && j == height-1){
            color = Math.round((
                (colorList[i+0][j-1]*kernel[1][0]) + 
                (colorList[i+0][j-0]*kernel[1][1]) + 
                (colorList[i+1][j-1]*kernel[2][0]) + 
                (colorList[i+1][j-0]*kernel[2][1]) 
                ));
            color = Math.round(color/kernelDiv);
        }else if(i == width-1 && j == 0){
            color = Math.round((
                (colorList[i-1][j-0]*kernel[0][1]) + 
                (colorList[i-1][j+1]*kernel[0][2]) + 
                (colorList[i+0][j-0]*kernel[1][1]) + 
                (colorList[i+0][j+1]*kernel[1][2]) 
                ));
            color = Math.round(color/kernelDiv);
        
        }else if(i == 0){
            color = Math.round((
                (colorList[i+0][j-1]*kernel[1][0]) + 
                (colorList[i+0][j-0]*kernel[1][1]) + 
                (colorList[i+0][j+1]*kernel[1][2]) + 
                (colorList[i+1][j-1]*kernel[2][0]) + 
                (colorList[i+1][j-0]*kernel[2][1]) + 
                (colorList[i+1][j+1]*kernel[2][2])
                ));
            color = Math.round(color/kernelDiv);
        }else if(i == width-1){
            color = Math.round((
                (colorList[i-1][j-1]*kernel[0][0]) + 
                (colorList[i-1][j-0]*kernel[0][1]) + 
                (colorList[i-1][j+1]*kernel[0][2]) + 
                (colorList[i+0][j-1]*kernel[1][0]) + 
                (colorList[i+0][j-0]*kernel[1][1]) + 
                (colorList[i+0][j+1]*kernel[1][2]) 
                ));
            color = Math.round(color/kernelDiv);
        }else if(j == 0){
            color = Math.round((
                (colorList[i-1][j-0]*kernel[0][1]) + 
                (colorList[i-1][j+1]*kernel[0][2]) + 
                (colorList[i+0][j-0]*kernel[1][1]) + 
                (colorList[i+0][j+1]*kernel[1][2]) + 
                (colorList[i+1][j-0]*kernel[2][1]) + 
                (colorList[i+1][j+1]*kernel[2][2])
                ));
            color = Math.round(color/kernelDiv);
        
        }else if(j == height-1){
            color = Math.round((
                (colorList[i-1][j-1]*kernel[0][0]) + 
                (colorList[i-1][j-0]*kernel[0][1]) + 
                (colorList[i+0][j-1]*kernel[1][0]) + 
                (colorList[i+0][j-0]*kernel[1][1]) + 
                (colorList[i+1][j-1]*kernel[2][0]) + 
                (colorList[i+1][j-0]*kernel[2][1]) 
                ));
            color = Math.round(color/kernelDiv);
        
            
        }else{
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
        
        
        }
        
        
        
        if(color > 255) color = 255;
        if(color < 0) color = 0;
        
        
        
        if(colorMod == RED) convolutedRedList[i][j] = color;
        else if(colorMod == GREEN) convolutedGreenList[i][j] = color;
        else convolutedBlueList[i][j] = color;
        
        
        
    }
    
        
    private void setUpConvolutedImage() {
        int pixel;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                
                if(modRed && modGreen && modBlue){
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (convolutedGreenList[i][j] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(modRed && modGreen && !modBlue){
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (convolutedGreenList[i][j] << 8) | blueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(modRed && !modGreen && modBlue){
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (greenList[i][j] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(!modRed && modGreen && modBlue){
                    pixel = (alphaList[i][j] << 24) | (redList[i][j] << 16) | (convolutedGreenList[i][j] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(modRed){
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (greenList[i][j] << 8) | blueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(modGreen) {
                    pixel = (alphaList[i][j] << 24) | (redList[i][j] << 16) | (convolutedGreenList[i][j] << 8) | blueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
                else if(modBlue) {
                    pixel = (alphaList[i][j] << 24) | (redList[i][j] << 16) | (greenList[i][j] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }else{
                    pixel = (alphaList[i][j] << 24) | (redList[i][j] << 16) | (greenList[i][j] << 8) | blueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
            }
        }

    }
    
    
 
        
        

    
}
