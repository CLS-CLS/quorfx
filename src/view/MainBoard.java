/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Controller;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author lytsikas
 */
public class MainBoard extends Group {
    /**
     * general group for holding or related wall nodes
     */
    Group wallGroup = new Group();
    
    /**
     * contains the walls that are displayed when hovering over possible wall positions.
     */
    Group wallToBePlacedGroup = new Group();
    
    /**
     * contains all the placed wall in the board
     */
    Group placedWalls = new Group();
    
    /**
     * contains the invisible helper walls used to define the wall section in the board
     */
    Group helperWalls = new Group();
    
    /**
     * contains all the tiles of the board
     */
    Group tileGroup = new Group();
    
    /**
     * the handler used when hovering over the wall section, or when adding a wall. 
     */
    private HoverHandler hv = new HoverHandler(this);
    
    /**
     * the model of the game
     */
    final Controller controller = new Controller();

    public MainBoard() {
        wallGroup.getChildren().addAll(helperWalls, wallToBePlacedGroup, placedWalls);
        getChildren().addAll(tileGroup, wallGroup);
        drawBoard();
        addHandlers();

    }

    private void drawBoard() {

        //Background
        Rectangle background = new Rectangle(0, 0, Config.BOARD_WIDTH, Config.BOARD_HEIGHT);
        background.setFill(Color.rgb(125, 106, 62));
        getChildren().add(0, background);
        Effect l = createEffect();

        //Tiles
        int colCounter = -1;
        for (double x = 0; x < Config.BOARD_WIDTH; x += (Config.TILE_LENGTH + Config.GAP)) {
            colCounter++;
            int rowCounter = -1;
            for (double y = 0; y < Config.BOARD_HEIGHT; y += (Config.TILE_LENGTH + Config.GAP)) {
                rowCounter++;
                Tile tile = new Tile(x, y, Config.TILE_LENGTH, Config.TILE_LENGTH, Color.BURLYWOOD);
                tile.setPosition(rowCounter * 2, colCounter * 2);

                tile.setEffect(createEffect());
                tileGroup.getChildren().add(tile);
                tile.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent me) {
                        System.out.println("Mouse Clicked tile (" + ((Tile) me.getSource()).getRow() + ","
                                + ((Tile) me.getSource()).getCol() + ")");
                        me.consume();
                    }
                });
            }
        }

        //Wall region
        double coord = Config.TILE_LENGTH;
        for (int i = 1; i < 9; i++) {
            WallRegion wr = new WallRegion(WallRegion.VERTICAL);
            helperWalls.getChildren().add(wr);
            wr.setTranslateX(coord);
            wr.setTranslateY(0);
            wr = new WallRegion(WallRegion.HORIZONTAL);
            helperWalls.getChildren().add(wr);
            wr.setTranslateX(0);
            wr.setTranslateY(coord);
            coord += Config.TILE_LENGTH + Config.GAP;
        }

    }
    
   
    private Effect createEffect() {
        //light effect
        Distant light = new Distant();
        light.setAzimuth(-135.0f);
        Lighting l = new Lighting();
        l.setLight(light);
        l.setSurfaceScale(2.0f);
        DropShadow ef = new DropShadow();
        ef.setOffsetX(Config.TILE_LENGTH / 10);
        ef.setOffsetY(Config.TILE_LENGTH / 10);
        ef.setWidth(Config.TILE_LENGTH / 10);
        ef.setInput(l);
        return ef;
    }
    
    /**
     * adds a hovering wall in the board
     * @param x
     * @param y
     * @param oriantation
     * @return 
     */
    public synchronized Node hoverWall(double x, double y, int oriantation) {

        WannaBeWall wall = new WannaBeWall(oriantation);
        wallToBePlacedGroup.getChildren().add(wall);
        wall.setTranslateX(x);
        wall.setTranslateY(y);
        wall.setEffect(createEffect());

        return wall;

    }
    
    /**
     * removes the specific hovering wall
     * @param node 
     */
    public synchronized void unhoverWall(Node node) {
        wallToBePlacedGroup.getChildren().remove(node);
    }
    
    /**
     * Creates an animation when the players chooses to put a wall in the board/
     * @param toXposition the x-coordinate of the position the wall should be putted.
     * @param toYposition the y-coordinate
     * @param oriantation the orientation the wall should have (vertical or horizontal)
     */
    public void animateWall(double toXposition, double toYposition, int oriantation) {
        Wall wall = new Wall(oriantation);
        
        //create effect for the animated wall
        Distant light = new Distant();
        light.setAzimuth(-135.0f);
        Lighting l = new Lighting();
        l.setLight(light);
        l.setSurfaceScale(2.0f);
        wall.setEffect(l);

        double startingX = 0, startingY = 0;
        float scaleFactor = 3f;

        switch (wall.getOriantation()) {
            case Wall.HORIZONTAL:
                startingX = (Config.BOARD_WIDTH - Config.WALL_WIDTH * scaleFactor);
                startingY = Config.BOARD_HEIGHT / 2;
                break;
            case Wall.VERTICAL:
                startingX = Config.BOARD_WIDTH / 2;
                startingY = (Config.BOARD_HEIGHT - Config.WALL_WIDTH * scaleFactor);
                break;
        }
        System.out.println(startingX + "  " + startingY);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(Config.WALL_ANIMATION_TIME), wall);
        translateTransition.setFromX(startingX);
        translateTransition.setToX(toXposition);
        translateTransition.setFromY(startingY);
        translateTransition.setToY(toYposition);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(Config.WALL_ANIMATION_TIME), wall);
        scaleTransition.setToX(1f);
        scaleTransition.setToY(1f);
        scaleTransition.setFromX(scaleFactor);
        scaleTransition.setFromY(scaleFactor);
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                translateTransition,
                scaleTransition);
        parallelTransition.setCycleCount(1);
        parallelTransition.play();
        placedWalls.getChildren().add(wall);

    }

    public void addHandlers() {
        
            wallGroup.addEventHandler(MouseEvent.MOUSE_MOVED, hv);
            wallGroup.addEventHandler(MouseEvent.MOUSE_PRESSED, hv);
            wallGroup.addEventFilter(MouseEvent.MOUSE_EXITED, hv);
        
//        for (Node node : helperWalls.getChildren()) {
//            node.addEventHandler(MouseEvent.MOUSE_MOVED, hv);
//            node.addEventHandler(MouseEvent.MOUSE_CLICKED, hv);
//            node.addEventFilter(MouseEvent.MOUSE_EXITED, hv);
//        }
   }

    public void removeHandler() { 
      
            wallGroup.removeEventHandler(MouseEvent.MOUSE_MOVED, hv);
            wallGroup.removeEventHandler(MouseEvent.MOUSE_PRESSED, hv);
            wallGroup.removeEventFilter(MouseEvent.MOUSE_EXITED, hv);
       
//        for (Node node : helperWalls.getChildren()) {
//            node.removeEventHandler(MouseEvent.MOUSE_MOVED, hv);
//            node.removeEventHandler(MouseEvent.MOUSE_CLICKED, hv);
//            node.removeEventFilter(MouseEvent.MOUSE_EXITED, hv);
//        }
    }

    void setWall(int row, int col, int oriantation) {
        animateWall(Tools.getXfromCol(col), Tools.getYfromRow(row), oriantation);
        controller.raiseWall(row, col);
//        TODO
//        controller.getCurrentPlayer().decreaseWall();
//        state.nextTurn();
    }
}
