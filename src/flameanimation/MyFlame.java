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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author alfon
 */
public class MyFlame extends JFrame {
    //VARIABLES
    private boolean isPaused = false;
    private boolean isStoped = false;

    private static Viewer viewer;
    private Thread thread;
    private int viewerRate = 20;
    private FlamePalette flamePalette;
    private Flame flame1;
    private Flame flame2;
    private ControlPanel controlPanel;
    private GridBagConstraints constraints = new GridBagConstraints();  

    //MAIN
    public static void main(String[] args) {
        
        MyFlame myFlame = new MyFlame();
        
    }
    
    //CONSTRUCTOR
    public MyFlame(){
        
        //Set flame palette
        flamePalette = setFlamePalette(flamePalette);
        //Create flames
        flame1 = new Flame(500,850,BufferedImage.TYPE_INT_ARGB);
        flame1.setRate(90);
        flame1.setPalette(flamePalette);
        //Create viewer
        viewer = new Viewer(flame1);
        this.setViewerRate(viewerRate);
        //Create control panel
        controlPanel = new ControlPanel();
        controlPanel.setMyFlame(this);
        //Set the jframe
        setMyFlame();
       // this.add(viewer);
        setGridRules();
        //Start the viewer thread
        thread = new Thread(viewer);
        thread.start();
        //Set the jframe visible
        this.setVisible(true);
        
    }
    
    //PUBLIC METHODS
    //setFlamePalette: Prepare the palette
    public FlamePalette setFlamePalette(FlamePalette palette){
        palette = new FlamePalette();
        palette.addTargetColor(new TargetColor(255, Color.WHITE));
        palette.addTargetColor(new TargetColor(210, Color.YELLOW));
        palette.addTargetColor(new TargetColor(150, Color.ORANGE));
        palette.addTargetColor(new TargetColor(100, Color.ORANGE.darker()));
        palette.addTargetColor(new TargetColor(70, Color.RED));
        palette.addTargetColor(new TargetColor(0, Color.RED.darker()));
        return palette;

    }
    
    public void setGridRules(){
       
        constraints.gridx = 1; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.weightx = 1;
        constraints.weighty = 1;

        constraints.fill = GridBagConstraints.BOTH;

        this.add(viewer , constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 1;

        this.add (controlPanel, constraints);
                //constraints.gridx = 1; // El área de texto empieza en la columna cero.
//        constraints.gridy = 0; // El área de texto empieza en la fila cero

        //this.add(viewer , constraints);
    }
    
    //setStop: Not implemented
    public void setStop(){
         if(isStoped == false){
            isStoped = true;
            viewer.setFireThread(Viewer.FireState.STOP);
        }
    }

    //setPause: Not implemented
    public void setPause(){
        if(isPaused == false){
            isPaused = true;
            viewer.setFireThread(Viewer.FireState.PAUSE);
        } else{
            isPaused = false;
            viewer.setFireThread(Viewer.FireState.RESUME);

        }
        
    }
    
    
    
    
    //setViewerRate: Sets the framerate of the viewer
    public void setViewerRate(int rate){
        viewer.setRate(rate);
        
    }
    
    public void setFlameRate(int rate){
        flame1.setRate(rate);
    }

    //PRIVATE METHODS
    //setMyFlame: Sets the jframe
    private void setMyFlame() {
        
        this.setTitle("Flame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout (new GridBagLayout());
        
        
        this.setBounds(0, 0, 1360, 790);
        this.setResizable(false);        
    }
    
    
}
