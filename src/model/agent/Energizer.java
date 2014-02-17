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
public class Energizer extends Agent {

    public static final int PA_MAX = 500;

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
        if (sitesToVisit.size() != 0) {
            super.doEnergyCheck();

            // WALK 
            doWalk();

            // broadcast ( "need-energy?" )
        } else {
            // Ask the base for a site to transport
        }

    }

    @Override
    public void doWalk() {
        if (goToBase) {
            // return to base
        } else {
            // go to site to visit currentTarget

            // if reach site(currentTarget)
            // currentTarget++
            // if currentTarget > sitetovisite.size()
            // gotobase = true;
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

    @Override
    public String getDisplayString() {
        return "Energizer";
    }

    @Override
    public ImageIcon getDisplayImage() {
        return new ImageIcon(getClass().getResource("/images/energizer.png"));
    }
}
