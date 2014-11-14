package application;

import game_engine.gameRepresentation.Level;
import game_engine.inputManagers.InputEvent;
import game_engine.inputManagers.InputHandler;
import game_engine.stateManaging.GameElementManager;
import game_engine.stateManaging.GameLoop;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ShittyMain extends Application {
    @Override
    public void start (Stage primaryStage) {
        Group g = new Group();
        g.minHeight(900);
        g.minWidth(900);
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
        new GameLoop(currentLevelBeingPlayedYayJavaNamingIsSoMuchFun);
        new GameElementManager(currentLevelBeingPlayedYayJavaNamingIsSoMuchFun);

        // Add the input handler to the group
        InputHandler handler = new InputHandler(g);
        InputEvent<MouseEvent, Group> keyPressedInput =
                new InputEvent<MouseEvent, Group>(MouseEvent.MOUSE_CLICKED,g, group -> {
                    group.getChildren().get(0).setRotate(group.getChildren().get(0).getRotate()+20);
                    System.out.println("Clicked");
                });
        /
        handler.addEventHandler(keyPressedInput);
    }

}
