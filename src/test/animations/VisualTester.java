package test.animations;

import game_engine.visuals.Dimension;
import game_engine.visuals.elementVisuals.Animator;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * A basic visual tester. Sets up a scene and allows the implementation of visuals by implementing
 * the getNode() method
 *
 * @author Zach
 */
public class VisualTester extends Application {

    @Override
    public void start (Stage primaryStage) {
        Group display = getDisplay();
        Bounds bounds = display.getLayoutBounds();
        Scene scene = new Scene(display, bounds.getWidth(), bounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }

    protected Group getDisplay () {
        Group group = new Group();
        Image image = new Image("resources/img/exploBig.png");
        Animator player = new Animator(image, new Dimension(40, 40), 7);
        //TODO fix animation sequence now takes an animationtag object instead of a string tag
        //AnimationSequence animation = new AnimationSequence("explode", 0, 13, true, 0.01);
        //player.setAnimation(animation);
        Duration oneFrameAmt = Duration.millis(1000 / 60);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
                                               new EventHandler<ActionEvent>() {
                                                   @Override
                                                   public void handle (ActionEvent event) {
                                                       player.update();
                                                   }
                                               });

        Timeline timeline = new Timeline();

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(oneFrame);
        timeline.playFromStart();

        group.getChildren().add(player.getNode());

        return group;
    }

}
