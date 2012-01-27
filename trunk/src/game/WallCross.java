/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author lytsikas
 */
public class WallCross extends BoardTile{
    private boolean enabled;
    
    public WallCross(int row, int col) {
        super(row, col);
    }
     public void setEnabled(boolean isEnabled){
        System.out.println("WallCross (" + row+","+col+")-" + enabled);
        enabled = isEnabled;
    }
    
    public boolean isEnabled(){
        return enabled;
    }
    
    
    
}
