package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;

/**
 * Implements the OR functionality on two parameters
 *
 * @author Zach
 */
public class Or<A, B> extends Evaluator<A, B, Boolean> {

    public Or (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, "||", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Boolean param1, Boolean param2) {
        return param1 || param2;
    }

}
