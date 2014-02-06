/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syspairapair;

/**
 *
 * @author maxime
 */
public class Base implements  Positionable{

    private Position position;

    public Base(Position position) {
        this.position = position;
    }

    public Base() {
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    
    

}
