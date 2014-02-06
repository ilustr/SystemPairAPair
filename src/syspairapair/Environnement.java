/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syspairapair;

import model.utils.Position;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author maxime
 */
public class Environnement implements Observer {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private Object[][] map;
    private Base base;

    public Environnement() {
        map = new Object[WIDTH][HEIGHT];
        base = new Base();
    }

    private void init() {

    }

    private Position getrandomPosition() {
        int x = (int) (Math.random() * (WIDTH));
        int y = (int) (Math.random() * (HEIGHT));
        return new Position(x, y);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
