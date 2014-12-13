package engine.gameRepresentation.evaluatables.evaluators;

import javafx.scene.shape.Polygon;
import model.state.gameelement.traits.Boundable;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * An evaluator that returns true if two game elements are colliding
 * 
 * @author Zach
 */
public class Collision<A, B> extends Evaluator<A, B, Boolean> {

    public Collision (Evaluatable<A> parameter1,
                      Evaluatable<B> parameter2) {
        super(Boolean.class, "collidesWith", parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (GameElement element1, GameElement element2) {
        if (element1 instanceof Boundable
            && element2 instanceof Boundable) {
            double[] element1Bounds = ((Boundable) element1)
                    .findGlobalBounds();
            double[] element2Bounds = ((Boundable) element2
                    ).findGlobalBounds();
            boolean collides = new Polygon(element1Bounds)
                    .intersects(new Polygon(element2Bounds)
                            .getBoundsInLocal());
            return collides;
        }
        return false;
    }
}
