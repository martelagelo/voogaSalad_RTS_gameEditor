package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;

/**
 * A less than evaluator returns a boolean indicating if one element is less
 * than the other.
 *
 * @author Zach
 *
 */
public class LessThan<A, B> extends Evaluator<A, B, Boolean> {

    public LessThan (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, "<", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Number num1, Number num2) {
        return num1.doubleValue() < num2.doubleValue();
    }

}
