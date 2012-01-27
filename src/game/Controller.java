/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;


/**
 *
 * @author lytsikas
 */
public class Controller {

    private Players players = new Players();
      
    private BoardTile[][] board = new BoardTile[18][18];

    public Controller() {
        setUpBoard();
    }
     
    
    private void setUpBoard() {
        {
            int row = 0;
            int col = 0;
            while (row < 18) {
                while (col < 18) {
                    board[row][col] = new Tile(row, col);
                    col++;
                    if (col > 17) {
                        break;
                    }
                    board[row][col] = new Wall(row, col);
                    col++;
                }
                col = 0;
                row++;
                if (row > 17) {
                    break;
                }
                while (col < 18) {
                    board[row][col] = new Wall(row, col);
                    col++;
                    if (col > 17) {
                        break;
                    }
                    board[row][col] =  new WallCross(row, col);
                    col++;
                }
                col = 0;
                row++;
            }
        }
        //make the connections 
        //1. create the UP connectiosn
        for (int row = 1; row < 17; row++) {
            for (int col = 0; col < 17; col++) {
                board[row][col].addConnection(board[row - 1][col], Direction.UP);
            }
        }

        //2. create the DOWN Connections
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 17; col++) {
                board[row][col].addConnection(board[row + 1][col], Direction.DOWN);
            }
        }
        //3. create the RIGHT connections
        for (int row = 0; row < 17; row++) {
            for (int col = 0; col < 16; col++) {
                board[row][col].addConnection(board[row][col + 1], Direction.RIGHT);
            }
        }
        //4. create the LEFT connections
        for (int row = 0; row < 17; row++) {
            for (int col = 1; col < 17; col++) {
                board[row][col].addConnection(board[row][col - 1], Direction.LEFT);
            }
        }
    }
    
    /**
     * a valid move is when the players moves from his tile to another tile (landing tile) where:
     * the "landing" tile is adjacent to the initial tile and it is empty
     * or
     * in case the tile in not empty, the next tile in the same moving direction,
     * or 
     * if the tile in the same moving direction does not exits or is not accessible 
     * because of an active wall, the tile(s) adjacent to the tile which is occupied 
     * by the other player.
     * 
     * @param from
     * @param to
     * @return 
     */
     boolean isValidPlayerMove(Tile from, Tile to){
        
        //the landing tile should be empty
        if (to.hasPlayer())return false;
        
        //and connected with the starting tile
        if (isAdjacent(to, from)){
            if (isConnected(to, from)){
                return true;
            }else {
                return false;
            }
        }
        
        //No adjacent and "to" is empty. 
        //there must be a intermidiate tile where the staring tile is conncted to the intremidiate one and
        // the intermidiate conncted to the landing one. The intermidiate tile should have a player on it in order
        // for the current player to "jump" over it.
        Tile interm = null;
        Direction dir = null;
        for (Direction d: Direction.values()){
            Tile helper = getAdjacentTile(from, d);
            if (helper !=null && isConnected(from, helper) && helper.hasPlayer()){
                interm = helper;
                dir = d;
                break;
            }
        }
        //so such tile exist.. the move is not valid
        if (interm==null)return false;
        
        //the tile exists. The player should jump the tile and land to the next tile 
        //(without changing direction). 
        Tile toLand;
        if ((toLand=getAdjacentTile(interm, dir))!=null && isConnected(interm, toLand)){
            if (toLand==to){
                return true;
            }else {
                return false;
            }
        }
        
        //There is not tile to land if the player jumps the occupied tile without changing direction
        //that is either becuase such tile would be out of the board or the tile is not connected with the
        //occupied tile becuase of a wall. 
        //In that case the player can land in whatever tile adjacent to the occupied tile he wants.
        for (Direction d: Direction.values()){
            Tile adj = getAdjacentTile(interm, d);
            if (isConnected(adj, interm) && adj == to)return true;
        }
        
        return false;
    }
    
   
    
    /**
     * 2 tiles are considered connected when they are adjacent to each other
     * and the there is not active wall between them.
     * @param t1
     * @param t2
     * @return 
     */
    boolean isConnected(Tile t1, Tile t2){
        boolean connected = false ;
        for (Direction d: t1.getConnDirections()){
            Wall wall = (Wall)t1.getConnection(d);   
            if (wall.isEnabled() && wall.hasConnection(d) && wall.getConnection(d)== t2){
                connected = true;
                break;
            }
        }
        return connected;
    }
    /**
     * 2 tiles are considered adjacent to each other when they have one wall in common
     * @param t1
     * @param t2
     * @return 
     */
    boolean isAdjacent(Tile t1, Tile t2){
        boolean adjacent = false ;
        for (Direction d: t1.getConnDirections()){
            Wall wall = (Wall)t1.getConnection(d);   
            if (wall.hasConnection(d) && wall.getConnection(d)== t2){
                adjacent = true;
                break;
            }
        }
        return adjacent;
    }
    
     public boolean isAdjacent(int row1, int col1, int row2, int col2){
        Tile t1 = (Tile)board[row1][col1];
        Tile t2 = (Tile)board[row2][col2];
        return isAdjacent(t1, t2);
    }
    
    /**
     * gets the adjacent tile to the t1 in the given direction
     * @param t1
     * @param d
     * @return the adjacent tile, null if it does not exist.
     */
     Tile getAdjacentTile(Tile t1,Direction d){
        Tile result = null;
        Wall w = (Wall)t1.getConnection(d);
        if (w!=null){
            result = (Tile)w.getConnection(d);
        }
        return result;
    }
    
    public void raiseWall(int row, int col) throws IllegalArgumentException{
        Wall w1 = (Wall)board[row][col];        
        w1.setEnabled(true);
        BoardTile w2 = w1.getConnection(Direction.RIGHT);
        Direction d = null;
        if (w2 !=null && w2 instanceof WallCross){
            d = Direction.RIGHT;
        }else {
            d= Direction.DOWN;
        }
        ((WallCross)w1.getConnection(d)).setEnabled(true);
        ((Wall)w1.getConnection(d).getConnection(d)).setEnabled(true);
    }
    
    public boolean isValidWallRaise(int row, int col){
        boolean valid = true;
        if (row < 0 || col < 0 || row >15 || col >15) return false;
        if (!(board[row][col] instanceof Wall)) throw new IllegalArgumentException("the coords does not correspond to wal posiiton");
        Wall w1 = (Wall)board[row][col];        
        if (w1.isEnabled())return false;
        BoardTile w2 = w1.getConnection(Direction.RIGHT);
        Direction d = null;
        if (w2 !=null && w2 instanceof WallCross){
            d = Direction.RIGHT;
        }else {
            d= Direction.DOWN;
        }
        if (((WallCross)w1.getConnection(d)).isEnabled())return false;
        if (((Wall)w1.getConnection(d).getConnection(d)).isEnabled())return false;
        return valid;
        
    }
    
    public BoardTile getBoardTile(int row, int col){
        return board[row][col];
    }
    
     
}
