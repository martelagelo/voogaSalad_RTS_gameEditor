package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * Return true if elements not colliding
 * 
 * @author Zach
 *
 */
public class NotCollision<A, B> extends Evaluator<A, B, Boolean> {

    private Evaluator<?, ?, ?> collisionEvaluator;

    public NotCollision (Evaluatable<A> parameter1,
                         Evaluatable<B> parameter2) {
        super(Boolean.class, "notCollision", parameter1, parameter2);
        collisionEvaluator = new Collision<>(parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (ElementPair elements) {
        return !((Boolean) collisionEvaluator.evaluate(elements));
    }

}
