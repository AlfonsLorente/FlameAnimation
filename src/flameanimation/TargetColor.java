/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Color;

/**
 *
 * @author alfon
 */
public class TargetColor {
    //VARIABLES
    private Color color;
    private int temperature;

    //CONSTRUCTOR
    public TargetColor(int temperature, Color color){
        this.temperature = temperature;
        this.color = color;
    }

    //GETTERS AND SETTERS
    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    
}
    
  