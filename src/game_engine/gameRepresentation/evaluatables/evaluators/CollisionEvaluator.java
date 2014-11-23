package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.Boundable;
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
			return new Polygon(
					((Boundable) element1.getGameElementState()).getBounds())
					.intersects(new Polygon(((Boundable) element2
							.getGameElementState()).getBounds())
							.getBoundsInLocal());
		}
		return false;
	}

}
