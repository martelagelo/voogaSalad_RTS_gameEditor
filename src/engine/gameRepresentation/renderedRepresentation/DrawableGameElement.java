package engine.gameRepresentation.renderedRepresentation;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.StateTags;
import model.state.gameelement.traits.Boundable;
import engine.computers.pathingComputers.Location;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerFactory;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerState;
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

    // TODO: remove this factory ...
    private AttributeDisplayerFactory myWidgetFactory;

    private DrawableGameElementState drawableState;
    private Visualizer myVisualizer;
    private Queue<Location> waypoints;
    private AttributeDisplayerState myAttributeDisplayerState;

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
        waypoints = new LinkedList<>();
        myVisualizer.initializeDisplay();

        // TODO: remove, this is for testing only
        AttributeDisplayer healthBar =
                new AttributeBarDisplayer(drawableState.attributes, StateTags.HEALTH, 0, 500);
        myVisualizer.addWidget(healthBar);

        myAttributeDisplayerState =
                new AttributeDisplayerState("attributeBar", StateTags.HEALTH, 0, 500);
    }

    @Override
    public void update () {
        super.update();
        myVisualizer.update();
    }

    @Override
    public Node getNode () {
        return myVisualizer.getNode();
    }

    public double[] getBounds () {
        return drawableState.getBounds();
    }

    public Point2D getPosition () {
        return myVisualizer.getNodeLocation();
    }

    public void registerAsDrawableChild (Consumer<DrawableGameElementState> function) {
        function.accept(drawableState);
    }

    @Override
    public double[] findGlobalBounds () {
        return drawableState.findGlobalBounds();
    }

    public void setWaypoints (List<Location> waypointsToAdd) {
        waypoints.clear();
        waypoints.addAll(waypointsToAdd);
        Location newGoal = waypoints.poll();
        //TODO make this into a method
        setNumericalAttribute(StateTags.X_GOAL_POSITION,newGoal.myX);
        setNumericalAttribute(StateTags.X_GOAL_POSITION,newGoal.myY);
        setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION,newGoal.myX);
        setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION,newGoal.myY);
        
    }

    public void addWaypoint (double x, double y) {
        Location newWaypoint = new Location(x, y);
        waypoints.add(newWaypoint);
    }

    public Location getNextWaypoint () {
        double currentX = getNumericalAttribute(StateTags.X_POSITION).doubleValue();
        double currentY = getNumericalAttribute(StateTags.Y_POSITION).doubleValue();
        Location current = new Location(currentX, currentY);
        return waypoints.size() == 0 ? current : waypoints.poll();
    }
}
