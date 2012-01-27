/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author lytsikas
 */
public enum Direction {
    
    UP(0), DOWN(1), LEFT(2), RIGHT(3);

    private Direction(int loc) {
        this.loc = loc;
       
    }
    
    int loc;
    
    
}
