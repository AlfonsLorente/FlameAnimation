/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Color;
import java.util.ArrayList;


/**
 *
 * @author alfon
 */
public class FlamePalette {
    
    //VARIABLES
    private ArrayList<TargetColor> targetColorList;
    private Color[] colorList;
    private int depth;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
    
    //PUBLIC METHODS
    public void addTargetColor(TargetColor targetColor){
        
    }
    
    public Color getColor(int temperature){
        return Color.BLACK;
    }
    
    
    //PRIVATE METHODS
    private void createColors(){
        //Variables
        TargetColor targetPrev;
        TargetColor targetNew;
        //Set depth
        if(this.colorList == null){
            this.colorList = new Color[depth];
        }
        
        //Sort the list of target colors
        //for()
        
        
    }
    
    private void interpolateColors(TargetColor targetFrom, TargetColor targetEnd){
        
    }
    
}
