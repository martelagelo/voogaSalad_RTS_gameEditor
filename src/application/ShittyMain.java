package application;

import game_engine.gameRepresentation.SelectableGameElement;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import visualComponents.ScrollableScene;


public class ShittyMain extends Application {
    @Override
    public void start (Stage primaryStage) {
        Group g = new Group();
        
        Group gameObjects = new Group();
        
        SelectableGameElement el = new SelectableGameElement(new Image("resources/img/EnemyCombat_Right.png"), new Point2D(0,0), "unit1");
        gameObjects.getChildren().add(el.getVisibleRepresentation());
        
        
        
        ScrollableScene scrollingScene = new ScrollableScene(g, 600, 600);
        scrollingScene.addObjects(gameObjects);
        
        Duration oneFrameAmt = Duration.millis(1000/60);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
                                               new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
        //g.getChildren().add(new ImageView(poop));
    }

}
