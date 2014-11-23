package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;

/**
 * A less than evaluator
 *
 * @author Zach
 *
 */
public class LessThanEvaluator<A, B> extends Evaluator<A, B, Boolean>{

	public LessThanEvaluator(Evaluatable<A> parameter1,
			Evaluatable<B> parameter2) {
		super(Boolean.class, "<", parameter1, parameter2);
	}

	@Override
	protected Boolean evaluate(Number num1, Number num2) {
		return num1.doubleValue() < num2.doubleValue();
	}

}
