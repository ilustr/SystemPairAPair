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
import model.utils.Positionable;

/**
 *
 * @author hugo
 */
public class Energizer extends Agent {

    public static final int PA_MAX = 500;
    private static int NB_MAX_SITES = 1;
    private static int BROADCAST_RANGE = 1;

    private ArrayList<Position> sitesToVisit;
    private int currentTarget;

    public Energizer(Position posBase) {
        super(posBase);

        doReload();

        currentTarget = 0;
        sitesToVisit = new ArrayList<>();
    }

    @Override
    public void onReceive(String msg) {
        // msg == "NEED ENERGY"
        // fill agent's tank

    }

    @Override
    public void doWork() {
        
        super.doEnergyCheck();
        // WALK 
        doWalk();
        
        if (sitesToVisit.size() > 0) {
            
            //don't give too muck fuel or you gonna have a bad time
            if(this.actionPoints > getDistBase() * 2)
            {
                ArrayList<Agent> agents = Environment.getInstance().getAgentsInRange(this.pos, BROADCAST_RANGE);
                
                for (Agent agent : agents) {
                    if(agent instanceof Digger)
                    {
                        this.actionPoints -= agent.reload();
                    }
                }
            }
        }
        
        if (Environment.isNextTo(this, posBase))
        {
                this.doReportToBase();
        }

    }

    private void loadSitesFromBase() {
        // Ask the base for a site to transport
        for (int i = 0; i < NB_MAX_SITES; i++) {
            Position site = Environment.getInstance().getBase().getToEnergize();
            if(site != null)
                this.sitesToVisit.add(site);
        }
    }
    private void reLoadSitesInBase() {
        // Ask the base for a site to transport
        for (Position position : sitesToVisit) {
            Environment.getInstance().getBase().addToEnergize(position);
        }
        this.sitesToVisit.clear();
    }

    @Override
    public void doWalk() {
        if (goToBase) {
            this.moveTo(posBase);// return to base
        } else {
            if(this.sitesToVisit.size() > 0)
            {
                this.moveTo(this.sitesToVisit.get(0));
                if (Environment.isNextTo(this, this.sitesToVisit.get(0)))
                {
                    this.goToBase = true;
                }
            }
        }
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }

    @Override
    public void doReportToBase() {
        doReload();
        reLoadSitesInBase();
        loadSitesFromBase();
        this.goToBase = false;
    }

    @Override
    public String getDisplayString() {
        return "Energizer";
    }

    @Override
    public ImageIcon getDisplayImage() {
         if(isActive()){
            this.active = false;
            return new ImageIcon(getClass().getResource("/images/energizer.png"));
         }else{
            return new ImageIcon(getClass().getResource("/images/energizerActive.png")); 
         }
    }

    @Override
    public int reload() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
