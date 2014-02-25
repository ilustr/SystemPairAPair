/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.agent;

import javax.swing.ImageIcon;
import static model.agent.Digger.PA_MAX;
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
            if (isOnSite) {
                workOnSite();
            } else if (this.goToBase && Environment.isNextTo(this, posBase)) {
//                System.out.println("has reach the base");
                this.doReportToBase();
            }
        } else if (Environment.isNextTo(this, posBase)) {
            waitForASite();
        }

        // WALK 
        doWalk();
    }

    public void workOnSite() {
//        System.out.println("Work on site");
       this.active = true;
       Environment.getInstance().refreshAgent(this);
        if (Environment.getInstance().noMoreMisterNiceOre(siteToTransport)) {
            goToBase = true;
            isOnSite = false;
            hasFinished = true;
            return;
        } 
       
        oreTransported += Environment.getInstance().loadRsc(siteToTransport, CAPACITY - oreTransported);
     
        if (CAPACITY - oreTransported <= 0) {
//            System.out.println("transporter full "+ oreTransported);
            goToBase = true;
            isOnSite = false;
        }
    }

    public void waitForASite() {
        doReload();
        Environment.getInstance().goOnBase(this);
        this.siteToTransport = Environment.getInstance().getBase().getExtracted();

        if (siteToTransport != null) {
            doLeaveBase();
//            System.out.println("site to transport " + siteToTransport);
            goToBase = false;
        }
    }

    @Override
    public void doWalk() {
        if (goToBase && !Environment.isNextTo(this, posBase)) {
            this.moveTo(posBase);
        } else if (!isOnSite && siteToTransport != null) {
            this.moveTo(siteToTransport);
//            System.out.println("hey ohh + " + this.actionPoints);
            if (Environment.isNextTo(this, siteToTransport)) {
                this.isOnSite = true;
            }
        }
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }

    @Override
    public synchronized void doReportToBase() {
//        System.out.println("reporting to base");
        doReload();
        
        // drop ressources
        Environment.getInstance().getBase().dropRessources(oreTransported);
        oreTransported = 0;
        goToBase = false;

        if (hasFinished) {
            System.out.println("finish");
            hasFinished = false;
            Environment.getInstance().getBase().addToEnergizeTerminated(siteToTransport);
            Environment.getInstance().removeFromMap(siteToTransport);
            siteToTransport = null;
        }
    }

    @Override
    public String getDisplayString() {
        return "Transportor";
    }

    @Override
    public ImageIcon getDisplayImage() {
        if (isActive()) {
            this.active = false;
            return new ImageIcon(getClass().getResource("/images/transporterActive.png"));
        } else {
            return new ImageIcon(getClass().getResource("/images/transporter.png"));
        }
    }

    @Override
    public int reload() {
        int diff = PA_MAX - this.actionPoints;
        this.doReload();
        return diff;
    }
}
