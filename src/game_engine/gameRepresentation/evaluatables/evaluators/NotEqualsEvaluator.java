package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;

/**
 * A not equals evaluator
 *
 * @author Zach
 *
 */
public class NotEqualsEvaluator<A, B> extends Evaluator<A, B, Boolean> {
	public NotEqualsEvaluator(Evaluatable<A> parameter1,
			Evaluatable<B> parameter2) {
		super(Boolean.class, "!=", parameter1, parameter2);
	}

	@Override
	public Boolean evaluate(Object item1, Object item2) {
		return !item1.equals(item2);
	}

	@Override
	protected Boolean evaluate(Number num1, Number num2) {
		return num1.doubleValue() != num2.doubleValue();
	}

}