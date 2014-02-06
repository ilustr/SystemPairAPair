/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.agent;

import java.util.Observable;
import model.environment.Environment;
import model.utils.Position;

/**
 *
 * @author ilustr
 */
public abstract class Agent extends Observable implements Runnable{
    
    protected int actionPoints;
    protected Position pos;
    protected Position posBase; 
    protected Environment env;
    protected boolean goToBase;
    
    private boolean kill = false;

    public Agent(Position posBase)
    {
        this.posBase = posBase;
        this.pos = posBase;
        goToBase = false;
    }
    
    public abstract void onReceive(String msg);
    
    public abstract void doWork();
    
    public abstract void doWalk();
    
    public abstract void doReload();
    
    public abstract void doReportToBase();
    
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
    
    public void doEnergyCheck() {
        // Check if energie is sufficient to return to base
            // if not, gotobase = true;
    }
}
