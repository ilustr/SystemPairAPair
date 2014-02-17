/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.environment.Environment;
import model.utils.Position;
import model.utils.Positionable;

public class IHM extends JFrame implements Observer {

    private JLabel[][] cells;
    private JLabel background;

    public IHM(int width, int height) {
        cells = new JLabel[Environment.WIDTH][Environment.HEIGHT];
        initBackground();
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        Environment.getInstance().addObserver(this);
    }
    
    private void initBackground(){
        setLayout(new BorderLayout());
        background = new JLabel(new ImageIcon(getClass().getResource("/images/backGroundSpace2.jpg")));
        add(background);
        background.setLayout(new GridLayout(Environment.WIDTH, Environment.HEIGHT));
    }

    private void init() {
        for (int i = 0; i < Environment.WIDTH; ++i) {
            for (int j = 0; j < Environment.HEIGHT; j++) {
                //    System.out.println("i " + i  + " j " + j );
                Positionable p = Environment.getInstance().get(new Position(i, j));
                if (p != null) {
                    cells[i][j] = new JLabel(p.getDisplayImage());
                } else {
                    cells[i][j] = new JLabel(" ");
                }
                background.add(cells[i][j]);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Position> positions = (ArrayList<Position>) arg;
        ImageIcon label = (ImageIcon) (cells[positions.get(0).x][positions.get(0).y].getIcon());
        cells[positions.get(0).x][positions.get(0).y].setIcon(null);
        cells[positions.get(1).x][positions.get(1).y].setIcon(label);
    }
}
