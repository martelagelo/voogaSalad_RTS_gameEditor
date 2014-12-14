package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;

/**
 * Perform subtraction but not assignment (-) on two numbers. Return the result
 * of the subtraction
 *
 * @author Zach
 *
 */
public class Subtraction<A, B> extends Evaluator<A, B, Number> {

    public Subtraction (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Number.class, "-", parameter1, parameter2);
    }

    @Override
    protected Number evaluate (Number num1, Number num2) {
        return num1.doubleValue() - num2.doubleValue();
    }

}
