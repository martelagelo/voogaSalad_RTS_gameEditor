package engine.visuals;

import java.util.List;
import javafx.scene.Group;
import engine.UI.ParticipantManager;
import engine.UI.RunnerInputManager;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;


/**
 * The class containing the visual IO and UI pieces for the player. Manages
 * retrieving and allowing observers to react to IO for clicking and key
 * presses, as well as scrolling the map
 * 
 * @author John, Michael D.
 *
 */
public class VisualManager {
    private StatisticsBox myStatisticsBox;
    private ScrollablePane scene;
    private ScrollableBackground background;
    private MiniMap myMiniMap;
    private Group root;
    private ParticipantManager myParticipantManager;

    /**
     * Creates a new VisualManager. One visual manager should be created for every Scene (map)
     * 
     * @param gameObjectVisuals the group for initial objects on the map. If no objects yet exist,
     *        add an empty new Group()
     * @param screenWidth the width of the screen to create
     * @param screenHeight the height of the screen to create
     */
    public VisualManager (Group gameObjectVisuals,
                          double screenWidth,
                          double screenHeight) {
        scene = new ScrollablePane(gameObjectVisuals, screenWidth, screenHeight);
        background = scene.getScrollingBackground();
        myMiniMap = new MiniMap(scene);
        scene.addToScene(new Group(myMiniMap.getDisplay()));
        root = gameObjectVisuals;
    }

    /**
     * Gets the background scene in order to display
     * 
     * @return the Scene object that represents the map
     */
    public ScrollablePane getScrollingScene () {
        return scene;
    }

    /**
     * updates the scene, should be called in each KeyFrame so that
     * the background can update appropriately with scrolling
     */
    public void update (List<SelectableGameElement> list) {
        // TODO: is there something better to pass here than just a list of the objects? maybe their
        // Point2D map locations? probably not since we also need the element's team color for the
        // map
        scene.update();
        myMiniMap.updateMiniMap(list);
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
     * Adds new objects to the map
     * 
     * @param g
     */
    public void addObject (Group g) {
        background.addObjects(g);
    }

    /**
     * Remove an object from the display
     * 
     * @param g the group of the object to remove
     */
    public void removeObject (Group g) {
        background.removeObjects(g);
    }

    /**
     * gets the ScrollableBackground that represents the map
     * 
     * @return the map's scrollablebackground representation
     */
    public ScrollableBackground getBackground () {
        return background;
    }

    public void attachInputManager (RunnerInputManager myInputManager) {
        scene.attachInputManager(myInputManager);
    }

    public void attachParticipantManager (ParticipantManager participantManager) {
        myParticipantManager = participantManager;
        // TODO: programmatically determine position
        myStatisticsBox = new StatisticsBox(30, 10, participantManager);
        scene.addToScene(new Group(myStatisticsBox));
    }
}
