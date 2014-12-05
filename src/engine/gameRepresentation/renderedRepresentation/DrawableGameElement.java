package engine.gameRepresentation.renderedRepresentation;

import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.StateTags;
import model.state.gameelement.traits.Boundable;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerFactory;
import engine.visuals.Displayable;
import engine.visuals.elementVisuals.Visualizer;
import engine.visuals.elementVisuals.widgets.attributeDisplays.AttributeBarDisplayer;
import engine.visuals.elementVisuals.widgets.attributeDisplays.AttributeDisplayer;


/**
 * A game element that is capable of being drawn. Combines a game element state
 * as well as an animation player. Essentially wraps a savable game element
 * state with a drawable wrapper to avoid JavaFX serialization problems.
 * 
 * @author Zach, Jonathan, Nishad, Rahul, John L., Michael D., Stanley
 *
 */
public class DrawableGameElement extends GameElement implements Displayable, Boundable {

	private AttributeDisplayerFactory myWidgetFactory;
    private DrawableGameElementState drawableState;
    private Visualizer myVisualizer;

    /**
     * Create a drawable game element from the given state
     * 
     * @param state
     *        the state of the drawable element
     * @param actions
     *        the actions map for the game element (Created by a factory to
     *        be passed into the element)
     */
    public DrawableGameElement (DrawableGameElementState state,
                                ResourceBundle actionTypes, Visualizer visualizer) {
        super(state, actionTypes);
        drawableState = state;
        myVisualizer = visualizer;
        myWidgetFactory = new AttributeDisplayerFactory();

        /*
         * TODO: if this should be removed, where would you rather put it? The state needs to
         * be initialized somewhere and DrawableGameElement is the lowest level that both has a
         * visual
         * component and actually has access to its own position
         */
        this.initializeDisplay();

        // TODO: remove, this is for testing only
        AttributeDisplayer xPosBar =
                new AttributeBarDisplayer(drawableState.attributes, StateTags.HEALTH, 0, 500);
        myVisualizer.addWidget(xPosBar);
        // AttributeDisplayer selectionTriangle = new
        // UnitSelectedDisplayer(drawableState.attributes, StateTags.IS_SELECTED, 0, 1);
        // myVisualizer.addWidget(selectionTriangle);
    }

    
    // TODO: add to visualizer
    /**
     * Update the drawable game element and its visual appearance.
     */
    @Override
    public void update () {
        super.update();        
        String teamColor = getTextualAttribute(StateTags.TEAM_COLOR);
        //System.out.println("Updating drawable game element: " + teamColor);
        myVisualizer.update();
        // Make sure the element's visual aspects match its underlying state
        myVisualizer.getNode()
                .setLayoutX(getNumericalAttribute(StateTags.X_POSITION).doubleValue());
        myVisualizer.getNode()
                .setLayoutY(getNumericalAttribute(StateTags.Y_POSITION).doubleValue());

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

    // TOOD: move to visualizer
    public Point2D getPosition () {
        return new Point2D(getNode().getLayoutX(), getNode().getLayoutY());
    }
    
    public void registerAsDrawableChild(Consumer<DrawableGameElementState> function){
    	function.accept(drawableState);
    }

    // TODO: move to visualizer and give visualizer attributes
    /**
     * Initializes the display for each game element
     */
    private void initializeDisplay () {
        myVisualizer.getNode()
                .setLayoutX(getNumericalAttribute(StateTags.X_POSITION).doubleValue());
        myVisualizer.getNode()
                .setLayoutY(getNumericalAttribute(StateTags.Y_POSITION).doubleValue());
        myVisualizer.getNode().setTranslateX(-drawableState.myAnimatorState.getViewportSize()
                .getWidth() / 2);
        myVisualizer.getNode().setTranslateY(-drawableState.myAnimatorState.getViewportSize()
                .getHeight() / 2);
    }

    @Override
    public double[] findGlobalBounds () {
        return drawableState.findGlobalBounds();
    }
    
}
