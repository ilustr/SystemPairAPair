/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.agent;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import model.utils.Position;

/**
 *
 * @author hugo
 */
public class Digger extends Agent {
    public static final int PA_MAX = 50;
    
    private Position siteToDig;
    private boolean isOnSite;

    public Digger(Position posBase) {
        super(posBase);
        
        doReload();
        
        siteToDig = null;
        isOnSite = false;
    }

    @Override
    public void onReceive(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doWork() {
        if (siteToDig != null) {
            
            //if (!isOnSite)
                // WALK 
                doWalk();
            //else 
                // Dig until everythings has been cleared up
                // once it's finished, mark site has finished
                // mark isonsite false
                // mark gotobase true
                
            // if gotobase && base is reach
                // goReportToBase

        } else {
            // Ask the base for a site to dig
        }

    }

    @Override
    public void doWalk() {
        if (goToBase) {
            // return to base
        } else {
            // go to site to dig
        }
        
        // check if site to dig is reach
        // fi true --> isOnSite = true;
        
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }

    @Override
    public void doReportToBase() {
        // drop ressources
        // gotobase false
        // sitetodig false
        doReload();
    }
    
    @Override
    public String getDisplayString() {
        return "Digger";
    }

   @Override
    public ImageIcon getDisplayImage() {
        return new ImageIcon(getClass().getResource("/images/digger.png"));
    }
    
}
