package game_engine.visuals;

import java.util.Observer;
import game_engine.stateManaging.GameElementManager;
import javafx.scene.Group;
import javafx.scene.Scene;


public class VisualManager {
    private ScrollableScene scene;
    private ScrollableBackground background;

    public VisualManager (Group gameObjectVisuals, double screenWidth, double screenHeight) {
        scene = new ScrollableScene(gameObjectVisuals, screenWidth, screenHeight);
        background = scene.getBackground();
    }

    public Scene getScene () {
        return scene;
    }

    public void update () {
        scene.update();
    }

    public Group getVisualRepresentation () {
        return new Group(background.getChildren());
    }

    public void addBoxObserver (Observer o) {
        scene.addBoxObserver(o);
    }

    public void addClickObserver (Observer o) {
        scene.addClickObserver(o);
    }

    public void addObjects (Group g) {
        background.addObjects(g);
    }

    public ScrollableBackground getBackground () {
        return background;
    }

}
