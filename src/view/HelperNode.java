/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author lytsikas
 */
public class HelperNode extends Parent implements Grid, Orientable{
    int col, row, oriantation;
    
    public HelperNode(double width, double height, int oriantation){
        this.oriantation = oriantation;
        Rectangle rect = new Rectangle(width, height);
        rect.setStroke(Color.BLUE);
        rect.setFill(Color.RED);
        rect.setOpacity(0);
        getChildren().add(rect);
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
    public void setPosition(int row, int col) {
       setCol(col);
       setRow(row);
    }

    @Override
    public void setRow(int row) {
       this.row = row;
    }

    public int getOriantation() {
        return oriantation;
    }

    @Override
    public void setOriantation(int oriantation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
