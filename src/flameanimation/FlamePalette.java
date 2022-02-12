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
    private ArrayList<TargetColor> targetColorList = new ArrayList<TargetColor>();
    private int[] colorList;

    //CONSTRUCOR
    public FlamePalette(){
        
    }
    
    //PUBLIC METHODS
    /**
     * Adds a target color to a list
     * @param targetColor - TargetColor
     */
    public void addTargetColor(TargetColor targetColor) {
        this.targetColorList.add(targetColor);
    }

    /**
     * returns the value of the actual color
     * @param num - int
     * @return color (int)
     */
    public int getColor(int num) {
        if (this.colorList == null){
            createColors();
        }
        return colorList[num];
    }

    //PRIVATE METHODS
    /**
     * Create the colorList variable and setting up all the colors
     */
    private void createColors() {
        this.colorList = new int[256];
        for (int i = 0; i < (targetColorList.size() - 1); i++) {
            //passes to interpolateColors the actual target color and the next one to come
            interpolateColors(targetColorList.get(i), targetColorList.get(i+1));
        } 
    }
    
    /**
     * Create each temperature for each step of the target color, and so, setting up the colorList.
     * @param targetFrom - TargetColor
     * @param targetEnd - TargetColor
     */
    private void interpolateColors(TargetColor targetFrom, TargetColor targetEnd) {
        //Gets all the primari colors of each target color's color
        int[] fromColor = new int[]{
            targetFrom.getColor().getRed(), 
            targetFrom.getColor().getGreen(), 
            targetFrom.getColor().getBlue(), 
            targetFrom.getColor().getAlpha()
        };
        int[] endColor = new int[]{
            targetEnd.getColor().getRed(), 
            targetEnd.getColor().getGreen(), 
            targetEnd.getColor().getBlue(), 
            targetEnd.getColor().getAlpha()
        };
        
        //Set the amount of steps needed for each color to achive the target
        double steps = targetFrom.getTemperature() - targetEnd.getTemperature(); 
        double R = (endColor[0] - fromColor[0]) / steps;
        double G = (endColor[1] - fromColor[1]) / steps;
        double B = (endColor[2] - fromColor[2]) / steps;
        double A = (endColor[3] - fromColor[3]) / steps;
        int j = 0;
        
        //Loop the amount of steps
        for (int i = targetFrom.getTemperature(); i > targetEnd.getTemperature(); i--) {
            //Add the new color to the list
            this.colorList[i] = new Color(
                (int)(fromColor[0] + (R*j)),
                (int)(fromColor[1] + (G*j)), 
                (int)(fromColor[2] + (B*j)), 
                (int)(fromColor[3] + (A*j))
            ).getRGB();
            j++;
        }
    }
}
