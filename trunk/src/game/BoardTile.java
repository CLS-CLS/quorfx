/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;

/**
 * a board tile is the model used to represent the components of the board
 * The board components can be either a tile , or a wall, or a wall-cross. 
 * below is how the model is constructed
 * ____________________________________
 * |T W T W T W T W T W T W T W T W T |
 * |W C W C W C W C W C W C W C W C W |
 * |T W T W T W T W T W T W T W T W T |
 * |.....                             |
 * |                                  |
 * |T W T W T W T W T W T W T W T W T |
 * 
 * where T- tile . W-wall, C- wall cross
 *
 * @author lytsikas
 */
public abstract class BoardTile {
    
    public static final int WALL_TYPE = 1;
    private BoardTile[] connections = new BoardTile[4];
    int row, col;

    public BoardTile(int row, int col) {
        this.col = col;
        this.row = row;
    }
    /**
     * @return all the direction that the BoardTile has connections 
     */
    public Direction[] getConnDirections(){
        ArrayList<Direction> ar = new ArrayList<>();
        for (Direction d:Direction.values()){
            if (hasConnection(d))ar.add(d);
        }
        return (Direction[])ar.toArray();
    }
    
    public void addConnection(BoardTile tile, Direction d){
        connections[d.loc] = tile;
    }
    
    public boolean hasConnection(Direction d){
        return connections[d.loc] != null;
    }
    
    public BoardTile getConnection(Direction d){
        return connections[d.loc];
    }
    
    public BoardTile[] getConnections(){
        return connections;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
    
    
    
    
    
}
