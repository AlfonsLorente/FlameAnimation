/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;

/**
 *
 * @author alfon
 */
public class MyFlame extends JFrame {

    //VARIABLES
    private boolean isPaused = false;
    private boolean isExit = false;
    private int flameCoolAmount = 45;
    private static Viewer viewer;
    private Thread thread;
    private int viewerRate = 55;
    private int flameRate = 55;
    private FlamePalette flamePalette;
    private Flame flame;
    private FlameAnimation flameAnimation;
    private Thread fireThread, fireAnimationThread;

    private ControlPanel controlPanel;
    private GridBagConstraints constraints = new GridBagConstraints();
    private Color c1, c2, c3, c4, c5;
    private String audio = "MUSIC/zombies.wav";
    private Clip clip;
    private boolean audioPlaying = false;
    private String imageSrc = "IMG/darkHouse.jpg";

    private BufferedImage image;
    private BufferedImage convolutedImage;
    private Convolution convolution;
    private Convolution.Type convType = Convolution.Type.CENTERPOINTS;
    private boolean redState = true, greenState = true, blueState = true;
    private float[][] kernel = new float[3][3];
    private float kernelDiv = 1;
    private JMenuBar menubar;

    /**
     * enum that sets the fire state
     */
    enum FireState {
        EXIT,
        PAUSE,
        RESUME
    }

    //MAIN
    public static void main(String[] args) {
        new MyFlame();

    }

    //CONSTRUCTOR
    /**
     * sets up ALL the application
     */
    public MyFlame() {

        //Set flame palette
        flamePalette = setFlamePalette(flamePalette);

        //Set image and convoluted image
        setUpImages();

        //Create flames
        setUpFlame();
        setUpFlameAnimation();

        fireThread = new Thread(flame);
        fireThread.start();

        fireAnimationThread = new Thread(flameAnimation);
        fireAnimationThread.start();

        //set viewer up
        setUpViewer();

        //Create control panel
        controlPanel = new ControlPanel();
        controlPanel.setMyFlame(this);

        //Set the jframe (this class)
        setMyFlame();

        //set Audio
        setUpAudio(audio);

        //Set the grid rules for viewer and control panel
        setGridRules();

        //Start the viewer thread
        thread = new Thread(viewer);
        thread.start();

        //Set the jframe visible
        this.setVisible(true);

    }

    //GETTERS AND SETTERS
    /**
     * returns the first color of the palette
     *
     * @return Color
     */
    public Color getC1() {
        return c1;
    }

