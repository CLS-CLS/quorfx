/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

/**
 *
 * @author lytsikas
 */
public class Wall extends Parent implements Grid, Orientable{
   
    private int row;
    private int col;
    private int counter = 0;
    private int oriantation = HORIZONTAL;
    private Rectangle rect;
    
     public Wall(int oriantation){
        rect = new Rectangle(Config.WALL_WIDTH ,Config.WALL_HEIGHT);
        rect.setFill(Color.YELLOW);
        setOriantation(oriantation);
        getChildren().add(rect);
    }
    
    @Override
    public int getOriantation() {
        return oriantation;
    }
   
       
    @Override
    public final void setOriantation(int oriantation){
        
       if (this.oriantation==HORIZONTAL && oriantation==VERTICAL){
           rect.getTransforms().add(new Translate(Config.WALL_HEIGHT,0));
           rect.getTransforms().add(new Rotate(90,0,0));
           
       }
       if (this.oriantation==VERTICAL && oriantation==HORIZONTAL){
           rect.getTransforms().clear();
       }
       this.oriantation = oriantation;
       
    }
    
    
    public void animateWall(){
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(100);
        final double red = Color.web("#FFFF00").getRed();
        final double green = Color.web("#FFFF00").getGreen();
        final double blue = Color.web("#FFFF00").getBlue();
        KeyFrame kf1 = new KeyFrame(Duration.millis(5),new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent arg0) {
                counter+=3;
                double color = blue + ((double)counter)/100;
                if (color>1) color=1;
                rect.setFill(Color.color(red,green,color));
            }
        });
        timeline.getKeyFrames().add(kf1);
        timeline.play();
                
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
       setRow(row);
       setCol(col);
    }

    @Override
    public void setRow(int row) {
        this.row = row;
    }
   
    
}
