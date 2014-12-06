package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * Perform subtraction (-) on two numbers. Return the result of the subtraction
 * 
 * @author Zach
 *
 */
public class Subtraction<A, B> extends Evaluator<A, B, Number> {

    public Subtraction (String id,
                        Evaluatable<A> parameter1,
                        Evaluatable<B> parameter2) {
        super(Number.class, id, "-", parameter1, parameter2);
    }

    @Override
    protected Number evaluate (Number num1, Number num2) {
        return num1.doubleValue() - num2.doubleValue();
    }

}
