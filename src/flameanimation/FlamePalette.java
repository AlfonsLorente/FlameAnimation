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

    private ArrayList<TargetColor> targetColorList = new ArrayList<TargetColor>();
    private int[] colorList;

    public void addTargetColor(TargetColor targetColor) {
        this.targetColorList.add(targetColor);
    }

    public int getColor(int num) {
        if (this.colorList == null){
            createColors();
        }
        return colorList[num];
    }

    private void createColors() {
        this.colorList = new int[256];
        for (int i = 0 ; i < (targetColorList.size() - 1) ; i++) {
            interpolateColors(targetColorList.get(i), targetColorList.get(i+1));
        } 
    }

    private void interpolateColors(TargetColor targetFrom, TargetColor targetEnd) {

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
        
        double steps = targetFrom.getTemperature() - targetEnd.getTemperature(); 
        double A = (endColor[3] - fromColor[3]) / steps;
        double R = (endColor[0] - fromColor[0]) / steps;
        double G = (endColor[1] - fromColor[1]) / steps;
        double B = (endColor[2] - fromColor[2]) / steps;
        int j = 0;
        for (int i = targetFrom.getTemperature() ; i > targetEnd.getTemperature() ; i--) {
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
