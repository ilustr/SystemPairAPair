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
                }else{
                     this.active = true;
                     Environment.getInstance().refreshAgent(this);
                }
            }
        }
        if ( Environment.isNextTo(this, posBase)) 
        {
            this.doReportToBase();
        }
        
        doWalk();
    }

    @Override
    public void doWalk() {
        
        if (goToBase && !Environment.isNextTo(this, posBase) && !this.isOnBase()) {
            this.moveTo(posBase);
        } else if (siteToDig != null) {
            
            if(!isOnSite)
                this.moveTo(siteToDig);
            
            if( Environment.isNextTo(this, siteToDig))
                this.isOnSite = true; 
            else
                this.isOnSite = false;
        }
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }

    @Override
    public void doReportToBase() {
        
        if(siteToDig != null)
        {
            if(hasFinished)
            {
                Environment.getInstance().getBase().addExtracted(siteToDig);
                siteToDig = null;
            }
            else
            {
                goToBase = false;
            }
        }
        
        if(siteToDig == null)
        {
            Environment.getInstance().goOnBase(this);

            this.siteToDig = Environment.getInstance().getBase().getDiscovered();
            //System.out.println("site to dig "+ siteToDig);
            if (siteToDig != null) {
                doLeaveBase();
                goToBase = false;
                hasFinished = false;
            }
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

    @Override
    public int reload() {
        int diff = PA_MAX - this.actionPoints;
        this.doReload();
        if(!this.hasFinished){
            this.goToBase = false;
        }
        return diff;
    }
    
}
