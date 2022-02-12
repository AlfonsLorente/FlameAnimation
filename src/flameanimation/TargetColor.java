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
    /**
     * sets the temperature and color variables
     *
     * @param temperature - int
     * @param color - Color
     */
    public TargetColor(int temperature, Color color) {
        this.temperature = temperature;
        this.color = color;
    }

    //GETTERS AND SETTERS
    /**
     * gets the temperature
     *
     * @return temperature (int)
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * sets the temperature
     *
     * @param temperature - int
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    /**
     * gets the color
     *
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * sets the color
     *
     * @param color - Color
     */
    public void setColor(Color color) {
        this.color = color;
    }

}
