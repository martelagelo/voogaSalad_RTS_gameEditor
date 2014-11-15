package application;

import visualComponents.ScrollableScene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.inputManagers.InputEvent;
import game_engine.inputManagers.InputHandler;
import game_engine.stateManaging.GameElementManager;
import game_engine.stateManaging.GameLoop;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ShittyMain extends Application {
    @Override
    public void start (Stage primaryStage) {
        Group g = new Group();

        Group gameObjects = new Group();

        ScrollableScene scrollingScene = new ScrollableScene(g, 600, 600);
        scrollingScene.addObjects(gameObjects);

        Duration oneFrameAmt = Duration.millis(1000 / 60);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
                                               new EventHandler<ActionEvent>() {
                                                   @Override
                                                   public void handle (ActionEvent event) {
                                                       scrollingScene.update();
                                                   }
                                               });

        Timeline timeline = new Timeline();

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(oneFrame);
        timeline.playFromStart();

        primaryStage.setScene(scrollingScene);
        shittyRun(gameObjects);
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }

    public void shittyRun (Group g) {
        System.out.println("Shitty running");
        Image poop = new Image("resources/img/poop.png");
        g.getChildren().add(new ImageView(poop));

        LevelState currentLevelBeingPlayedYayJavaNamingIsSoMuchFun = new LevelState();
        new GameLoop(currentLevelBeingPlayedYayJavaNamingIsSoMuchFun);
        new GameElementManager(currentLevelBeingPlayedYayJavaNamingIsSoMuchFun);

        // Add the input handler to the group
        InputHandler handler = new InputHandler(g);
        InputEvent<MouseEvent, Group> keyPressedInput =
                new InputEvent<MouseEvent, Group>(MouseEvent.MOUSE_CLICKED, g, group -> {
                    group.getChildren().get(0)
                            .setRotate(group.getChildren().get(0).getRotate() + 20);
                    System.out.println("Clicked");
                });
        GameLoop gameLoop = new GameLoop(
                                         currentLevelBeingPlayedYayJavaNamingIsSoMuchFun);
        GameElementManager elementManager =
                new GameElementManager(
                                       currentLevelBeingPlayedYayJavaNamingIsSoMuchFun);

    }

}
