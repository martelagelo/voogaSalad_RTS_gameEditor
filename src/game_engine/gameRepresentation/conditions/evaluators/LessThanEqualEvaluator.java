package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;

/**
 * A less than or equal to evaluator
 * 
 * @author Zach
 *
 */
public class LessThanEqualEvaluator<A, B> extends Evaluator<A, B, Boolean> {

	public LessThanEqualEvaluator(Evaluatable<A> parameter1,
			Evaluatable<B> parameter2) {
		super(Boolean.class, "<=", parameter1, parameter2);
	}

	@Override
	protected Boolean evaluate(Double number1, Double number2) {
		return number1.doubleValue() <= number2.doubleValue();
	}

}
