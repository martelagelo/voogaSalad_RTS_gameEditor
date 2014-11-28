package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;

/**
 * A greater than evaluator
 *
 * @author Zach
 *
 */
public class GreaterThanEvaluator<A, B> extends Evaluator<A, B, Boolean> {

	public GreaterThanEvaluator(Evaluatable<A> parameter1,
			Evaluatable<B> parameter2) {
		super(Boolean.class, ">", parameter1, parameter2);
	}

	@Override
	protected Boolean evaluate(Number num1, Number num2) {
		return num1.doubleValue() > num2.doubleValue();
	}

}
