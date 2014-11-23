package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.Boundable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import javafx.scene.shape.Polygon;

/**
 * An evaluator that returns true if two game elements are colliding
 * 
 * @author Zach
 */
public class CollisionEvaluator<A, B> extends Evaluator<A, B, Boolean> {

	public CollisionEvaluator(Evaluatable<A> parameter1,
			Evaluatable<B> parameter2) {
		super(Boolean.class, "collidesWith", parameter1, parameter2);
	}

	@Override
	public Boolean evaluate(GameElement element1, GameElement element2) {
		if (element1.getGameElementState() instanceof Boundable
				&& element2.getGameElementState() instanceof Boundable) {
			double[] element1Bounds = ((Boundable) element1
					.getGameElementState()).getBounds();
			double[] element2Bounds = ((Boundable) element2
					.getGameElementState()).getBounds();

			return new Polygon(findGlobalBounds(element1Bounds,
					element1.getGameElementState()))
					.intersects(new Polygon(findGlobalBounds(element2Bounds,
							element2.getGameElementState())).getBoundsInLocal());
		}
		return false;
	}

	/**
	 * Take an object's bounds and add its x and y position to the bounds to get
	 * the global object bounds
	 */
	private double[] findGlobalBounds(double[] bounds, GameElementState state) {
		for (int i = 0; i < bounds.length; i += 2) {
			bounds[i] += state.getNumericalAttribute(
					DrawableGameElementState.X_POS_STRING).doubleValue();
			bounds[i + 1] += state.getNumericalAttribute(
					DrawableGameElementState.Y_POS_STRING).doubleValue();
		}
		return bounds;
	}
}
