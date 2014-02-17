/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import model.environment.Environment;
import model.utils.Position;

/**
 *
 * @author hugo
 */
public class Detector extends Agent{
    public static final int PA_MAX = 150;
    
    private ArrayList<Position> visitedYet;
    private ArrayList<Position> hasOre;
    
    
    public Detector(Position posBase) {
        super(posBase);
        goToBase = false;
        
        doReload();
        
        visitedYet = new ArrayList<>();
        hasOre = new ArrayList<>();
    }


    @Override
    public void onReceive(String msg) {
        throw new UnsupportedOperationException("Detector.Onreceive : Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doWork() {
        super.doEnergyCheck();
        
        // WALK 
        doWalk();
        
        // IF (ore around)
            // add to list
            // mark it 
            // return to base

        // If gotobase && base is reach
            // doReportToBase();
    }

    @Override
    public void doWalk() {
        if (goToBase) {
            moveTo(posBase);
        } else {
            // walk randomly dependings on what cases have been visited yet
            Random rand = new Random();
            moveTo(new Position(rand.nextInt(Environment.WIDTH), rand.nextInt(Environment.HEIGHT)));
        }
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }

    @Override
    public void doReportToBase() {
        // Give site discovered
        doReload();
    }

    @Override
    public String getDisplayString() {
        return "Detector";
    }

    @Override
    public String getDisplayImage() {
        return null;
    }
}
