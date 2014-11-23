package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.NumberEvaluator;

/**
 * A greater than or equal to evaluator
 *
 * @author Zach
 *
 */
public class GreaterThanEqualEvaluator<A, B> extends Evaluator<A, B, Boolean>
		implements NumberEvaluator<Boolean> {

	public GreaterThanEqualEvaluator(Evaluatable<A> parameter1,
			Evaluatable<B> parameter2) {
		super(Boolean.class, ">=", parameter1, parameter2);

	}

	@Override
	public Boolean evaluate(Double num1, Double num2) {
		return num1.doubleValue() >= num2.doubleValue();
	}

}
