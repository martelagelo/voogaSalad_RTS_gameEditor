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


public class ShittyMain extends Application {
    @Override
    public void start (Stage primaryStage) {
        Group g = new Group();

        Group gameObjects = new Group();

        ScrollableScene scrollingScene = new ScrollableScene(g, 600, 600);
        scrollingScene.addObjects(gameObjects);

        Duration oneFrameAmt = Duration.millis(1000 / 60);
//        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
//                                               new EventHandler<ActionEvent>() {
//                                                   @Override
//                                                   public void handle (ActionEvent event) {
//                                                       scrollingScene.update();
//                                                   }
//                                               });
//
//        Timeline timeline = new Timeline();
//
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.getKeyFrames().clear();
//        timeline.getKeyFrames().add(oneFrame);
//        timeline.playFromStart();
        
        Object saveLoadUtility = new Object(); // loololol
        
        Game game = hardCodeAGame();
        Engine engine = new Engine(game,saveLoadUtility);


        primaryStage.setScene(scrollingScene);
        shittyRun(gameObjects);
        primaryStage.show();
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
