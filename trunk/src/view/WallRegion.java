/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author CHR
 */
public class WallRegion extends Parent implements Orientable{
    private double width;
    private double height;
    public WallRegion(int oriantation) {
        this.oriantation = oriantation;
        switch(oriantation){
            case HORIZONTAL :
                width=Config.BOARD_WIDTH;
                height= Config.GAP;
                break;
            case VERTICAL:
                width=Config.GAP;
                height=Config.BOARD_WIDTH;
                
        }
        Rectangle rect = new Rectangle(width, height);
        rect.setOpacity(0);
        getChildren().add(rect);
    }
    
    
    
    int oriantation;
    
    @Override
    public int getOriantation() {
        return oriantation;
    }

    @Override
    public void setOriantation(int oriantation) {
        this.oriantation = oriantation;
    }
  
    
}
