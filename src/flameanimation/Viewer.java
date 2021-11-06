/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flameanimation;

import java.awt.Canvas;

/**
 *
 * @author alfon
 */
public class Viewer extends Canvas{
    //VARIABLES
    private int rate;
    private Flame flame;

    //GETTERS AND SETTERS
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Flame getFlame() {
        return flame;
    }

    public void setFlame(Flame flame) {
        this.flame = flame;
    }
    
    
    
}
