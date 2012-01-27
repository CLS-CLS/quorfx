/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 * contains tools for translating coordinates to grid position and vice versa
 * @author CHR
 */
public class Tools {
    
    public static int getColFromX(double x){
        return getGrid(x);
    }
    
    public static int getRowFromY(double y){
        return getGrid(y);
    }
    
    public static double getYfromRow(int row){
        return getCoordFromGrid(row);
    }
    
    public static double getXfromCol(int col){
        return getCoordFromGrid(col);
    }
    
       
    private static int getGrid(double coord){
        int grid = 0;
        double dummyC = Config.TILE_LENGTH;
        while (coord >= dummyC){
            grid ++;
            dummyC += Config.GAP;
            if (coord <dummyC)break;
            grid++;
            dummyC += Config.TILE_LENGTH;
        }
        return grid;
    }
    
    public static double traslateToX(double x){
        return getXfromCol(getColFromX(x));
    }
    
    public static double translateToY(double y){
        return getYfromRow(getRowFromY(y));
    }
    
    
    
    private static double getCoordFromGrid(int grid){
       int nGap = grid/2;
       int nTl = grid - nGap;
       return nTl * Config.TILE_LENGTH + nGap * Config.GAP;
        
    }
    
}
