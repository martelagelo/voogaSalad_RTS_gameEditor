package application;

import game_engine.gameRepresentation.Level;
import game_engine.stateManaging.GameElementManager;
import game_engine.stateManaging.GameLoop;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        Image poop = new Image("resources/img/poop.png");
        g.getChildren().add(new ImageView(poop));

        Level currentLevelBeingPlayedYayJavaNamingIsSoMuchFun = new Level();
        GameLoop gameLoop = new GameLoop(currentLevelBeingPlayedYayJavaNamingIsSoMuchFun);
        GameElementManager elementManager =
                new GameElementManager(currentLevelBeingPlayedYayJavaNamingIsSoMuchFun);
    }

}
