/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.agent;

import java.util.ArrayList;
import model.environment.Environment;
import model.utils.Position;

/**
 *
 * @author hugo
 */
public class Digger extends Agent {
    public static final int PA_MAX = 50;
    
    private Position siteToDig;
    private boolean isOnSite;
    private boolean hasFinished;

    public Digger(Position posBase) {
        super(posBase);
        
        doReload();
        
        siteToDig = null;
        isOnSite = false;
        hasFinished = true;
    }

    @Override
    public void onReceive(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doWork() {
        if (siteToDig != null) {
            
            if (!isOnSite)
                // WALK 
                doWalk();
            else {
                // Dig until everythings has been cleared up
                if(/*Environment.getInstance().dig(pos)*/false)
                {
                // once it's finished, mark site has finished
                    this.isOnSite = false;
                    this.goToBase = true;
                    this.hasFinished = true;
                }
            } 
            if (this.goToBase && this.pos.equals(posBase))
            {
                this.doReportToBase();
            }
        } else {
            // Ask the base for a site to dig
        }

    }

    @Override
    public void doWalk() {
        if (goToBase) {
            this.moveTo(posBase);
        } else {
            this.moveTo(siteToDig);
        }
        
        if(this.pos.equals(siteToDig))
                this.isOnSite = true;
        
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }

    @Override
    public void doReportToBase() {
        goToBase = false;
        if (hasFinished) {
//            Environment.getInstance().getBase()
            siteToDig = null;
        }
        doReload();
    }
    
    @Override
    public String getDisplayString() {
        return "Digger";
    }

    @Override
    public String getDisplayImage() {
        return null;
    }
    
}
