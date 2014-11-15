package test.animations;

import game_engine.visuals.AnimationSequence;
import game_engine.visuals.AnimationPlayer;
import game_engine.visuals.Dimension;
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
import javafx.scene.image.ImageView;
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
        Scene scene = new Scene(display, bounds.getWidth(),bounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }

    protected Group getDisplay () {
        Group group = new Group();
        Image image = new Image("resources/img/exploBig.png");
        //ImageView imageView = new ImageView(image);
        AnimationPlayer player = new AnimationPlayer(image,new Dimension(40,40),5);
        AnimationSequence animation = new AnimationSequence("exprode",0,6,true);
        player.setAnimation(animation);
        Duration oneFrameAmt = Duration.millis(1000);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
                                               new EventHandler<ActionEvent>() {
                                                   @Override
                                                   public void handle (ActionEvent event) {
                                                       System.out.println(player.update());
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
