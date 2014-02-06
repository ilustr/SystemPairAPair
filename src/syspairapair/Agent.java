/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package syspairapair;

import java.util.Observable;

/**
 *
 * @author ilustr
 */
public class Agent extends Observable implements Runnable{
    
    protected int actionPoints;
    protected Position pos;
    //protected Environment env;
    protected Position posBase; 

    public void init(Position posBase)
    {
        this.posBase = posBase;
        this.pos = posBase;
    }
    
    public abstract void onReceive(String msg);
    
    public void broadcast(String msg) {
        
    }
    
    public void end()
    {
        
    }
    
    @Override
    public void run() {
        
    }
    
}
