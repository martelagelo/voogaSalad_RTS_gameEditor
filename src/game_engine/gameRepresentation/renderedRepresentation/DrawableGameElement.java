package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.StateTags;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Boundable;
import game_engine.visuals.Displayable;
import game_engine.visuals.elementVisuals.Visualizer;
import game_engine.visuals.elementVisuals.animations.AnimationSequence;
import game_engine.visuals.elementVisuals.animations.NullAnimationSequence;
import game_engine.visuals.elementVisuals.widgets.attributeDisplays.AttributeBarDisplayer;
import game_engine.visuals.elementVisuals.widgets.attributeDisplays.AttributeDisplayer;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * A game element that is capable of being drawn. Combines a game element state
 * as well as an animation player. Essentially wraps a savable game element
 * state with a drawable wrapper to avoid JavaFX serialization problems.
 * 
 * @author Zach, Jonathan, Nishad, Rahul, John L., Michael D.
 *
 */
public class DrawableGameElement extends GameElement implements Displayable, Boundable {

    private DrawableGameElementState drawableState;
    private Visualizer myVisualizer;


    /**
     * Create a drawable game element from the given state
     * 
     * @param state
     *            the state of the drawable element
     * @param actions
     *            the actions map for the game element (Created by a factory to
     *            be passed into the element)
     */
    public DrawableGameElement (DrawableGameElementState state,
            Map<String, List<Evaluatable<?>>> actions, ResourceBundle actionTypes, Visualizer visualizer) {
        super(state, actions, actionTypes);
        drawableState = state;
        myVisualizer = visualizer;

        // TODO: remove, this is for testing only
        AttributeDisplayer xPosBar = new AttributeBarDisplayer(drawableState.attributes, StateTags.X_POSITION, 0, 500);
        myVisualizer.addWidget(xPosBar);
    }

    /**
     * Update the drawable game element and its visual appearance.
     */
    @Override
    public void update () {
        super.update();
        myVisualizer.update();
    }

    /**
     * Update the element's image location based on its x and y position
     */
    protected void updateImageLocation () {
        myVisualizer.getNode().setLayoutX(getNumericalAttribute(StateTags.X_POSITION).doubleValue());
        myVisualizer.getNode().setLayoutY(getNumericalAttribute(StateTags.Y_POSITION).doubleValue());
    }


    /**
     * Gets the node that is being displayed on the scene
     * 
     * @return The game element
     */
    @Override
    public Node getNode () {
        return myVisualizer.getNode();
    }

    /**
     * Get the bound array of the object
     * 
     * @return the object's bounds
     */
    public double[] getBounds () {
        return drawableState.getBounds();
    }

    // TODO from here down, remove this crap
    // TODO: Fix. Move logic into group

    public Point2D getLocation () {
        // TODO move positions and fix
        Point2D p = new Point2D(getNode().getLayoutX(), getNode().getLayoutY());
        return p;
    }

    /**
     * Initializes the display for each game element
     */
    private void initializeDisplay () {
        myVisualizer.getNode().setLayoutX(getNumericalAttribute(StateTags.X_POSITION).doubleValue());
        myVisualizer.getNode().setLayoutY(getNumericalAttribute(StateTags.Y_POSITION).doubleValue());
        myVisualizer.getNode().setTranslateX(-drawableState.myAnimatorState.getViewportSize().getWidth() / 2);
        myVisualizer.getNode().setTranslateY(-drawableState.myAnimatorState.getViewportSize().getHeight() / 2);
    }
}
