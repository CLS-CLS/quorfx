/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 * 
 * @author lytsikas
 */
public class Players {
    
    int players[] = new int[2];
    int currentPlayer;
    
    
    public Players() {
        
    }
    
    public int getNextPlayer(){
        int nextPlayer = currentPlayer;
        nextPlayer++;
        if (nextPlayer == players.length){
            nextPlayer = 0;
        }
        return nextPlayer;
    }
    
    public int nextTurn(){
        currentPlayer = getNextPlayer();
        return currentPlayer;
    }
    
    public void setCurrentPlayer(int player){
        currentPlayer = player;
    }
    
    public int getCurrentPlayer(){
        return currentPlayer;
    }
    
    
    
    
}