    /**
     * sets the first color of the palette
     *
     * @param c1 - Color
     */
    public void setC1(Color c1) {
        this.c1 = c1;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    /**
     * returns the second color of the palette
     *
     * @return Color
     */
    public Color getC2() {
        return c2;
    }

    /**
     * sets the second color of the palette
     *
     * @param c2 - Color
     */
    public void setC2(Color c2) {
        this.c2 = c2;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    /**
     * returns the third color of the palette
     *
     * @return Color
     */
    public Color getC3() {
        return c3;
    }

    /**
     * sets the third color of the palette
     *
     * @param c3 - Color
     */
    public void setC3(Color c3) {
        this.c3 = c3;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    /**
     * returns the fourth color of the palette
     *
     * @return Color
     */
    public Color getC4() {
        return c4;
    }

    /**
     * sets the fourth color of the palette
     *
     * @param c4 - Color
     */
    public void setC4(Color c4) {
        this.c4 = c4;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    /**
     * returns the fifth color of the palette
     *
     * @return Color
     */
    public Color getC5() {
        return c5;
    }

    /**
     * sets the fifth color of the palette
     *
     * @param c5 - Color
     */
    public void setC5(Color c5) {
        this.c5 = c5;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    //PUBLIC METHODS
    /**
     * change the convoluted image depending on its type
     *
     * @param newType
     */
    public void changeConvolutedImage(Convolution.Type newType) {
        if (!convType.equals(newType)) {
            convType = newType;
            if (convType.equals(Convolution.Type.PERSONALITZED)) {
                convolution = new Convolution(image, redState, greenState, blueState, kernel, kernelDiv);
            } else {
                convolution = new Convolution(image, convType, redState, greenState, blueState);

            }
            convolutedImage = convolution.getConvolutedImage();
            viewer.setConvolutedImage(convolutedImage);
            flameAnimation.setConvolutedImage(convolutedImage);

        }
    }

    /**
     * change the convoluted image depending on the colors to convolute
     *
     * @param colorName - String
     * @param colorState - boolean
     */
    public void changeConvolutedImage(String colorName, boolean colorState) {
        if (colorName.equals("red")) {
            redState = colorState;
        } else if (colorName.equals("green")) {
            greenState = colorState;
        } else if (colorName.equals("blue")) {
            blueState = colorState;
        }

        if (convType.equals(Convolution.Type.PERSONALITZED)) {
            convolution = new Convolution(image, redState, greenState, blueState, kernel, kernelDiv);
        } else {
            convolution = new Convolution(image, convType, redState, greenState, blueState);

        }

        convolutedImage = convolution.getConvolutedImage();

        viewer.setConvolutedImage(convolutedImage);
        flameAnimation.setConvolutedImage(convolutedImage);
    }

    /**
     * change the kernel
     *
     * @param kernel - float[][]
     * @param div float
     */
    void changeConvolutionKernel(float[][] kernel, float div) {
        this.convType = Convolution.Type.PERSONALITZED;
        this.kernel = kernel;
        this.kernelDiv = div;
        convolution = new Convolution(image, redState, greenState, blueState, kernel, div);
        convolutedImage = convolution.getConvolutedImage();
        flameAnimation.setConvolutedImage(convolutedImage);
        viewer.setConvolutedImage(convolutedImage);
    }

    /**
     * change the image and so the convoluted image
     *
     * @param imageSrc - String
     */
    public void changeImage(String imageSrc) {
        try {
            image = ImageIO.read(new File(imageSrc));
        } catch (IOException ex) {
            Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (convType.equals(Convolution.Type.PERSONALITZED)) {
            convolution = new Convolution(image, redState, greenState, blueState, kernel, kernelDiv);

        } else {
            convolution = new Convolution(image, convType, redState, greenState, blueState);
        }
        convolutedImage = convolution.getConvolutedImage();

        viewer.setImage(image);
        viewer.setConvolutedImage(convolutedImage);
        //Gotta restart the flame animation with the new convolutedImage
        flameAnimation.setAlive(false);
        setUpFlameAnimation();
        fireAnimationThread = new Thread(flameAnimation);
        fireAnimationThread.start();

        viewer.setFlameAnimation(flameAnimation);

    }

    /**
     * change the luminance minimum
     *
     * @param value
     */
    void setLuminanceMin(int value) {
        flameAnimation.setLuminanceMin((float) value / 100);

    }

    /**
     * Exists the application
     */
    public void setExit() {
        if (isExit == false) {
            isExit = true;
            this.setFireThread(Viewer.FireState.EXIT);
        }
    }

    /**
     * sets up the flames cool
     *
     * @param flameCoolAmount
     */
    public void setFlameCoolAmount(int flameCoolAmount) {
        this.flameCoolAmount = flameCoolAmount;
        flame.setCoolAmount(flameCoolAmount);
        flameAnimation.setCoolAmount(flameCoolAmount);
    }

    /**
     * Prepare the default flame palette
     *
     * @param palette - FlamePalette
     * @return - FlamePalette
     */
    public FlamePalette setFlamePalette(FlamePalette palette) {
        //Create the palette
        palette = new FlamePalette();
        c1 = new Color(255, 255, 255, 255);
        c2 = new Color(255, 233, 40, 225);
        c3 = new Color(255, 165, 0, 200);
        c4 = new Color(255, 0, 0, 175);
        c5 = new Color(75, 1, 15, 140);
        //set targets to the palette
        palette.addTargetColor(new TargetColor(255, c1));
        palette.addTargetColor(new TargetColor(80, c2));
        palette.addTargetColor(new TargetColor(40, c3));
        palette.addTargetColor(new TargetColor(15, c4));
        palette.addTargetColor(new TargetColor(0, c5));
        return palette;

    }

    /**
     * change the flame palette
     *
     * @param c1 - Color 1
     * @param c2 - Color 2
     * @param c3 - Color 3
     * @param c4 - Color 4
     * @param c5 - Color 5
     */
    public void setFlamePalette(Color c1, Color c2, Color c3, Color c4, Color c5) {
        //Set new palette colors
        c1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 255);
        c2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 200);
        c3 = new Color(c3.getRed(), c3.getGreen(), c3.getBlue(), 230);
        c4 = new Color(c4.getRed(), c4.getGreen(), c4.getBlue(), 175);
        c5 = new Color(c5.getRed(), c5.getGreen(), c5.getBlue(), 140);
        //set targets to the palette
        FlamePalette palette = new FlamePalette();
        palette.addTargetColor(new TargetColor(255, c1));
        palette.addTargetColor(new TargetColor(80, c2));
        palette.addTargetColor(new TargetColor(30, c3));
        palette.addTargetColor(new TargetColor(20, c4));
        palette.addTargetColor(new TargetColor(0, c5));
        flamePalette = palette;
        flame.setPalette(flamePalette);
        flameAnimation.setPalette(flamePalette);

    }

    /**
     * sets the state of the fire or exits app
     *
     * @param state - Viewer.FireState
     */
    public void setFireThread(Viewer.FireState state) {
        switch (state) {
            case EXIT:
                System.exit(0);
                break;
            case PAUSE:
                fireThread.suspend();
                fireAnimationThread.suspend();
                break;
            case RESUME:
                fireThread.resume();
                fireAnimationThread.resume();
                break;
        }
    }

    /**
     * sets the flame rate
     *
     * @param rate - int
     */
    public void setFlameRate(int rate) {
        flame.setRate(rate);
        flameAnimation.setRate(rate);
    }

    /**
     * pauses and despauses the application
     */
    public void setPause() {
        if (isPaused == false) {
            isPaused = true;
            this.setFireThread(Viewer.FireState.PAUSE);
        } else {
            isPaused = false;
            this.setFireThread(Viewer.FireState.RESUME);

        }

    }

    /**
     * Sets the framerate of the viewer
     *
     * @param rate - int
     */
    public void setViewerRate(int rate) {
        viewer.setRate(rate);

    }

    /**
     * start or stop the song
     */
    public void songController() {
        if (audioPlaying) {
            audioPlaying = false;
            clip.stop();
        } else {
            audioPlaying = true;
            clip.start();
        }
    }

    //PRIVATE METHODS
    /**
     * set up the gridbag layout rules for viewer and control panel
     */
    private void setGridRules() {
        //Set the constraints up for the viewer
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        //Add the viewer with the contraints.
        this.add(viewer, constraints);

        //Set the constraints up for the control panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 1;
        //Add the control panel with the contraints.
        this.add(controlPanel, constraints);

    }

    /**
     * Sets the jframe (this class)
     */
    private void setMyFlame() {
        this.setTitle("Flame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout());
        this.setBounds(0, 0, 1360, 790);

        //window mode
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

        //icon image
        this.setIconImage(new ImageIcon("ICON/flameIco.png").getImage());

        //change menu bar
        menubar = new JMenuBar();
        menubar.setOpaque(true);
        menubar.setBackground(Color.RED);
        this.setJMenuBar(menubar);
        this.setResizable(false);
    }

    /**
     * Sets up the audio system
     *
     * @param audio - String (file path)
     */
    public void setUpAudio(String audio) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audio).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * sets up the flame
     */
    private void setUpFlame() {
        flame = new Flame(300, 600, BufferedImage.TYPE_INT_ARGB);
        flame.setRate(flameRate);
        flame.setPalette(flamePalette);
        flame.setCoolAmount(flameCoolAmount);

    }

    /**
     * sets up the flame animation
     */
    private void setUpFlameAnimation() {
        flameAnimation = new FlameAnimation(convolutedImage.getWidth(),
                convolutedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB, convolutedImage);
        flameAnimation.setRate(30);
        flameAnimation.setPalette(flamePalette);
        flameAnimation.setCoolAmount(flameCoolAmount);
    }

    /**
     * set up the image and the convoluted image
     */
    private void setUpImages() {
        try {
            image = ImageIO.read(new File(imageSrc));
        } catch (IOException ex) {
            Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (convType.equals(Convolution.Type.PERSONALITZED)) {
            convolution = new Convolution(image, redState, greenState, blueState, kernel, kernelDiv);

        } else {
            convolution = new Convolution(image, convType, redState, greenState, blueState);
        }
        convolutedImage = convolution.getConvolutedImage();

    }

    /**
     * sets up the viewer
     */
    private void setUpViewer() {
        viewer = new Viewer(flame, image, convolutedImage, flameAnimation);
        this.setViewerRate(viewerRate);
    }

}
