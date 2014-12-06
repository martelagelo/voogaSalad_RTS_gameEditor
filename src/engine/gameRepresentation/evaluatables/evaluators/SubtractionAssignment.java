package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * Perform subtraction and assignment (-=) on two numbers. Return a boolean indicating whether the
 * operation was successful
 * 
 * @author Zach
 *
 */
public class SubtractionAssignment<A, B> extends Evaluator<A, B, Number> {

    public SubtractionAssignment (String id,
                                  Evaluatable<A> parameter1,
                                  Evaluatable<B> parameter2) {
        super(Number.class, id, "-=", parameter1, parameter2);
    }

    @Override
    protected Number evaluate (Number num1, Number num2) {
        double newValue = num1.doubleValue() - num2.doubleValue();
        setParameter1Value(newValue);
        return newValue;
    }

}
