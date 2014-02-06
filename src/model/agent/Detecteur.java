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
public class Detecteur extends Agent{
    public static final int PA_MAX = 30;
    
    private ArrayList<Position> visitedYet;
    private ArrayList<Position> hasOre;

    public Detecteur(Position posBase) {
        super(posBase);
    }

    @Override
    public void onReceive(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doWork() {
        // WALK 
        
        // IF (ore around)
            // add to list
    }
    
}
