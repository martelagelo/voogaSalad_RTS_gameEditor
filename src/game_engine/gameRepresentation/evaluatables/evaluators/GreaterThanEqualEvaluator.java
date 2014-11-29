package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * A greater than or equal to evaluator
 *
 * @author Zach
 *
 */
public class GreaterThanEqualEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public GreaterThanEqualEvaluator (String id, Evaluatable<A> parameter1,
                                      Evaluatable<B> parameter2) {
        super(Boolean.class, id, ">=", parameter1, parameter2);

    }

    @Override
    protected Boolean evaluate (Number num1, Number num2) {
        return num1.doubleValue() >= num2.doubleValue();
    }

}
