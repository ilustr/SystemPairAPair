/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syspairapair;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author maxime
 */
public class Environnement implements Observer {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    

    private Positionable[][] map;
    private Base base;
    private ArrayList<Agent> agents;
    
    
    public Environnement() {
        map = new Positionable[WIDTH][HEIGHT];
        base = new Base();
        agents = new ArrayList<>();
        this.init();
    }
    
    public void add(Positionable positionable){
        Position position = positionable.getPosition();
        map[position.x][position.y] = positionable;
        
    }
    
    public Positionable get(Position position){
        return map[position.x][position.y];
    }

    private void init() {
        base.setPosition(getrandomPosition());
        this.add(base);
    }
    

    private Position getrandomPosition() {
        int x = (int) (Math.random() * (WIDTH));
        int y = (int) (Math.random() * (HEIGHT));
        return new Position(x, y);
    }

    @Override
    public void update(Observable o, Object arg) {
        if ( o instanceof Agent)
        {
            
        }
    }

}
