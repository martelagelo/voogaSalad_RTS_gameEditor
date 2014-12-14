package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;

/**
 * An evaluator that implements the boolean AND functionality
 *
 * @author Zach
 */
public class And<A, B> extends Evaluator<A, B, Boolean> {

    public And (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, "&&", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Boolean param1, Boolean param2) {
        return param1 && param2;
    }

}
