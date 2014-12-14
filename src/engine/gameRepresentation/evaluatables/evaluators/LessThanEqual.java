package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;

/**
 * A less than or equal to evaluator
 *
 * @author Zach
 *
 */
public class LessThanEqual<A, B> extends Evaluator<A, B, Boolean> {

    public LessThanEqual (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, "<=", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Number number1, Number number2) {
        return number1.doubleValue() <= number2.doubleValue();
    }

}
