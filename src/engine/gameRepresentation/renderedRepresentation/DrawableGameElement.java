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
import engine.visuals.Displayable;
import engine.visuals.elementVisuals.Visualizer;

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
	private Queue<Location> wayPoints;

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
		wayPoints = new LinkedList<>();
		myVisualizer.initializeDisplay();
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
		wayPoints.clear();
		wayPoints.addAll(waypointsToAdd);
		Location newGoal = wayPoints.poll();
		setGoalPositions(newGoal);
	}

	private void setGoalPositions(Location newGoal) {
		setNumericalAttribute(StateTags.X_GOAL_POSITION.getValue(),
				newGoal.getX());
		setNumericalAttribute(StateTags.Y_GOAL_POSITION.getValue(),
				newGoal.getY());
		setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION.getValue(),
				newGoal.getX());
		setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION.getValue(),
				newGoal.getY());
	}

	public void addWaypoint(double x, double y) {
		Location newWaypoint = new Location(x, y);
		wayPoints.add(newWaypoint);
	}

	public Location getNextWaypoint() {
		double currentX = getNumericalAttribute(StateTags.X_POSITION.getValue())
				.doubleValue();
		double currentY = getNumericalAttribute(StateTags.Y_POSITION.getValue())
				.doubleValue();
		Location current = new Location(currentX, currentY);
		return wayPoints.size() == 0 ? current : wayPoints.poll();
	}

	public List<Line> getLines() {
		List<Line> lines = new ArrayList<Line>();
		if (getNumericalAttribute(StateTags.IS_SELECTED.getValue()).intValue() == 1) {
			Queue<Location> copyWayPoints = new LinkedList<Location>();
			createLine(lines, Color.RED,
					getNumericalAttribute(StateTags.X_POSITION.getValue())
							.doubleValue(),
					getNumericalAttribute(StateTags.Y_POSITION.getValue())
							.doubleValue(),
					getNumericalAttribute(StateTags.X_GOAL_POSITION.getValue())
							.doubleValue(),
					getNumericalAttribute(StateTags.Y_GOAL_POSITION.getValue())
							.doubleValue());
			if (wayPoints.peek() != null) {
				createLine(
						lines,
						Color.BLACK,
						getNumericalAttribute(
								StateTags.X_GOAL_POSITION.getValue())
								.doubleValue(),
						getNumericalAttribute(
								StateTags.Y_GOAL_POSITION.getValue())
								.doubleValue(), wayPoints.peek().getX(),
						wayPoints.peek().getY());
			}
			while (wayPoints.peek() != null) {
				copyWayPoints.add(wayPoints.peek());
				Location P1 = wayPoints.poll();
				if (wayPoints.peek() != null) {
					Location P2 = wayPoints.peek();
					createLine(lines, Color.BLACK, P1.getX(), P1.getY(),
							P2.getX(), P2.getY());
				}
			}
			wayPoints = copyWayPoints;
		}
		return lines;
	}

	private void createLine(List<Line> lines, Color color, double firstX,
			double firstY, double lastX, double lastY) {
		Line line = new Line(firstX, firstY, lastX, lastY);
		line.getStrokeDashArray().addAll(25d, 10d);
		line.setStroke(color);
		lines.add(line);
	}

}
