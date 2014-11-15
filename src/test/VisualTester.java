package test;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * A basic visual tester. Sets up a scene and allows the implementation of visuals by implementing
 * the getNode() method
 *
 * @author Zach
 */
public class VisualTester extends Application {

    @Override
    public void start (Stage primaryStage) {
        Parent display = getDisplay();
        Bounds displayBounds = display.getBoundsInParent();
        Scene scene = new Scene(display, displayBounds.getWidth(), displayBounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }

    protected Group getDisplay () {
        return new Group();
    }

}
