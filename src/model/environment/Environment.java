/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.environment;

import model.utils.Positionable;
import java.util.ArrayList;
import java.util.Observable;
import model.agent.Agent;
import model.agent.Detector;
import model.agent.Digger;
import model.agent.Transporter;
import model.utils.Information;
import model.utils.Position;

/**
 *
 * @author maxime
 */
public class Environment extends Observable {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    
    public static final int MIN_QUANTITY_ORE = 20;
    public static final int MAX_QUANTITY_ORE = 70;

    public static final int DETECTORS_NUMBER = 2;
    public static final int ORE_NUMBER = 10;
    public static final int DIGGERS_NUMBER = 2;
    public static final int ENERGIZERS_NUMBER = 3;
    public static final int TRANSPORTERS_NUMBER = 1;

    private Positionable[][] map;

    private static Environment INSTANCE = null;

    private Base base;

    private Environment() {
        map = new Positionable[WIDTH][HEIGHT];
        base = new Base();
        this.init();
    }

    public static synchronized Environment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Environment();
        }
        return INSTANCE;
    }

    public synchronized boolean dig(Position pos) {
        if (get(pos) instanceof Ore) {
            return ((Ore) get(pos)).dig();
        }
        return false;
    }
    
    public synchronized int loadRsc(Position pos, int max) {
        if (get(pos) instanceof Ore) {
            int quantity = 0;
            while  ( ((Ore) get(pos)).getInStack() && quantity < Transporter.LOAD_QUANTITY && quantity < max)
            {
                System.out.println("load 1");
                System.out.println("max :"+max);
                System.out.println("load qute :"+Transporter.LOAD_QUANTITY);
                System.out.println("test :"+ (quantity <= Transporter.LOAD_QUANTITY && quantity <= max));
                quantity++;
            }
            System.out.println("quantity : "+quantity);
            return quantity;
        }
        return -1;
    }
    
    public synchronized boolean noMoreMisterNiceOre(Position pos){
        if (get(pos) instanceof Ore) {
            return ((Ore) get(pos)).getStack() <= 0;
        }
        return true;
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
        Position posBase;
        do{
            posBase = getRandomPosition();
        }while ( posBase.x == 0|| posBase.x == WIDTH-1 || posBase.y == 0 || posBase.y == HEIGHT-1 );
        
        this.base.init(posBase);
        this.add(base);
        for (Positionable positionable : base.getAgents()) {
            positionable.setPosition(getRandomPosition());
            this.add(positionable);
        }

        for (int i = 0; i < ORE_NUMBER; i++) {
            int randomQuantity = (int) (Math.random() * (MAX_QUANTITY_ORE - MIN_QUANTITY_ORE)) + MIN_QUANTITY_ORE;
            Ore ore = new Ore(randomQuantity);
            do{
                ore.setPosition(getRandomPosition());
            }while(isNextTo(ore, posBase));
            this.add(ore);
        }
    }

    public synchronized Position getRandomPosition() {
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
            Information infos = new Information();
            infos.setPos(sendPos);
            infos.setSource(positionable);
            setChanged();
            notifyObservers(infos);
            return true;
        }
        return false;
    }

    public void startGame() {
        for (Agent agent : base.getAgents()) {
            new Thread(agent).start();
        }
    }

    public static boolean isNextTo(Positionable positionable, Position positionElement) {

        Position pos = positionable.getPosition();

        for (int i = pos.y - 1; i <= pos.y + 1; ++i) {
            for (int j = pos.x - 1; j <= pos.x + 1; j++) {
                if (positionElement.equals(new Position(j, i))) {
                    return true;
                }
            }
        }
        return false;
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

    public boolean isThereOre(Position position) {
        if (position.x < 0 || position.x >= WIDTH || position.y < 0 || position.y >= HEIGHT) {
            return false;
        }

        if (get(position) instanceof Ore) {
            return true;
        }
        return false;
    }

    public void refreshAgent(Positionable positionable) {
        Information infos = new Information();
        ArrayList<Position> sendPos = new ArrayList<>();
        sendPos.add(positionable.getPosition());
        sendPos.add(positionable.getPosition());
        infos.setPos(sendPos);
        infos.setSource(positionable);
        setChanged();
        notifyObservers(infos);
    }
}
