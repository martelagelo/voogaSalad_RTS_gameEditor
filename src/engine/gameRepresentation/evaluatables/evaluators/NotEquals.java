package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;

/**
 * A not equals evaluator returns a boolean indicating if two parameters are not
 * equal.
 *
 * @author Zach
 *
 */
public class NotEquals<A, B> extends Evaluator<A, B, Boolean> {
    public NotEquals (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, "!=", parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (Object item1, Object item2) {
        return !item1.equals(item2);
    }

    @Override
    protected Boolean evaluate (Number num1, Number num2) {
        return num1.doubleValue() != num2.doubleValue();
    }

}
