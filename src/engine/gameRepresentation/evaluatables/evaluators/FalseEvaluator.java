package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;

/**
 * A class that follows the null pattern to make an evaluator that does nothing
 * and returns false. Used in the case of handling improper parameters for an
 * action
 *
 * @author Zach
 *
 */
public class FalseEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public FalseEvaluator () {
        super(Boolean.class, "False", null, null);
    }

    /**
     * A stub creator used for factories in the case of failures
     */
    public FalseEvaluator (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        this();
    }

    @Override
    public Boolean evaluate (ElementPair elements) {
        return false;
    }

}
