package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * Perform addition on two numbers. Return a boolean indicating whether the
 * addition was successful
 * 
 * @author Zach
 *
 * @param <A>
 *        the first number
 * @param <B>
 *        the second number
 */
public class Addition<A, B> extends Evaluator<A, B, Number> {

    public Addition (String id, Evaluatable<A> parameter1,
                                        Evaluatable<B> parameter2) {
        super(Number.class, id, "+", parameter1, parameter2);
    }

    @Override
    protected Number evaluate (Number num1, Number num2) {
        return num1.doubleValue() + num2.doubleValue();
    }

}
