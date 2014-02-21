/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.agent;

import java.util.ArrayList;
import javax.swing.ImageIcon;
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
        if (siteToDig != null) 
        {
            if (isOnSite) {
                // Dig until everythings has been cleared up
                if(!Environment.getInstance().dig(siteToDig))
                {
                // once it's finished, mark site has finished
                    this.isOnSite = false;
                    this.goToBase = true;
                    this.hasFinished = true;
                }
            }
            else if (this.goToBase && Environment.isNextTo(this, posBase))
            {
                this.doReportToBase();
            }
        }
        else if (Environment.isNextTo(this, posBase)) 
        {
            System.out.println("lol");
            this.siteToDig = Environment.getInstance().getBase().getDiscovered();
            System.out.println("site to dig "+ siteToDig);
            if (siteToDig != null) {
                goToBase = false;
            }
        }
        
        doWalk();
    }

    @Override
    public void doWalk() {
        if (goToBase && !Environment.isNextTo(this, posBase)) {
            this.moveTo(posBase);
        } else if (!isOnSite && siteToDig != null) {
            this.moveTo(siteToDig);
            
            if( Environment.isNextTo(this, siteToDig))
                this.isOnSite = true;  
        }
              
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }

    @Override
    public void doReportToBase() {
        if (hasFinished) {
            Environment.getInstance().getBase().addExtracted(siteToDig);
            siteToDig = null;
            hasFinished = false;
        }
        doReload();
    }
    
    @Override
    public String getDisplayString() {
        return "Digger";
    }

   @Override
    public ImageIcon getDisplayImage() {
        if(isActive()){
            this.active = false;
            return new ImageIcon(getClass().getResource("/images/diggerActive.png"));
        }
        else
            return new ImageIcon(getClass().getResource("/images/digger.png"));
    }
    
}
