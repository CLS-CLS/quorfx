/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author lytsikas
 */
public class Tile extends BoardTile{
    private boolean hasPlayer;
    private int player;
   
    
    public Tile(int row, int col) {
        super(row, col);
    }
       
    public void setHasPlayer(boolean hasPlayer){
        this.hasPlayer = hasPlayer;
    }
    
    public void setPlayer(int player){
        this.player = player;
        setHasPlayer(true);
    }
    
    public int getPlayer(){
        if (hasPlayer()){
            return player;
        }else{
            return -1;
        }
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }
}
