package application;

import game_engine.Engine;
import game_engine.gameRepresentation.renderedRepresentation.Game;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.inputManagers.InputEvent;
import game_engine.inputManagers.InputHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import player.ScrollableScene;
import player.VisualManager;


public class ShittyMain extends Application {
    @Override
    public void start (Stage primaryStage) {
        Group g = new Group();
        
        VisualManager manager = new VisualManager(g, 600, 600);
        Object saveLoadUtility = new Object(); // loololol
        
        Game game = hardCodeAGame();
        Engine engine = new Engine(game,saveLoadUtility, manager);
        
    }

    private Game hardCodeAGame () {
        SelectableGameElementState archer = new SelectableGameElementState(10,10);
        
        return null;
    }

    public static void main (String[] args) {
        launch(args);
    }

    public void shittyRun (Group g) {
        System.out.println("Shitty running");
        Image poop = new Image("resources/img/poop.png");
        g.getChildren().add(new ImageView(poop));


        // Add the input handler to the group
        InputHandler handler = new InputHandler(g);
        InputEvent<MouseEvent, Group> keyPressedInput =
                new InputEvent<MouseEvent, Group>(MouseEvent.MOUSE_CLICKED, g, group -> {
                    group.getChildren().get(0)
                            .setRotate(group.getChildren().get(0).getRotate() + 20);
                    System.out.println("Clicked");
                });
    }

}
