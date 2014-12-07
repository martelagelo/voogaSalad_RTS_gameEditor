package engine.gameRepresentation.renderedRepresentation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
public class DrawableGameElement extends GameElement implements Displayable,
		Boundable {

	private DrawableGameElementState drawableState;
	private Visualizer myVisualizer;
	private Queue<Location> currentWayPoints;
	private Queue<Location> currentPath;

	/**
	 * Create a drawable game element from the given state
	 * 
	 * @param state
	 *            the state of the drawable element
	 * @param actions
	 *            the actions map for the game element (Created by a factory to
	 *            be passed into the element)
	 */
	public DrawableGameElement(DrawableGameElementState state,
			Visualizer visualizer) {
		super(state);
		drawableState = state;
		myVisualizer = visualizer;
		currentWayPoints = new LinkedList<>();
		currentPath = new LinkedList<>();
		myVisualizer.initializeDisplay();

		// myAttributeDisplayerState =
		// new AttributeDisplayerState("attributeBar", StateTags.HEALTH, 0,
		// 500);
		// myVisualizer.addWidget(myWidgetFactory.createAttributeDisplayer(this.myAttributeDisplayerState,
		// drawableState.attributes));
	}

	@Override
	public void update() {
		super.update();
		myVisualizer.update();
	}

	@Override
	public Node getNode() {
		return myVisualizer.getNode();
	}

	public double[] getBounds() {
		return drawableState.getBounds();
	}

	public Point2D getPosition() {
		return myVisualizer.getNodeLocation();
	}

	public void registerAsDrawableChild(
			Consumer<DrawableGameElementState> function) {
		function.accept(drawableState);
	}

	@Override
	public double[] findGlobalBounds() {
		return drawableState.findGlobalBounds();
	}

	public void setWaypoints(List<Location> waypointsToAdd) {
		currentWayPoints.clear();
		currentWayPoints.addAll(waypointsToAdd);
		Location newGoal = currentWayPoints.poll();
		// TODO make this into a method
		setNumericalAttribute(StateTags.X_GOAL_POSITION, newGoal.myX);
		setNumericalAttribute(StateTags.Y_GOAL_POSITION, newGoal.myY);
		setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION, newGoal.myX);
		setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION, newGoal.myY);

	}

	public void addWaypoint(double x, double y) {
		Location newWaypoint = new Location(x, y);
		currentWayPoints.add(newWaypoint);
	}

	public Location getNextWaypoint() {
		double currentX = getNumericalAttribute(StateTags.X_POSITION)
				.doubleValue();
		double currentY = getNumericalAttribute(StateTags.Y_POSITION)
				.doubleValue();
		Location current = new Location(currentX, currentY);
		return currentWayPoints.size() == 0 ? current : currentWayPoints.poll();
	}

	public List<Line> getLines() {
		List<Line> lines = new ArrayList<Line>();
		if (getNumericalAttribute(StateTags.IS_SELECTED).intValue() == 1) {
			// Queue<Location> copyWayPoints = new LinkedList<Location>();
			Line line = new Line(getNumericalAttribute(StateTags.X_POSITION)
					.doubleValue(), getNumericalAttribute(StateTags.Y_POSITION)
					.doubleValue(), getNumericalAttribute(
					StateTags.X_GOAL_POSITION).doubleValue(),
					getNumericalAttribute(StateTags.Y_GOAL_POSITION)
							.doubleValue());
			
			line.getStrokeDashArray().addAll(25d, 10d);
			line.setStroke(Color.RED);
			lines.add(line);
			
			// while (waypoints.peek() != null) {
			// copyWayPoints.add(waypoints.peek());
			// Location P1 = waypoints.poll();
			// if (waypoints.peek() != null) {
			// Location P2 = waypoints.peek();
			// Line l = new Line(P1.myX, P2.myY, P2.myX,
			// P2.myY);
			// l.getStrokeDashArray().addAll(25d, 10d);
			// l.setStroke(Color.GREEN);
			// lines.add(l);
			// }
			// }
			// //waypoints.clear();
			// waypoints = copyWayPoints;
		}
		return lines;
	}
	
	public void addToPath(Location e) {
		currentPath.add(e);
	}
	
	public void clearPaths() {
		currentPath.clear();
	}
}
