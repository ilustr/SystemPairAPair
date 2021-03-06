/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.environment;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import model.agent.Agent;
import model.agent.Detector;
import model.agent.Digger;
import model.agent.Energizer;
import model.agent.Transporter;
import model.utils.Positionable;
import model.utils.Position;

/**
 *
 * @author maxime
 */
public class Base implements Positionable {

    private Position position;
    private ArrayList<Position> discovered;
    private ArrayList<Position> extracted;
    private ArrayList<Position> transported;
    private ArrayList<Position> toEnergize;
    private ArrayList<Position> toEnergizeTerminated;
    private ArrayList<Position> siteRecorded;
    private ArrayList<Agent> agentsInside;
    private ArrayList<Agent> agents;
    private int ore;
    

    public Base() { }

    public void init(Position pos) {
        agents = new ArrayList<>();
        discovered = new ArrayList<>();
        extracted = new ArrayList<>();
        transported = new ArrayList<>();
        toEnergize = new ArrayList<>();
        toEnergizeTerminated = new ArrayList<>();
        siteRecorded = new ArrayList<>();
        agentsInside = new ArrayList<>();
        ore = 0;
        
        this.setPosition(pos);

        for (int i = 0; i < Environment.DIGGERS_NUMBER; ++i) {
            Digger digger = new Digger(this.getPosition());
            agents.add(digger);
        }

        for (int i = 0; i < Environment.DETECTORS_NUMBER; ++i) {
            agents.add(new Detector(this.getPosition()));
        }

        for (int i = 0; i < Environment.ENERGIZERS_NUMBER; ++i) {
            Energizer energizer = new Energizer(this.getPosition());
            agents.add(energizer);
        }

        for (int i = 0; i < Environment.TRANSPORTERS_NUMBER; ++i) {
            Transporter transporter = new Transporter(this.getPosition());
            agents.add(transporter);
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
    public ImageIcon getDisplayImage() {
       return new ImageIcon(getClass().getResource("/images/base5.png"));
    }

    public synchronized Position getDiscovered() {
        if(discovered.size() > 0)
        {
            Position pos = discovered.get(0);
//            System.out.println("lol "+pos);
            discovered.remove(0);
            return pos;
        }
        else
            return null;
    }

    public synchronized boolean addDiscovered(Position e) {
        if (addRecord(e)) {
            addToEnergize(e);
//            System.out.println("Add discovered site : "+e.x+ "-"+e.y);
            return discovered.add(e);
        } else
            return false;
    }
    
    public synchronized Position getExtracted() {
        if(extracted.size() > 0)
        {
            Position pos = extracted.get(0);
            extracted.remove(0);
            return pos;
        }
        else
            return null;
    }

    public synchronized boolean addExtracted(Position e) {
        return extracted.add(e);
    }
    
    public synchronized Position getToEnergize() {
        if(toEnergize.size() > 0)
        {
            Position pos = toEnergize.get(0);
            toEnergize.remove(0);
            return pos;
        }
        else
            return null;
    }
    
    public synchronized boolean addToEnergizeTerminated(Position e) {
        return this.toEnergizeTerminated.add(e);
    }
        

    public synchronized boolean addToEnergize(Position e) {
        if(!this.toEnergizeTerminated.contains(e))
            return toEnergize.add(e);
        else
            return false;
    }
    
    public synchronized boolean addTransported(Position e) {
        return transported.add(e);
    }
    
    public synchronized boolean addRecord(Position e) {
        if (!isSiteKnown(e))
            return siteRecorded.add(e);
        else
            return false;
    }
    
    public boolean isSiteKnown(Position e) {
        return siteRecorded.contains(e);
    }
    
    public void dropRessources(int qte) {
        ore += qte;
    }

    public int getOre() {
        return ore;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public ArrayList<Agent> getAgentsInside() {
        return agentsInside;
    }
    
    
}
