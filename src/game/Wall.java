/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author lytsikas
 */
public class Wall extends BoardTile{
    
    private boolean enabled = false;
    
    public Wall(int row, int col) {
        super(row, col);
    }
    
    public void setEnabled(boolean isEnabled){
        System.out.println("Wall (" + row+","+col+")-" + enabled);
        enabled = isEnabled;
    }
    
    public boolean isEnabled(){
        return enabled;
    }
         
    
}
