/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.agent;

import java.util.ArrayList;
import java.util.HashMap;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doWork() {
        // WALK 
        doWalk();
        
        // IF (ore around)
            // add to list
            // mark it 
            // return to base
        
        // Check if energie is sufficient to return to base
            // if not, gotobase = true;

    }

    @Override
    public void doWalk() {
        if (goToBase) {
            // return to base
        } else {
            // walk randomly dependings on what cases have been visited yet
        }
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }
    
}
