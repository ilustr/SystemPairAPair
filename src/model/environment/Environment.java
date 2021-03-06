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
    public static final int HEIGHT = 20;

    public static final int MIN_QUANTITY_ORE = 20;
    public static final int MAX_QUANTITY_ORE = 70;

    public static final int DETECTORS_NUMBER = 2;
    public static final int ORE_NUMBER = 10;
    public static final int DIGGERS_NUMBER = 12;
    public static final int ENERGIZERS_NUMBER = 3;
    public static final int TRANSPORTERS_NUMBER = 7;

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
        if( isInsideTheMap(position) )
            return map[position.x][position.y];
        else
            return null;
    }

    public synchronized Positionable get(int x, int y) {
        return map[x][y];
    }

    public synchronized void empty(Position position) {
        map[position.x][position.y] = null;
    }

    private void init() {
        Position posBase;
        do {
            posBase = getRandomPosition();
        }while (posBase.x == 0|| posBase.x == WIDTH-2 || posBase.y == 0 || posBase.y == HEIGHT-2);
        
        this.base.init(posBase);
        for (Positionable positionable : base.getAgents()) {
            positionable.setPosition(Environment.getPopPosition(posBase));
        }

        for (int i = 0; i < ORE_NUMBER; i++) {
            int randomQuantity = (int) (Math.random() * (MAX_QUANTITY_ORE - MIN_QUANTITY_ORE)) + MIN_QUANTITY_ORE;
            Ore ore = new Ore(randomQuantity);
            do {
                ore.setPosition(getRandomPosition());
            } while (isNextTo(ore, posBase));
            this.add(ore);
        }
        this.add(base);
    }

    private boolean isInsideTheMap(Position pos) {
        return pos.x >= 0 && pos.x < WIDTH && pos.y >= 0 && pos.y < HEIGHT;
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
    
    public static synchronized Position getPopPosition(Position base){
       return new Position(base.x +1 ,base.y+1);
    }

    public synchronized Position getEmptyPositionAroundBase() {

        Position pos = getInstance().getBase().getPosition();
        for (int i = pos.y - 1; i <= pos.y + 1; ++i) {
            for (int j = pos.x - 1; j <= pos.x + 1; j++) {
                if (i != 0 && j != 0 && getInstance().get(j, i) == null) {
                    return new Position(j, i);
                }
            }
        }
        return null;
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

    public void goOnBase(Agent agent) {
        if (!agent.isOnBase()) {
            getInstance().base.getAgentsInside().add(agent);
            getInstance().empty(agent.getPosition());
            getInstance().refreshAgent(agent);
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
    
    public ArrayList<Agent> getAgentsInRange(Position pos, int range) {
        ArrayList<Agent> agents = new ArrayList<>();
        
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                Positionable positionable = Environment.getInstance().get(new Position(pos.x+i, pos.y+j));
                if(positionable != null)
                {
                    if(positionable instanceof Agent)
                    {
                        agents.add((Agent) positionable);
                    }
                }
            }
        }
        return agents;
    }
    
    public synchronized void removeFromMap(Position e) {
        Information infos = new Information();
        ArrayList<Position> sendPos = new ArrayList<>();
        sendPos.add(e);
        infos.setPos(sendPos);
        infos.setSource(get(e));
        
        map[e.x][e.y] = null;
        
        setChanged();
        notifyObservers(infos);
    }
}
