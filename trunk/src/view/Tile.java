/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author lytsikas
 */
public class Tile extends Rectangle implements Grid{
    
    private int col;
    private int row;
    public Tile(double x, double y, double width, double height,Paint color) {
        super(x, y, width, height);
        setFill(color);
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public void setRow(int row) {
        this.row = row;
    }
    /**
     * Convenience method
     * @param row
     * @param col 
     */
    @Override
    public void setPosition(int row, int col){
        setCol(col);
        setRow(row);
    }
   
}
