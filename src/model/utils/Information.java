/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.utils;

import java.util.ArrayList;

/**
 *
 * @author maxime
 */
public class Information {
    public ArrayList<Position> pos;
    public Positionable source;

    public ArrayList<Position> getPos() {
        return pos;
    }

    public void setPos(ArrayList<Position> pos) {
        this.pos = pos;
    }

    public Positionable getSource() {
        return source;
    }

    public void setSource(Positionable source) {
        this.source = source;
    }
    
    
}
