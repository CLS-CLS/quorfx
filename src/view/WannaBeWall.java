/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * WannaBe wall is the "hovering" wall. It is used when the users hovers over wall region, 
 * in order to notify the user that this region is eligible to put a wall.
 * @author CHR
 */
public class WannaBeWall extends Parent implements Grid, Orientable{
    int row, col, oriantation;
    double width = Config.WALL_WIDTH, height = Config.WALL_HEIGHT;
    Path path;
      
    public WannaBeWall(int oriantation){
        path = createPath();
        getChildren().add(path);
        setOriantation(oriantation);
        setMouseTransparent(true);
     }
    
    
    @Override
    public int getCol() {
        return col;
    }

    @Override
    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public int getOriantation() {
        return oriantation;
    }

    @Override
    public void setOriantation(int oriantation) {
       if (this.oriantation==HORIZONTAL && oriantation==VERTICAL){
          path.getTransforms().add(new Translate(Config.WALL_HEIGHT,0));
          path.getTransforms().add(new Rotate(90,0,0));
           
       }
       if (this.oriantation==VERTICAL && oriantation==HORIZONTAL){
           path.getTransforms().clear();
       }
       this.oriantation = oriantation;
       
    
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public void setPosition(int row, int col) {
        setRow(row);
        setCol(col);
    }

    private Path createPath() {
        
        double xHeight = height * 110 / 100;
        Path path = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.setX(0.0f);
        moveTo.setY(height-xHeight);

        HLineTo hLineTo = new HLineTo();
        hLineTo.setX(width);
              
        ArcTo arcTo = new ArcTo();
        arcTo.setX(width);
        arcTo.setY(xHeight);
        arcTo.setRadiusX(xHeight- height/2);
        arcTo.setRadiusY(xHeight- height/2);
        arcTo.setSweepFlag(true);
        
        HLineTo hLineTo2 = new HLineTo();
        hLineTo2.setX(0f);
        
        ArcTo arcTo2 = new ArcTo();
        arcTo2.setX(0f);
        arcTo2.setY(0.0f);
        arcTo2.setRadiusX(xHeight- height/2);
        arcTo2.setRadiusY(xHeight- height/2);
        arcTo2.setSweepFlag(true);
      
        path.getElements().add(moveTo);
        path.getElements().add(hLineTo);
        path.getElements().add(arcTo);
        path.getElements().add(hLineTo2);
        path.getElements().add(arcTo2);
                
        path.setStroke(Color.GREY);
       
        Stop s1 = new Stop(0, Color.GRAY);
        Stop s2 = new Stop(0.5, Color.WHITE);
        Stop s3 = new Stop(1, Color.GRAY);
        ArrayList<Stop> stops = new ArrayList<>();
        stops.add(s1); stops.add(s2);stops.add(s3);        
        
        LinearGradient lg = new LinearGradient(50,height - xHeight ,50,xHeight,false,null,stops);
        path.setFill(lg);
        path.setOpacity(0.6);
        return path;
    }
    
    
    
    
    
    
    
    
}
