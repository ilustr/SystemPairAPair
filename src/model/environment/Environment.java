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
import model.agent.Detector;
import model.agent.Digger;
import model.utils.Information;
import model.utils.Position;

/**
 *
 * @author maxime
 */
public class Environment extends Observable {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    
    public static final int MIN_QUANTITY_ORE = 12;
    public static final int MAX_QUANTITY_ORE = 29;

    public static final int DETECTORS_NUMBER = 5;
    public static final int ORE_NUMBER = 5;
    public static final int DIGGERS_NUMBER = 7;
    public static final int ENERGIZERS_NUMBER = 3;
    public static final int TRANSPORTERS_NUMBER = 7;

    private Positionable[][] map;

    private static Environment INSTANCE = null;

    private Base base;

    private Environment() {
        map = new Positionable[WIDTH][HEIGHT];
        base = new Base(DETECTORS_NUMBER, DIGGERS_NUMBER, ENERGIZERS_NUMBER, TRANSPORTERS_NUMBER);
        this.init();
    }

    public static synchronized Environment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Environment();
        }
        return INSTANCE;
    }

    public synchronized boolean dig(Positionable positionable) {
        if (get(positionable.getPosition()) instanceof Ore) {
            return ((Ore) positionable).dig();
        }
        return false;
    }

    public synchronized void add(Positionable positionable) {
        Position position = positionable.getPosition();
        map[position.x][position.y] = positionable;

    }

    public synchronized Positionable get(Position position) {
        return map[position.x][position.y];
    }

    public synchronized void empty(Position position) {
        map[position.x][position.y] = null;
    }

    private void init() {
        base.setPosition(getrandomPosition());
        this.add(base);
        for (Positionable positionable : base.getAgents()) {
            positionable.setPosition(getrandomPosition());
            this.add(positionable);
        }

        for (int i = 0; i < ORE_NUMBER; i++) {
            int randomQuantity = (int) (Math.random() * (MAX_QUANTITY_ORE - MIN_QUANTITY_ORE)) + MIN_QUANTITY_ORE;
            Ore ore = new Ore(randomQuantity);
            ore.setPosition(getrandomPosition());
            this.add(ore);
        }
    }

    private synchronized Position getrandomPosition() {
        int x = 0;
        int y = 0;
        do {
            x = (int) (Math.random() * (WIDTH));
            y = (int) (Math.random() * (HEIGHT));
        } while (map[x][y] != null);
        return new Position(x, y);
    }

    public synchronized boolean moveTo(Positionable positionable, Position newPosition) {
        if (newPosition.x < 0 || newPosition.x >= WIDTH || newPosition.y < 0 || newPosition.y >= HEIGHT) {
            return false;
        }

        if (get(newPosition) == null) {
            ArrayList<Position> sendPos = new ArrayList<>();
            sendPos.add(positionable.getPosition());
            empty(positionable.getPosition());
            positionable.setPosition(newPosition);
            sendPos.add(newPosition);
            add(positionable);
            setChanged();
            notifyObservers(sendPos);
            return true;
        }
        return false;
    }

    public void startGame() {
        for (Agent agent : base.getAgents()) {
            new Thread(agent).start();
        }
    }

    public void displayMap() {
        System.out.println("");
        for (int i = 0; i < WIDTH; i++) {
            System.out.print("|");
            for (int j = 0; j < HEIGHT; j++) {
                char print = ' ';
                if (map[i][j] != null) {
                    Positionable p = map[i][j];
                    if (p instanceof Base) {
                        print = 'B';
                    } else if (p instanceof Detector) {
                        print = 'D';
                    } else if (p instanceof Digger) {
                        print = 'E';
                    } else {
                        print = 'O';
                    }
                }
                System.out.print("" + print + "|");
            }
            System.out.println("");
        }
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public boolean isThereOre(Position position){  
        if (get(position) instanceof Ore) {
            return true;
        }
        return false;
    }   
}
