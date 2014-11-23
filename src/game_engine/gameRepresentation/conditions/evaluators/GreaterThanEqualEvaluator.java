package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;

/**
 * A greater than or equal to evaluator
 *
 * @author Zach
 *
 */
public class GreaterThanEqualEvaluator<A, B> extends Evaluator<A, B, Boolean> {

	public GreaterThanEqualEvaluator(Evaluatable<A> parameter1,
			Evaluatable<B> parameter2) {
		super(Boolean.class, ">=", parameter1, parameter2);

	}

	@Override
	protected Boolean evaluate(Double num1, Double num2) {
		return num1.doubleValue() >= num2.doubleValue();
	}

}
