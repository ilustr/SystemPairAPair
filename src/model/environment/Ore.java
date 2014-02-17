/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.environment;

import java.util.ArrayList;
import model.agent.Agent;
import model.agent.Detector;
import model.agent.Digger;
import model.utils.Positionable;
import model.utils.Position;

/**
 *
 * @author maxime
 */
public class Ore implements Positionable {

    private Position position;
    private int quantity;
    private boolean diggable=true;
    
    public Ore(int quantity){
        this.quantity = quantity;
    }
    
    public boolean dig(){
        if(this.getQuantity()-1 > 0){
            this.setQuantity(this.getQuantity()-1);
            return true;
        }else{
            this.diggable = false;
            return false;
        }       
    }

    public boolean isDiggable() {
        return diggable;
    }

    public void setDiggable(boolean diggable) {
        this.diggable = diggable;
    }
    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public String getDisplayString() {
        return "Ore";
    }

    @Override
    public String getDisplayImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
