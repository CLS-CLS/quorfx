/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author CHR
 */
public class Config {
    public static final double APP_WIDTH = 640;
    public static final double APP_HEIGHT = 600;
    public static final double TILE_LENGTH = 50;
    public static final double GAP = 12;
    public static final int WALL_ANIMATION_TIME = 500;
    
    
    
    //When the wall width is greater than the wall height, the wall is considered to have
    //"Horizontal" oriantation. The WALL_WIDTH and WALL_HEIGHT are computed when the wall
    //is oriented horizontally
    public static final double WALL_WIDTH = GAP + TILE_LENGTH * 2;
    public static final double WALL_HEIGHT = GAP;
    public static final double BOARD_WIDTH = TILE_LENGTH * 9 + GAP * 8;
    public static final double BOARD_HEIGHT = TILE_LENGTH * 9 + GAP * 8;
         
}
