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
import model.utils.Positionable;
import model.utils.Position;

/**
 *
 * @author maxime
 */
public class Base implements Positionable {

    private Position position;
    private ArrayList<Agent> agents;

    public Base(int detectorsNumber, int extractorsNumber, int energizersNumber, int transportorNumber) {
        agents = new ArrayList<>();
        init(detectorsNumber, extractorsNumber, energizersNumber, transportorNumber);
    }

    private void init(int detectorsNumber, int diggerNumber, int energizersNumber, int transportorNumber) {
        //TODO initialize 
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
    public ImageIcon getDisplayImage() {
       return new ImageIcon(getClass().getResource("/images/base.png"));
    }
}
