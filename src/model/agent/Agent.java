/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.agent;

import java.util.Observable;
import model.environment.Environment;
import model.utils.Position;
import model.utils.Positionable;

/**
 *
 * @author ilustr
 */
public abstract class Agent implements Runnable,Positionable{
    
    protected int actionPoints;
    protected Position pos;
    protected Position posBase; 
    protected boolean goToBase;
    
    private boolean kill = false;

    public Agent(Position posBase)
    {
        this.posBase = posBase;
        this.pos = posBase;
        goToBase = false;
    }
    

    @Override
    public Position getPosition() {
        return pos;
    }

    @Override
    public void setPosition(Position position){
        this.pos = position;
    }
    
    
    
    public abstract void onReceive(String msg);
    
    public abstract void doWork();
    
    public abstract void doWalk();
    
    public abstract void doReload();
    
    public void broadcast(String msg) {
        
    }
    
    public void end()
    {
        this.kill = true;
    }
    
    @Override
    public final void run() {
        while (!kill)
        {
            doWork();
        }
    }    
}
