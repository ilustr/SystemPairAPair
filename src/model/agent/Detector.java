/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.ImageIcon;
import model.environment.Environment;
import model.utils.Position;

/**
 *
 * @author hugo
 */
public class Detector extends Agent{
    public static final int PA_MAX = 50;
    public static final int DETECTOR_VISIBILITY = 2;
    
    private ArrayList<Position> visitedYet;
    private ArrayList<Position> hasOre;
    
    private Position nextGoal;
    
    
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
        isThereAnyOreOutThere();

        if(this.goToBase && Environment.isNextTo(this, posBase))
        {
            this.doReportToBase();
        }
    }
    
    public void isThereAnyOreOutThere() {
        for (int i = -DETECTOR_VISIBILITY; i < DETECTOR_VISIBILITY; i++) {
            for (int j = -DETECTOR_VISIBILITY; j < DETECTOR_VISIBILITY; j++) {
                Position newPos = new Position(this.pos.x + i, this.pos.y + j);
                if (!visitedYet.contains(newPos)) {
                    if (Environment.getInstance().isThereOre(newPos)){
                        this.active = true;
                        Environment.getInstance().refreshAgent(this);
                        this.hasOre.add(newPos);
                    }
                    this.visitedYet.add(newPos);
                }
            }
        }
    }

    @Override
    public void doWalk() {
        if (goToBase) {
            moveTo(posBase);
        } else {
            if (nextGoal == null || Environment.isNextTo(this, nextGoal)) {
                // walk randomly dependings on what cases have been visited yet
                nextGoal = Environment.getInstance().getRandomPosition();
            } 
            moveTo(nextGoal);            
        }
    }

    @Override
    public void doReload() {
        super.actionPoints = PA_MAX;
    }

    @Override
    public void doReportToBase() {
        System.out.println("report1");
        this.active = true;
        for (Position position : this.hasOre) {
            System.out.println("report");
            Environment.getInstance().getBase().addDiscovered(position);
        }
        this.hasOre.clear();
        
        Environment.getInstance().refreshAgent(this);
        doReload();
        nextGoal = null;
        this.goToBase = false;
    }

    @Override
    public String getDisplayString() {
        return "Detector";
    }

    @Override
    public ImageIcon getDisplayImage() {
        if(isActive()){
            this.active = false;
            return new ImageIcon(getClass().getResource("/images/detectorActive.png"));
        }
        else
            return new ImageIcon(getClass().getResource("/images/detector.png"));
    }
}
