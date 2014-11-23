package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.NumberEvaluator;

/**
 * A not equals evaluator
 *
 * @author Zach
 *
 */
public class NotEqualsEvaluator<A, B> extends Evaluator<A, B, Boolean>
		implements NumberEvaluator<Boolean> {
	public NotEqualsEvaluator(Evaluatable<A> parameter1,
			Evaluatable<B> parameter2) {
		super(Boolean.class, "!=", parameter1, parameter2);
	}

	@Override
	public Boolean evaluate(Object item1, Object item2) {
		return !item1.equals(item2);
	}

	@Override
	public Boolean evaluate(Double num1, Double num2) {
		return num1.doubleValue() != num2.doubleValue();
	}

}
