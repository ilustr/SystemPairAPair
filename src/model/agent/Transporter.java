/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.agent;

import javax.swing.ImageIcon;
import model.environment.Environment;
import model.utils.Position;

/**
 *
 * @author hugo
 */
public class Transporter extends Agent {

    public static final int PA_MAX = 50;
    public static final int CAPACITY = 10;
    /* how much ore an action point load */
    public static final int LOAD_QUANTITY = 5;

    private Position siteToTransport;
    private boolean isOnSite;
    private boolean hasFinished;
    private int oreTransported;

    public Transporter(Position posBase) {
        super(posBase);

        doReload();

        siteToTransport = null;
        isOnSite = false;
        hasFinished = false;
        oreTransported = 0;
        goToBase = true;
    }

    @Override
    public void onReceive(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doWork() {
        if (siteToTransport != null) {
            if (isOnSite){
                if (Environment.getInstance().noMoreMisterNiceOre(siteToTransport))
                {
                    goToBase = true;
                    isOnSite = false;
                    hasFinished = true;
                }
                    
                if (oreTransported + LOAD_QUANTITY <= CAPACITY) {
                    oreTransported += Environment.getInstance().loadRsc(siteToTransport);
                    System.out.println("load");
                } else {
                    goToBase = true;
                    isOnSite = false;
                }
                
            } else if (this.goToBase && Environment.isNextTo(this, posBase))
            {
                this.doReportToBase();
            } else {
                // WALK 
                doWalk();
            }
        } else if (Environment.isNextTo(this, posBase)) 
        {
            this.siteToTransport = Environment.getInstance().getBase().getExtracted();
            System.out.println("site to transport "+ siteToTransport);
            if (siteToTransport != null) {
                goToBase = false;
            }
        }

    }

    @Override
    public void doWalk() {
        if (goToBase && !Environment.isNextTo(this, posBase)) {
            this.moveTo(posBase);
        }  else if (!isOnSite && siteToTransport != null) {
            this.moveTo(siteToTransport);
            
            if( Environment.isNextTo(this, siteToTransport))
                this.isOnSite = true;  
        }
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }

    @Override
    public void doReportToBase() {
        // drop ressources
        Environment.getInstance().getBase().dropRessources(oreTransported);
        oreTransported = 0;
        
        if (hasFinished) {
            siteToTransport = null;
            hasFinished = false;
        }
    }

    @Override
    public String getDisplayString() {
        return "Transportor";
    }

    @Override
    public ImageIcon getDisplayImage() {
        return new ImageIcon(getClass().getResource("/images/transporter.png"));
    }
}
