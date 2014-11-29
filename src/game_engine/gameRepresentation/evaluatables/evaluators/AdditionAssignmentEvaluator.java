package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;


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
public class AdditionAssignmentEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public AdditionAssignmentEvaluator (String id, Evaluatable<A> parameter1,
                                        Evaluatable<B> parameter2) {
        super(Boolean.class, id, "+=", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Number num1, Number num2) {
        return setParameter1Value(num1.doubleValue() + num2.doubleValue());
    }

}
