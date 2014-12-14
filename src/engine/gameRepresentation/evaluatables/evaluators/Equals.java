package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;

/**
 * An equals evaluator. Returns a boolean indicating if the two objects are
 * equal. Does no assignment to the parameters.
 *
 * @author Zach
 *
 */
public class Equals<A, B> extends Evaluator<A, B, Boolean> {

    public Equals (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, "==", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Object item1, Object item2) {
        return item1.equals(item2);
    }

    @Override
    protected Boolean evaluate (Number num1, Number num2) {
        return num1.equals(num2);
    }

}
