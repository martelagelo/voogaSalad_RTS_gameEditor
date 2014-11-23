package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.NumberEvaluator;


/**
 * An equals evaluator
 * 
 * @author Zach
 *
 */
public class EqualsEvaluator<A,B> extends Evaluator<A,B,Boolean> implements NumberEvaluator<Boolean> {

    public EqualsEvaluator (String evaluatorRepresentation,
			Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class,"=", parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (Object item1, Object item2) {
        return item1.equals(item2);
    }

	@Override
	public Boolean evaluate(Double num1, Double num2) {
		return num1 == num2;
	}

}
