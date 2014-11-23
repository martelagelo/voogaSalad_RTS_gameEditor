package game_engine.visuals;

import java.util.Observer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;


/**
 * The class containing the visual IO and UI pieces for the player. Manages retrieving and
 * allowing observers to react to IO for clicking and key presses, as well as scrolling the map
 * 
 * @author John, Michael D.
 *
 */
public class VisualManager {
    private ScrollableScene scene;
    private ScrollableBackground background;

    /**
     * Creates a new VisualManager. One visual manager should be created for every Scene (map)
     * 
     * @param gameObjectVisuals the group for initial objects on the map. If no objects yet exist,
     *        add an empty new Group()
     * @param screenWidth the width of the screen to create
     * @param screenHeight the height of the screen to create
     */
    public VisualManager (Group gameObjectVisuals, double screenWidth, double screenHeight) {
        scene = new ScrollableScene(gameObjectVisuals, screenWidth, screenHeight);
        background = scene.getBackground();
    }

    /**
     * Gets the background scene in order to display
     * 
     * @return the Scene object that represents the map
     */
    public Scene getScene () {
        return scene;
    }

    /**
     * updates the scene, should be called in each KeyFrame so that
     * the background can update appropriately with scrolling
     */
    public void update () {
        scene.update();
    }

    /**
     * Gets the group containing all elements added to the map
     * 
     * @return the group containing all nodes that have been added to the map.
     *         These will only have the JavaFX nodes, not all the states
     */
    public Group getVisualRepresentation () {
        // TODO: containing method currently unused, possibly delete
        return new Group(background.getChildren());
    }

    /**
     * Adds a new observer to any boxes drawn by this visual manager instance
     * 
     * @param o
     */
    public void addBoxObserver (Observer o) {
        scene.addBoxObserver(o);
    }

    /**
     * Adds a new observer to any clicks made on this map by this visual manager instance
     * 
     * @param o
     */
    public void addClickObserver (Observer o) {
        scene.addClickObserver(o);
    }

    /**
     * Adds a new observer to any key presses made on this map by this visual manager instance
     * 
     * @param o
     */
    public void addKeyboardObserver (Observer o) {
        scene.addKeyboardObserver(o);
    }

    /**
     * Adds new objects to the map
     * 
     * @param g
     */
    public void addObjects (Group g) {
        background.addObjects(g);
    }

    public void addMiniMap (Canvas c) {
        scene.addObjects(c);
    }

    /**
     * gets the ScrollableBackground that represents the map
     * 
     * @return the map's scrollablebackground representation
     */
    public ScrollableBackground getBackground () {
        return background;
    }

}
