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
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

/**
 *
 * @author alfon
 */
public class MyFlame extends JFrame {

    //VARIABLES
    private boolean isPaused = false;
    private boolean isExit = false;
    private int flameCoolAmount = 70;
    private static Viewer viewer;
    private Thread thread;
    private int viewerRate = 50;
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
    private String imageSrc = "IMG/paisajeBonito.jpg";

    private BufferedImage image;
    private BufferedImage convolutedImage;
    private Convolution convolution;
    private Convolution.Type convType = Convolution.Type.CENTERPOINTS;
    private boolean redState = true, greenState = true, blueState = true;
    private float[][] kernel = new float[3][3];
    private float kernelDiv = 1;

    //fireState: enum that sets the fire state
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
    public MyFlame() {

        //Set flame palette
        flamePalette = setFlamePalette(flamePalette);

        //Create viewer
        setUpImages();
        //Create flames

        setUpFlame();

        setUpFlameAnimation();

        fireThread = new Thread(flame);
        fireThread.start();

        fireAnimationThread = new Thread(flameAnimation);
        fireAnimationThread.start();

        setUpViewer();

        //Create control panel
        controlPanel = new ControlPanel();

        controlPanel.setMyFlame(this);
        //Set the jframe

        setMyFlame();

        //set Audio
        //setUpAudio(audio);
        setGridRules();

        //Start the viewer thread
        thread = new Thread(viewer);

        thread.start();

        //Set the jframe visible
        this.setVisible(true);

    }

    //GETTERS AND SETTERS
    public Color getC1() {
        return c1;
    }

    public void setC1(Color c1) {
        this.c1 = c1;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    public Color getC2() {
        return c2;
    }

    public void setC2(Color c2) {
        this.c2 = c2;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    public Color getC3() {
        return c3;
    }

    public void setC3(Color c3) {
        this.c3 = c3;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    public Color getC4() {
        return c4;
    }

    public void setC4(Color c4) {
        this.c4 = c4;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    public Color getC5() {
        return c5;
    }

    public void setC5(Color c5) {
        this.c5 = c5;
        this.setFlamePalette(c1, c2, c3, c4, c5);
    }

    private void setUpFlame() {
        flame = new Flame(600, 450, BufferedImage.TYPE_INT_ARGB);
        flame.setRate(30);
        flame.setPalette(flamePalette);
        flame.setCoolAmount(flameCoolAmount);

    }

    private void setUpViewer() {
        viewer = new Viewer(flame, image, convolutedImage, flameAnimation);
        this.setViewerRate(viewerRate);
    }

    public void setFlameCoolAmount(int flameCoolAmount) {
        this.flameCoolAmount = flameCoolAmount;
        flame.setCoolAmount(flameCoolAmount);
        flameAnimation.setCoolAmount(flameCoolAmount);
    }

    //PUBLIC METHODS
    //setFlamePalette: Prepare the palette
    public FlamePalette setFlamePalette(FlamePalette palette) {
        //Create the palette
        palette = new FlamePalette();
        c1 = new Color(255, 255, 255, 255);
        c2 = new Color(255, 233, 0, 240);
        c3 = new Color(155,135,12, 230);
        c4 = new Color(204,0,0, 220);
        c5 = new Color(51,46,46, 210);
        //set the palette colors
        palette.addTargetColor(new TargetColor(255, c1));
        palette.addTargetColor(new TargetColor(50, c2));
        palette.addTargetColor(new TargetColor(20, c3));
        palette.addTargetColor(new TargetColor(15, c4));
        palette.addTargetColor(new TargetColor(0, c5));
        return palette;

    }

    //setFlamePalette: Prepare the palette
    public void setFlamePalette(Color c1, Color c2, Color c3, Color c4, Color c5) {
        //Set new palette colors
        c1 = new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 255);
        c2 = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 240);
        c3 = new Color(c3.getRed(), c3.getGreen(), c3.getBlue(), 230);
        c4 = new Color(c4.getRed(), c4.getGreen(), c4.getBlue(), 220);
        c5 = new Color(c5.getRed(), c5.getGreen(), c5.getBlue(), 210);
        
        FlamePalette palette = new FlamePalette();
        palette.addTargetColor(new TargetColor(255, c1));
        palette.addTargetColor(new TargetColor(170, c2));
        palette.addTargetColor(new TargetColor(90, c3));
        palette.addTargetColor(new TargetColor(15, c4));
        palette.addTargetColor(new TargetColor(0, c5));
        flamePalette = palette;
        flame.setPalette(flamePalette);
        flameAnimation.setPalette(flamePalette);

    }

    //setExit: Exists the application
    public void setExit() {
        if (isExit == false) {
            isExit = true;
            this.setFireThread(Viewer.FireState.EXIT);
        }
    }

    //setPause: pauses and despauses the application
    public void setPause() {
        if (isPaused == false) {
            isPaused = true;
            this.setFireThread(Viewer.FireState.PAUSE);
        } else {
            isPaused = false;
            this.setFireThread(Viewer.FireState.RESUME);

        }

    }

    //setFireThread: sets the state of the fire
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

    private void setUpFlameAnimation() {
        flameAnimation = new FlameAnimation(convolutedImage.getWidth(),
                convolutedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB, convolutedImage);
        flameAnimation.setRate(30);
        flameAnimation.setPalette(flamePalette);
        flameAnimation.setCoolAmount(flameCoolAmount);
    }

    //setViewerRate: Sets the framerate of the viewer
    public void setViewerRate(int rate) {
        viewer.setRate(rate);

    }

    public void setFlameRate(int rate) {
        flame.setRate(rate);
        flameAnimation.setRate(rate);
    }

    //PRIVATE METHODS
    //setMyFlame: Sets the jframe
    private void setMyFlame() {
        this.setTitle("Flame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout());
        this.setBounds(0, 0, 1360, 790);
        this.setResizable(false);
    }

    //setGridRules: set up the gridbag layout rules
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

    //setUpAudio: Sets up the audio system
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

    //songController: start or stop the song
    public void songController() {
        if (audioPlaying) {
            audioPlaying = false;
            clip.stop();
        } else {
            audioPlaying = true;
            clip.start();
        }
    }


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
        flameAnimation.setAlive(false);
        setUpFlameAnimation();
        fireAnimationThread = new Thread(flameAnimation);
        fireAnimationThread.start();
        
        flameAnimation.setConvolutedImage(convolutedImage);

        viewer.setFlameAnimation(flameAnimation);

    }

    void changeConvolutionKernel(float[][] kernel, float div) {
        this.convType = Convolution.Type.PERSONALITZED;
        this.kernel = kernel;
        this.kernelDiv = div;
        convolution = new Convolution(image, redState, greenState, blueState, kernel, div);
        convolutedImage = convolution.getConvolutedImage();
        flameAnimation.setConvolutedImage(convolutedImage);
        viewer.setConvolutedImage(convolutedImage);
    }

}
