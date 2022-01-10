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
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author alfon
 */
public class MyFlame extends JFrame {
    //VARIABLES
    private boolean isPaused;
    private boolean isStoped;
    private static Viewer viewer;
    private static MyFlame myFlame;
    private Thread thread;
    private int viewerRate = 20;
    private FlamePalette flamePalette;
    private Flame flame1;
    private Flame flame2;
    private GridBagConstraints constraints = new GridBagConstraints();  

    //MAIN
    public static void main(String[] args) {
        
        myFlame = new MyFlame();
        
        
    }
    
    //CONSTRUCTOR
    public MyFlame(){
        //Set flame palette
        flamePalette = setFlamePalette(flamePalette);
        //Create flames
        flame1 = new Flame(500,850,BufferedImage.TYPE_INT_ARGB);
        flame1.setPalette(flamePalette);
        //Create viewer
        viewer = new Viewer(flame1);
        this.setViewerRate(viewerRate);
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
        palette.addTargetColor(new TargetColor(0, Color.RED.darker().darker()));
        return palette;

    }
    
    public void setGridRules(){
        constraints.gridx = 0; // El área de texto empieza en la columna cero.
        constraints.gridy = 0; // El área de texto empieza en la fila cero
        constraints.weightx = 1;
        constraints.weighty = 1;

        constraints.fill = GridBagConstraints.BOTH;
        this.add(viewer , constraints);
        //constraints.gridx = 1; // El área de texto empieza en la columna cero.
//        constraints.gridy = 0; // El área de texto empieza en la fila cero

        //this.add(viewer , constraints);
    }
    
    //setStop: Not implemented
    public void setStop(boolean s){
        
    }

    //setPause: Not implemented
    public void setPause(boolean p){
        
    }
    
    //setViewerRate: Sets the framerate of the viewer
    public void setViewerRate(int rate){
        viewer.setRate(rate);
        
    }

    //PRIVATE METHODS
    //setMyFlame: Sets the jframe
    private void setMyFlame() {
        
        this.setTitle("Flame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout (new GridBagLayout());
        
        /*JButton boton1 = new JButton ("Boton 1");
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        this.getContentPane().add (boton1, constraints);*/
        this.setBounds(0, 0, 1360, 790);
        this.setResizable(false);        
    }
    
    
}
