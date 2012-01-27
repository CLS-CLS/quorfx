/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



/**
 *
 * @author lytsikas
 */
public class QuorridorStart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Config.APP_WIDTH, Config.APP_HEIGHT, Color.AQUAMARINE);
        root.getChildren().add(new MainBoard());
        primaryStage.setScene(scene);
        primaryStage.show();
       
   }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}

