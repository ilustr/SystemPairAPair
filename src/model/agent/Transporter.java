/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.agent;

import model.utils.Position;

/**
 *
 * @author hugo
 */
public class Transporter extends Agent {
    public static final int PA_MAX = 50;
    
    private Position siteToTransport;

    public Transporter(Position posBase) {
        super(posBase);
        
        doReload();
        
        siteToTransport = null;
    }

    @Override
    public void onReceive(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doWork() {
        if (siteToTransport != null) {
            // WALK 
            doWalk();
            
            // if site is reach
            // load ressources
            // goToBase = true;
            
            // if gotobase && base is reach
                // goReportToBase
        } else {
            // Ask the base for a site to transport
        }

    }

    @Override
    public void doWalk() {
        if (goToBase) {
            // return to base
        } else {
            // go to site to dig
        }
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }

    @Override
    public void doReportToBase() {
        // drop ressources
        // gotobase false
        // sitetotransport false
    }
    
}