package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ShittyMain extends Application {
    @Override
    public void start (Stage primaryStage) {
        Group g = new Group();
        Scene scene = new Scene(g, 600, 600);
        primaryStage.setScene(scene);
        shittyRun(g);
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }

    public void shittyRun (Group g) {
        System.out.println("Shitty running");
        
    }

}
