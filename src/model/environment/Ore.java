/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.environment;

import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
    private int stack;
    
    public Ore(int quantity){
        this.quantity = quantity;
    }
    
    public boolean dig(){
        if(this.getQuantity()-1 > 0){
            this.setQuantity(this.getQuantity()-1);
            this.setStack(this.getStack()+1);
            return true;
        }else{
            Environment.getInstance().refreshAgent(this);
            this.diggable = false;
            return false;
        }       
    }
    
    public boolean getInStack(){
        if(this.getStack()-1 > 0){
            this.setStack(this.getStack()-1);
            return true;
        }else{
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

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
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
    public ImageIcon getDisplayImage() {
        if(isDiggable())
            return new ImageIcon(getClass().getResource("/images/ore.png"));
        else{
            return new ImageIcon(getClass().getResource("/images/ore_finish.png"));
        }
    }
    
    
}
