/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.agent;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.environment.Environment;
import model.utils.Position;
import model.utils.Positionable;

/**
 *
 * @author ilustr
 */
public abstract class Agent implements Runnable, Positionable {

    public static final int TIME_SLEEP_MS = 500;

    protected int actionPoints;
    protected Position pos;
    protected Position posBase;
    protected boolean active;
    protected boolean goToBase;

    private boolean kill = false;

    public Agent(Position posBase) {
        this.posBase = posBase;
        this.pos = posBase;
        goToBase = true;
        this.active=  false;
    }

    @Override
    public Position getPosition() {
        return pos;
    }

    @Override
    public void setPosition(Position position) {
        this.pos = position;
    }

    public abstract void onReceive(String msg);

    public abstract void doWork();

    public abstract void doWalk();

    public abstract void doReload();
    
    public abstract int reload();
    
    public abstract void doReportToBase();
    
    public void broadcast(String msg) {

    }

    public void end() {
        this.kill = true;
    }

    @Override
    public final void run() {
        while (!kill) {
            try {
                doWork();
                this.actionPoints--;
                this.doEnergyCheck();
                Thread.sleep(TIME_SLEEP_MS);
            } catch (InterruptedException ex) {
                Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void doEnergyCheck() {
        // Check if energie is sufficient to return to base
        if(this.actionPoints < getDistBase() * 1.5)
        {
            // if not, gotobase = true;
            this.goToBase = true;
        }
    }

    public int getDistBase() {
        return Math.abs(this.posBase.x-this.pos.x) + Math.abs(this.posBase.y-this.pos.y);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    private boolean moveAlgorithm(Position posTarget)
    {
        int x=0, y=0;
        
        // Select x
        if (posTarget.x > pos.x)
            x = 1;
        else if (posTarget.x < pos.x)
            x = - 1;
        
        // Select y
        if (posTarget.y > pos.y)
            y = 1;
        else if (posTarget.y < pos.y)
            y = - 1; 

        Position newPos = new Position(pos.x +x, pos.y + y); 
        if (Environment.getInstance().moveTo(this, newPos)) {
            this.pos = newPos;
            return true;
        } else {
            if (x == 0)
            {
                for (int i = -1; i <= 1; i++) {
                    newPos = new Position(pos.x +i, pos.y + y); 
                    if (Environment.getInstance().moveTo(this, newPos)) {
                        this.pos = newPos;
                        return true;
                    }
                }
            }   
            else if (y == 0)
            {
                for (int i = -1; i <= 1; i++) {
                    newPos = new Position(pos.x +x, pos.y +i); 
                    if (Environment.getInstance().moveTo(this, newPos)) {
                        this.pos = newPos;
                        return true;
                    }
                }
            }
            else 
            {
                newPos = new Position(pos.x, pos.y +y); 
                if (Environment.getInstance().moveTo(this, newPos)) {
                    this.pos = newPos;
                    return true;
                } else {
                    newPos = new Position(pos.x + x, pos.y ); 
                    if (Environment.getInstance().moveTo(this, newPos)) {
                        this.pos = newPos;
                        return true;
                    } 
                }
            }
            
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    newPos = new Position(pos.x + i, pos.y +j ); 
                    if (Environment.getInstance().moveTo(this, newPos)) {
                        this.pos = newPos;
                        return true;
                    }
                }
            }
            
            return false;
        }
    }
    
    public boolean moveTo(Position posTarget){
        if (actionPoints <= 0)
            return false;
        if (moveAlgorithm(posTarget))
        {
            return true;
        }
        return false;
    }
}
