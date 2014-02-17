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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.environment.Environment;
import model.utils.Position;
import model.utils.Positionable;

public class IHM extends JFrame implements Observer{

    private JLabel[][] cells;

    public IHM(int width, int height) {
        cells = new JLabel[Environment.WIDTH][Environment.HEIGHT];
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        Environment.getInstance().addObserver(this);
    }

    private void init() {
        setLayout(new GridLayout(Environment.WIDTH, Environment.HEIGHT));
        for (int i = 0; i < Environment.WIDTH; ++i) {
            for (int j = 0; j < Environment.HEIGHT; j++) {
            //    System.out.println("i " + i  + " j " + j );
                Positionable p = Environment.getInstance().get(new Position(i, j));
                if (p != null) {
                    cells[i][j] = new JLabel(p.getDisplayString());
                } else {
                    cells[i][j] = new JLabel(" ");
                }
                add(cells[i][j]);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Position> positions = (ArrayList<Position>) arg;
        String label = cells[positions.get(0).x][positions.get(0).y].getText();
        cells[positions.get(0).x][positions.get(0).y].setText(" ");
        cells[positions.get(1).x][positions.get(1).y].setText(label);
    }
}