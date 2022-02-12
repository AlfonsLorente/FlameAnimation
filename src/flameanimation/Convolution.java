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
public class Convolution {

    //VARIABLES
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

    //ENUMS
    /**
     * Indicates the convolution type
     */
    enum Type {
        SHARPEN,
        SMOOTH,
        RAISE,
        OUTLINE,
        EMBOSS,
        BLUR,
        CENTERPOINTS,
        PERSONALITZED
    }

    //CONSTRUCTIORS
    /**
     * Sets up all the necessary global variables to start and starts the
     * convolution
     *
     * @param image - BufferedImage
     * @param type - Convolution.Type
     * @param modRed - boolean
     * @param modGreen - boolean
     * @param modBlue - boolean
     */
    public Convolution(BufferedImage image, Type type, boolean modRed, boolean modGreen, boolean modBlue) {
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

    /**
     * Sets up all the variables to start but in this case the user sets up the
     * kernel.
     *
     * @param image - BufferedImage
     * @param redState - boolean
     * @param greenState - boolean
     * @param blueState - boolean
     * @param kernel - float[][]
     * @param div - float
     */
    Convolution(BufferedImage image, boolean redState, boolean greenState, boolean blueState, float[][] kernel, float div) {
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
        this.type = Type.PERSONALITZED;
        this.kernel = kernel;
        this.kernelDiv = div;

        startConvolution();
    }

    //GETTERS AND SETTERS
    /**
     * gets the convoluted image
     *
     * @return BufferedImage
     */
    public BufferedImage getConvolutedImage() {
        return convolutedImage;
    }

    /**
     * sets the image to convolute
     *
     * @param image - BufferedImage
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * sets the kernel to convolute
     *
     * @param kernel - float[][]
     */
    public void setKernel(float[][] kernel) {
        this.kernel = kernel;
    }

    /**
     * Sets the kernel division
     *
     * @param kernelDiv - float
     */
    public void setKernelDiv(float kernelDiv) {
        this.kernelDiv = kernelDiv;
    }

    //PUBLIC METHODS
    /**
     * blur effect kernel
     */
    public void blur() {
        kernel = new float[][]{
            {2, 1, 2},
            {1, -1, 1},
            {2, 1, 2}
        };
        kernelDiv = 11;
        applyConvolution();
    }

    /**
     * sharpen effect kernel
     */
    public void sharpening() {
        kernel = new float[][]{
            {0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}
        };
        kernelDiv = 1;
        applyConvolution();
    }

    /**
     * smooth effect kernel
     */
    public void smoothing() {
        kernel = new float[][]{
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
        };
        kernelDiv = 16;
        applyConvolution();
    }

    /**
     * raise effect kernel
     */
    public void raised() {
        kernel = new float[][]{
            {0, 0, -2},
            {0, 2, 0},
            {1, 2, 0}
        };
        kernelDiv = 1;
        applyConvolution();
    }

    /**
     * outline effect kernel
     */
    public void outline() {
        kernel = new float[][]{
            {-1, -1, -1},
            {-1, 8, -1},
            {-1, -1, -1}
        };
        kernelDiv = 1;
        applyConvolution();
    }

    /**
     * emboss effect kernel
     */
    public void emboss() {
        kernel = new float[][]{
            {-2, -1, 0},
            {-1, 1, 1},
            {0, 1, 2}
        };
        kernelDiv = 1;
        applyConvolution();
    }

    /**
     * center points effect kernel
     */
    public void centerPoints() {
        kernel = new float[][]{
            {-1, 0, -1},
            {0, 4, 0},
            {-1, 0, -1}
        };
        kernelDiv = 1;
        applyConvolution();
    }

    /**
     * starts the convolution depending on its type
     */
    private void startConvolution() {
        switch (type) {
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

            case CENTERPOINTS:
                centerPoints();
                break;

            case PERSONALITZED:
                applyConvolution();
            default:
                return;
        }
    }

    
    /**
     * fills all the color lists getting each channel color
     */
    private void fillColorLists() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                redList[i][j] = new Color(image.getRGB(i, j), true).getRed();
                greenList[i][j] = new Color(image.getRGB(i, j), true).getGreen();
                blueList[i][j] = new Color(image.getRGB(i, j), true).getBlue();
                alphaList[i][j] = new Color(image.getRGB(i, j), true).getAlpha();

            }
        }
    }
    
    
    /**
     * Apply convolutions depending on the color selection
     */
    private void applyConvolution() {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (modRed) {
                    evolveColor(i, j, RED);
                }
                if (modGreen) {
                    evolveColor(i, j, GREEN);
                }
                if (modBlue) {
                    evolveColor(i, j, BLUE);
                }

            }
        }

        setUpConvolutedImage();

    }

    /**
     * evolve the convolution doing all the maths.
     * @param i - int
     * @param j - int
     * @param colorMod - int (constant) 
     */
    private void evolveColor(int i, int j, int colorMod) {
        //seting one color list or another depending on the colorMod
        int[][] colorList;
        int color = 0;
        if (colorMod == RED) {
            colorList = redList;
        } else if (colorMod == GREEN) {
            colorList = greenList;
        } else {
            colorList = blueList;
        }
        
        //Convolute pixels including the side ones.
        if (i == 0 && j == 0) {
            color = Math.round(((colorList[i + 0][j - 0] * kernel[1][1])
                    + (colorList[i + 0][j + 1] * kernel[1][2])
                    + (colorList[i + 1][j - 0] * kernel[2][1])
                    + (colorList[i + 1][j + 1] * kernel[2][2])));
            color = Math.round(color / kernelDiv);

        } else if (i == width - 1 && j == height - 1) {
            color = Math.round(((colorList[i - 1][j - 1] * kernel[0][0])
                    + (colorList[i - 1][j - 0] * kernel[0][1])
                    + (colorList[i + 0][j - 1] * kernel[1][0])
                    + (colorList[i + 0][j - 0] * kernel[1][1])));
            color = Math.round(color / kernelDiv);
        } else if (i == 0 && j == height - 1) {
            color = Math.round(((colorList[i + 0][j - 1] * kernel[1][0])
                    + (colorList[i + 0][j - 0] * kernel[1][1])
                    + (colorList[i + 1][j - 1] * kernel[2][0])
                    + (colorList[i + 1][j - 0] * kernel[2][1])));
            color = Math.round(color / kernelDiv);
        } else if (i == width - 1 && j == 0) {
            color = Math.round(((colorList[i - 1][j - 0] * kernel[0][1])
                    + (colorList[i - 1][j + 1] * kernel[0][2])
                    + (colorList[i + 0][j - 0] * kernel[1][1])
                    + (colorList[i + 0][j + 1] * kernel[1][2])));
            color = Math.round(color / kernelDiv);

        } else if (i == 0) {
            color = Math.round(((colorList[i + 0][j - 1] * kernel[1][0])
                    + (colorList[i + 0][j - 0] * kernel[1][1])
                    + (colorList[i + 0][j + 1] * kernel[1][2])
                    + (colorList[i + 1][j - 1] * kernel[2][0])
                    + (colorList[i + 1][j - 0] * kernel[2][1])
                    + (colorList[i + 1][j + 1] * kernel[2][2])));
            color = Math.round(color / kernelDiv);
        } else if (i == width - 1) {
            color = Math.round(((colorList[i - 1][j - 1] * kernel[0][0])
                    + (colorList[i - 1][j - 0] * kernel[0][1])
                    + (colorList[i - 1][j + 1] * kernel[0][2])
                    + (colorList[i + 0][j - 1] * kernel[1][0])
                    + (colorList[i + 0][j - 0] * kernel[1][1])
                    + (colorList[i + 0][j + 1] * kernel[1][2])));
            color = Math.round(color / kernelDiv);
        } else if (j == 0) {
            color = Math.round(((colorList[i - 1][j - 0] * kernel[0][1])
                    + (colorList[i - 1][j + 1] * kernel[0][2])
                    + (colorList[i + 0][j - 0] * kernel[1][1])
                    + (colorList[i + 0][j + 1] * kernel[1][2])
                    + (colorList[i + 1][j - 0] * kernel[2][1])
                    + (colorList[i + 1][j + 1] * kernel[2][2])));
            color = Math.round(color / kernelDiv);

        } else if (j == height - 1) {
            color = Math.round(((colorList[i - 1][j - 1] * kernel[0][0])
                    + (colorList[i - 1][j - 0] * kernel[0][1])
                    + (colorList[i + 0][j - 1] * kernel[1][0])
                    + (colorList[i + 0][j - 0] * kernel[1][1])
                    + (colorList[i + 1][j - 1] * kernel[2][0])
                    + (colorList[i + 1][j - 0] * kernel[2][1])));
            color = Math.round(color / kernelDiv);

        } else {
            color = Math.round(((colorList[i - 1][j - 1] * kernel[0][0])
                    + (colorList[i - 1][j - 0] * kernel[0][1])
                    + (colorList[i - 1][j + 1] * kernel[0][2])
                    + (colorList[i + 0][j - 1] * kernel[1][0])
                    + (colorList[i + 0][j - 0] * kernel[1][1])
                    + (colorList[i + 0][j + 1] * kernel[1][2])
                    + (colorList[i + 1][j - 1] * kernel[2][0])
                    + (colorList[i + 1][j - 0] * kernel[2][1])
                    + (colorList[i + 1][j + 1] * kernel[2][2])));
            color = Math.round(color / kernelDiv);

        }

        //Controlling max and in color 
        if (color > 255) {
            color = 255;
        }
        if (color < 0) {
            color = 0;
        }
        
        //Seting the color convolute list depending on the colorMod.
        if (colorMod == RED) {
            convolutedRedList[i][j] = color;
        } else if (colorMod == GREEN) {
            convolutedGreenList[i][j] = color;
        } else {
            convolutedBlueList[i][j] = color;
        }

    }

    
    /**
     * Set up and construct the convolute image 
     */
    private void setUpConvolutedImage() {
        int pixel;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (modRed && modGreen && modBlue) {
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (convolutedGreenList[i][j] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                } else if (modRed && modGreen && !modBlue) {
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (convolutedGreenList[i][j] << 8) | blueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                } else if (modRed && !modGreen && modBlue) {
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (greenList[i][j] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                } else if (!modRed && modGreen && modBlue) {
                    pixel = (alphaList[i][j] << 24) | (redList[i][j] << 16) | (convolutedGreenList[i][j] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                } else if (modRed) {
                    pixel = (alphaList[i][j] << 24) | (convolutedRedList[i][j] << 16) | (greenList[i][j] << 8) | blueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                } else if (modGreen) {
                    pixel = (alphaList[i][j] << 24) | (redList[i][j] << 16) | (convolutedGreenList[i][j] << 8) | blueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                } else if (modBlue) {
                    pixel = (alphaList[i][j] << 24) | (redList[i][j] << 16) | (greenList[i][j] << 8) | convolutedBlueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                } else {
                    pixel = (alphaList[i][j] << 24) | (redList[i][j] << 16) | (greenList[i][j] << 8) | blueList[i][j];
                    convolutedImage.setRGB(i, j, pixel);
                }
            }
        }

    }

}
