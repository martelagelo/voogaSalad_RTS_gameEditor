package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * An evaluator that performs the multiplication assignment (*=) function. This returns a value and
 * does not
 * affect the values of the evaluator's children.
 * 
 * @author Zach
 *
 */
public class MultiplicationAssignment<A, B> extends Evaluator<A, B, Number> {

    public MultiplicationAssignment (String id,
                                     Evaluatable<A> parameter1,
                                     Evaluatable<B> parameter2) {
        super(Number.class, id, "*=", parameter1, parameter2);
    }

    @Override
    public Number evaluate (Number num1, Number num2) {
        setParameter1Value(num1.doubleValue() * num2.doubleValue());
        return num1.doubleValue() * num2.doubleValue();
    }
}
