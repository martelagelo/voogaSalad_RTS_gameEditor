package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * Perform subtraction and assignment (-=) on two numbers. Return a boolean indicating whether the
 * operation was successful
 * 
 * @author Zach
 *
 */
public class SubtractionAssignmentEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public SubtractionAssignmentEvaluator (
                                           Evaluatable<A> parameter1,
                                           Evaluatable<B> parameter2) {
        super(Boolean.class, "-=", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Number num1, Number num2) {
        return setParameter1Value(num1.doubleValue() - num2.doubleValue());
    }

}
