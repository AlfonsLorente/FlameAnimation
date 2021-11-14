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
    private int temperature;
    private Color color;

    public TargetColor(int temperature, Color color){
        this.temperature = temperature;
        this.color = color;
    }

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
    
  