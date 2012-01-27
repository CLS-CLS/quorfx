/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author CHR
 */
public class HoverHandler implements EventHandler<MouseEvent> {

    /**
     * Used to record the last WannabeWall displayed in the GUI in order to be able to remove it, when the wall is no
     * longer needed.
     */
    WannaBeWall hist = null;
    
    /**
     * Used to hold the alternative row and col of the position of the WannabeWall 
     * when the original position of the WannaBeWall is not valid.
     */
    int altRow, altCol;
    
    MainBoard mb;

    public HoverHandler(MainBoard mb) {
        this.mb = mb;
    }

    @Override
    public void handle(MouseEvent me) {
        int col = Tools.getColFromX(me.getSceneX());
        int row = Tools.getRowFromY(me.getSceneY());
        if (MouseEvent.MOUSE_MOVED.getName().equals(me.getEventType().getName())) {
            // In case the mouse is hovering over a CrossWall section we do not want to take any action.
            if (isCrossSection(row, col)) {
                return;
            }
                                 
            //In case the mouse is moved in another HelperWall region, we want to know the oriantation
            //of the WallRegion coresponding to the hovering coordinates. 
            WallRegion wr = getWallRegion(me.getSceneX(), me.getSceneY());
            if (wr!=null){
                 mouseMoved(col, row, wr.getOriantation());
            }
            //a wall can only be placed when a WannaBeWall exists.
        } else if (MouseEvent.MOUSE_PRESSED.getName().equals(me.getEventType().getName()) && hist != null) {
            mouseClicked();
        } else if (MouseEvent.MOUSE_EXITED.getName().equals(me.getEventType().getName())) {
           unhoverWall();
        }
    }

    private void hover(int col, int row, int oriantation) {
        if (hist == null || col != Tools.getColFromX(hist.getTranslateX()) || row != Tools.getRowFromY(hist.getTranslateY())) {
            unhoverWall();
            hist = (WannaBeWall) mb.hoverWall(Tools.getXfromCol(col), Tools.getYfromRow(row), oriantation);
        }
    }

    private void mouseClicked() {
        mb.setWall(Tools.getRowFromY(hist.getTranslateY()), Tools.getColFromX(hist.getTranslateX()), hist.getOriantation());
         unhoverWall();
        
    }
    
    /**
     * Removes any wannaBewalls already in the board.
     * Checks if the original row and col are valid positions for WannaBeWall. If not checks 
     * for an alternative row and col. Then if there is a valid position it creates and displays
     * the WannableWall. 
     * @param col
     * @param row
     * @param oriantation the orientation of the WannaBeWall to be created
     */
    private void mouseMoved(int col, int row, int oriantation) {
        if (!mb.controller.isValidWallRaise(row, col)) {
            if (hasAlternativeHoverWall(col, row, oriantation)) {
                col = getAltCol();
                row = getAltRow();
                hover(col, row, oriantation);
            } else {
                if (hist != null) {
                    unhoverWall();
                }
            }
        } else {
            hover(col, row, oriantation);
        }

    }

    /**
     * checks if the coords corresponds to a cross section of the walls
     * a cross section has an odd row and column number
     * @param me
     * @return
     */
    private boolean isCrossSection(int row, int col) {
        boolean result = false;
        if (row % 2 == 1 && col % 2 == 1) {
            result = true;
        }
        return result;
    }
    
    /**
     * Checks if there is an alternative col and row for positioning a WannaBeWall.
     * If such position exists it updates the altCol and altRow field to the ones found.
     * The alternative position depends on the orientation given. If Horizontal, the 
     * alternative position is 2 grids left of the original, if Vertical , 2 grids up.
     * @param col
     * @param row
     * @param oriantation
     * @return true if the alternative position is valid, false otherwise
     */
    private boolean hasAlternativeHoverWall(int col, int row, int oriantation) {
        altCol = col;
        altRow = row;
        switch (oriantation) {
            case Orientable.HORIZONTAL:
                col = col - 2;
                altCol = col;
                if (col < 0) {
                    return false;
                }
                return mb.controller.isValidWallRaise(row, col);
            case Orientable.VERTICAL:
                row = row - 2;
                altRow = row;
                return mb.controller.isValidWallRaise(row, col);

        }
        return false;

    }
    
    /**
     * Use this method only when the "hasAlternativeHoverWall"
     * returns true.
     * @return the alternative column of the WannaBeWall
     */
    private int getAltCol() {
        return altCol;
    }
    
     /**
     * Use this method only when the "hasAlternativeHoverWall"
     * returns true.
     * @return the alternative row of the WannaBeWall
     */
    private int getAltRow() {
        return altRow;
    }

    /**
     * Finds the WallRegion that contains the coordinates X,Y.
     *
     * @param sceneX
     * @param sceneY
     * @return
     */
    private WallRegion getWallRegion(double sceneX, double sceneY) {
        WallRegion wallRegion = null;
        for (Node wr : mb.helperWalls.getChildren()) {
            if (wr.contains(sceneX - wr.getTranslateX(), sceneY - wr.getTranslateY())) {
                wallRegion = (WallRegion) wr;
                break;
            }
        }
        return wallRegion;
   }
    
    private void unhoverWall(){
        mb.unhoverWall(hist);
        hist = null;
        
    }
}
