/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.utils;

/**
 *
 * @author maxime
 */
public class Position {
    
    public int x;
    public int y;
    
    public Position(int x , int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public boolean equals(Position obj) {
        if (obj == null) {
            return false;
        }
   
        if (this.x != obj.x) {
            return false;
        }
        if (this.y != obj.y) {
            return false;
        }
        return true;
    }
    
    
}
