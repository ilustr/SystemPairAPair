/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.utils;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import model.utils.Position;

/**
 *
 * @author maxime
 */
public interface Positionable {
    
    public void setPosition(Position position);
    
    public Position getPosition();
    
    public String getDisplayString();

    public ImageIcon getDisplayImage();
}
