/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.agent;

import java.util.Observable;
import model.utils.Position;
import model.utils.Positionable;

/**
 *
 * @author ilustr
 */
public abstract class Agent extends Observable implements Runnable,Positionable{
    
    protected int actionPoints;
    protected Position pos;
    //protected Environment env;
    protected Position posBase; 
    
    private boolean kill = false;

    public Agent(Position posBase)
    {
        this.posBase = posBase;
        this.pos = posBase;
    }
    
    public abstract void onReceive(String msg);
    
    public abstract void doWork();
    
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
