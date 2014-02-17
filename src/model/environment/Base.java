/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.environment;

import java.util.ArrayList;
import model.agent.Agent;
import model.agent.Detector;
import model.agent.Digger;
import model.utils.Positionable;
import model.utils.Position;

/**
 *
 * @author maxime
 */
public class Base implements Positionable {

    private Position position;
    private ArrayList<Agent> agents;
    private ArrayList<Position> discovered;
    private ArrayList<Position> extracted;
    private ArrayList<Position> transported;
    private ArrayList<Position> toEnergize;
    private ArrayList<Position> siteRecorded;
    

    public Base(int detectorsNumber, int extractorsNumber, int energizersNumber, int transportorNumber) {
        init(detectorsNumber, extractorsNumber, energizersNumber, transportorNumber);
    }

    private void init(int detectorsNumber, int diggerNumber, int energizersNumber, int transportorNumber) {
        //TODO initialize 
        
        agents = new ArrayList<>();
        discovered = new ArrayList<>();
        extracted = new ArrayList<>();
        transported = new ArrayList<>();
        toEnergize = new ArrayList<>();
        siteRecorded = new ArrayList<>();
        
        for (int i = 0; i < diggerNumber; ++i) {
            agents.add(new Digger(this.getPosition()));

        }

        for (int i = 0; i < detectorsNumber; ++i) {
            agents.add(new Detector(this.getPosition()));
        }

        for (int i = 0; i < energizersNumber; ++i) {
            // agents.add(new Energizer(this.getPosition()));
        }

        for (int i = 0; i < transportorNumber; ++i) {

        }

    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public String getDisplayString() {
        return "Base";
    }

    @Override
    public String getDisplayImage() {
        return null;
    }

    public Position getDiscovered() {
        if(discovered.size() > 0)
        {
            Position pos = discovered.get(0);
            return pos;
        }
        else
            return null;
    }

    public boolean addDiscovered(Position e) {
        if (addRecord(e)) {
            addToEnergize(e);
            return discovered.add(e);
        } else
            return false;
    }
    
    public Position getExtracted() {
        if(extracted.size() > 0)
        {
            Position pos = extracted.get(0);
            return pos;
        }
        else
            return null;
    }

    public boolean addExtracted(Position e) {
        return extracted.add(e);
    }
    public Position getToEnergize() {
        if(toEnergize.size() > 0)
        {
            Position pos = toEnergize.get(0);
            return pos;
        }
        else
            return null;
    }

    public boolean addToEnergize(Position e) {
        return toEnergize.add(e);
    }
    
    public boolean addTransported(Position e) {
        return transported.add(e);
    }
    
    public boolean addRecord(Position e) {
        if (isSiteKnown(e))
            return siteRecorded.add(e);
        else
            return false;
    }
    
    public boolean isSiteKnown(Position e) {
        return siteRecorded.contains(e);
    }
}
