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
    protected boolean goToBase;

    private boolean kill = false;

    public Agent(Position posBase) {
        this.posBase = posBase;
        this.pos = posBase;
        goToBase = false;
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
                Thread.sleep(TIME_SLEEP_MS);
            } catch (InterruptedException ex) {
                Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void doEnergyCheck() {
        // Check if energie is sufficient to return to base
            // if not, gotobase = true;
    }
}
