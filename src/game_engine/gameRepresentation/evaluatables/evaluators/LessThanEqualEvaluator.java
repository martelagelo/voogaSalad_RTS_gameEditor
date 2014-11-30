package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * A less than or equal to evaluator
 * 
 * @author Zach
 *
 */
public class LessThanEqualEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public LessThanEqualEvaluator (String id, Evaluatable<A> parameter1,
                                   Evaluatable<B> parameter2) {
        super(Boolean.class, id, "<=", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Number number1, Number number2) {
        return number1.doubleValue() <= number2.doubleValue();
    }

}
