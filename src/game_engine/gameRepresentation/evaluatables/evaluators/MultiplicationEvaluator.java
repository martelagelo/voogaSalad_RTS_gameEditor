package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * An evaluator that performs the multiplication (*) function. This returns a value and does not
 * affect the values of the evaluator's children.
 * 
 * @author Zach
 *
 */
public class MultiplicationEvaluator<A, B> extends Evaluator<A, B, Number> {

    public MultiplicationEvaluator (
                                    Evaluatable<A> parameter1,
                                    Evaluatable<B> parameter2) {
        super(Number.class, "*", parameter1, parameter2);
    }

    @Override
    public Number evaluate (Number num1, Number num2) {
        return num1.doubleValue() * num2.doubleValue();
    }

}
