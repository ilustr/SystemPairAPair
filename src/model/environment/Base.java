/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.environment;

import java.util.ArrayList;
import model.agent.Agent;
import model.utils.Positionable;
import model.utils.Position;

/**
 *
 * @author maxime
 */
public class Base implements Positionable {

    private Position position;
    private ArrayList<Agent> agents;

    public Base(Position position) {
        this.position = position;
    }

    public Base(int detectorsNumber, int extractorsNumber, int energizersNumber, int transportorNumber) {
        agents = new ArrayList<>();

    }

    private void init(int detectorsNumber, int extractorsNumber, int energizersNumber, int transportorNumber) {
        //TODO initialize 
        for (int i = 0; i < detectorsNumber; ++i) {

        }

        for (int i = 0; i < detectorsNumber; ++i) {

        }

        for (int i = 0; i < detectorsNumber; ++i) {

        }

        for (int i = 0; i < detectorsNumber; ++i) {

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
}
