/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.agent.Agent;
import model.environment.Environment;
import model.utils.Information;
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

    private void initBackground() {
        setLayout(new BorderLayout());
        background = new JLabel(new ImageIcon(getClass().getResource("/images/backGroundSpace2.jpg")));
        add(background);
        background.setLayout(new GridLayout(Environment.WIDTH, Environment.HEIGHT));
        background.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    private void init() {
        for (int i = 0; i < Environment.WIDTH; ++i) {
            for (int j = 0; j < Environment.HEIGHT; j++) {
                Positionable p = Environment.getInstance().get(new Position(i, j));
                if (p != null  && !( p instanceof Agent )) {
                    cells[i][j] = new JLabel(p.getDisplayImage());
                } else {
                    cells[i][j] = new JLabel(" ");
                }
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                background.add(cells[i][j]);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Information infos = (Information) arg;
        cells[infos.getPos().get(0).x][infos.getPos().get(0).y].setIcon(null);
        if (infos.getSource() instanceof Agent) {
            Agent a = (Agent) infos.getSource();
            if (a.isOnBase()) {
                cells[infos.getPos().get(1).x][infos.getPos().get(1).y].setIcon(null);
            } else {
                cells[infos.getPos().get(1).x][infos.getPos().get(1).y].setIcon(infos.getSource().getDisplayImage());
            }
        } else {
            cells[infos.getPos().get(0).x][infos.getPos().get(0).y].setIcon(infos.getSource().getDisplayImage());
        }
    }
}
