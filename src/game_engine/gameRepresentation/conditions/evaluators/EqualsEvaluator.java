package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;

/**
 * An equals evaluator
 * 
 * @author Zach
 *
 */
public class EqualsEvaluator<A, B> extends Evaluator<A, B, Boolean> {

	public EqualsEvaluator(Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
		super(Boolean.class, "==", parameter1, parameter2);
	}

	@Override
	protected Boolean evaluate(Object item1, Object item2) {
		return item1.equals(item2);
	}

	@Override
	protected Boolean evaluate(Number num1, Number num2) {
		return num1 == num2;
	}

}
