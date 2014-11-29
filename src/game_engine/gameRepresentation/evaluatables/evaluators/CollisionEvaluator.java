package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.StateTags;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Boundable;
import javafx.scene.shape.Polygon;


/**
 * An evaluator that returns true if two game elements are colliding
 * 
 * @author Zach
 */
public class CollisionEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public CollisionEvaluator (String id, Evaluatable<A> parameter1,
                               Evaluatable<B> parameter2) {
        super(Boolean.class, id, "collidesWith", parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (GameElement element1, GameElement element2) {
        if (element1 instanceof Boundable
            && element2 instanceof Boundable) {
            double[] element1Bounds = ((Boundable) element1)
                    .getBounds();
            double[] element2Bounds = ((Boundable) element2
                    ).getBounds();
            boolean collides = new Polygon(findGlobalBounds(element1Bounds,
                                                            element1))
                    .intersects(new Polygon(findGlobalBounds(element2Bounds,
                                                             element2))
                            .getBoundsInLocal());
            return collides;
        }
        return false;
    }

    /**
     * Take an object's bounds and add its x and y position to the bounds to get
     * the global object bounds
     */
    private double[] findGlobalBounds (double[] bounds, GameElement element) {
        double[] newBounds = bounds.clone();
        for (int i = 0; i < newBounds.length; i += 2) {
            newBounds[i] +=
                    element.getNumericAttribute(
                                                StateTags.X_POS_STRING)
                            .doubleValue();
            newBounds[i + 1] +=
                    element.getNumericAttribute(
                                                StateTags.Y_POS_STRING)
                            .doubleValue();
        }
        return newBounds;
    }
}
