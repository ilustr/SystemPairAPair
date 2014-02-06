/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.environment;

import model.utils.Positionable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import model.agent.Agent;
import model.utils.Information;
import model.utils.Position;

/**
 *
 * @author maxime
 */
public class Environment {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    public static final int DETECTORS_NUMBER = 20;
    public static final int EXTRACTORS_NUMBER = 5;
    public static final int ENERGIZERS_NUMBER = 3;
    public static final int TRANSPORTERS_NUMBER = 7;

    private Positionable[][] map;

    private static Environment INSTANCE = null;

    private Base base;

    private Environment() {
        map = new Positionable[WIDTH][HEIGHT];
        base = new Base(DETECTORS_NUMBER, EXTRACTORS_NUMBER, ENERGIZERS_NUMBER, TRANSPORTERS_NUMBER);
        this.init();
    }


    public static synchronized Environment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Environment();
        }
        return INSTANCE;
    }

    public void add(Positionable positionable) {
        Position position = positionable.getPosition();
        map[position.x][position.y] = positionable;

    }

    public Positionable get(Position position) {
        return map[position.x][position.y];
    }

    public void empty(Position position) {
        map[position.x][position.y] = null;
    }

    private void init() {
        base.setPosition(getrandomPosition());
        this.add(base);
        for (Positionable positionable : base.getAgents()) {
            this.add(positionable);
        }
    }

    private Position getrandomPosition() {
        int x = 0;
        int y = 0;
        do {
            x = (int) (Math.random() * (WIDTH));
            y = (int) (Math.random() * (HEIGHT));
        } while (map[x][y] != null);
        return new Position(x, y);
    }

    public boolean moveTo(Positionable positionable, Position newPostion) {
        if (get(newPostion) == null) {
            empty(positionable.getPosition());
            positionable.setPosition(newPostion);
            add(positionable);
            return true;
        }
        return false;
    }

}
