// This entire file is part of my masterpiece.
// JOHN LORENZ

package engine.visuals;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import engine.UI.InputManager;
import engine.UI.ParticipantManager;
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
    
    public static final Shape SELECTION_SHAPE = new Rectangle();
    
    private ScrollablePane scene;
    private ScrollableBackground background;
    private ParticipantManager myParticipantManager;
    private List<VisualDisplay> myVisualDisplayElements;

    /**
     * Creates a new VisualManager. One visual manager should be created for
     * every Scene (map)
     *
     * @param gameObjectVisuals
     *            the group for initial objects on the map. If no objects yet
     *            exist, add an empty new Group()
     * @param fieldWidth
     *            the width of the map to create
     * @param fieldHeight
     *            the height of the map to create
     */
    public VisualManager (double fieldWidth, double fieldHeight, Image backgroundImage) {
        scene = new ScrollablePane(new Group(), SELECTION_SHAPE, fieldWidth, fieldHeight, backgroundImage);
        background = scene.getScrollingBackground();
        myVisualDisplayElements.add(new AbilityMatrix(scene.widthProperty(), scene.heightProperty()));
        myVisualDisplayElements.add(new MiniMap(scene));
        myVisualDisplayElements.add(new StatisticsBox(myParticipantManager));

        initializeVisuals();
    }

    private void initializeVisuals () {
        for(VisualDisplay v : myVisualDisplayElements){
            scene.addToScene(new Group(v.getNode()));
        }
    }

    /**
     * Gets the background scene in order to display
     *
     * @return the Scene object that represents the map
     */
    public ScrollablePane getScrollingScene () {
        return scene;
    }

    public void changeBackground (Image backgroundURI) {
        scene.changeBackground(backgroundURI);
    }

    /**
     * updates the scene, should be called in each KeyFrame so that the
     * background can update appropriately with scrolling
     */
    public void update (List<SelectableGameElement> list) {
        scene.update();
        for(VisualDisplay v : myVisualDisplayElements){
            v.update(list);
        }
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
     * @param g
     *            the group of the object to remove
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

    public void attachInputManager (InputManager inputManager) {
        scene.attachInputManager(inputManager);
    }

    public void attachParticipantManager (ParticipantManager participantManager) {
        myParticipantManager = participantManager;
    }
}
