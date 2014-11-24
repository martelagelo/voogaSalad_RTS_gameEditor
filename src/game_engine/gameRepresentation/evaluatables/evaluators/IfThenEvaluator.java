package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * An evaluator that evaluates the second parameter only if the first parameter is true. Returns a
 * boolean indicating if the second parameter was executed.
 * 
 * @author Zach
 *
 * @param <A> the return type of the first parameter
 * @param <B> the return type of the second parameter
 */
public class IfThenEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public IfThenEvaluator (Evaluatable<A> parameter1,
                            Evaluatable<B> parameter2) {
        super(Boolean.class, "IFTHEN", parameter1, parameter2);
    }

    /**
     * Evaluate the evaluator on the element pair. Evaluate the first condition and only evaluate
     * the second if the first is true.
     *
     * @param elements
     *        a pairing of the current element and the element that is being
     *        examined by it
     * @return a boolean indicating if the second parameter was executed
     */
    @Override
    public Boolean getValue (ElementPair elements) {
        if (Boolean.class.equals(getParameter1().getType()) &&
            (Boolean) getParameter1().getValue(elements))
        {
            getParameter2().getValue(elements);
            return true;
        }
        return false;
    }

}
