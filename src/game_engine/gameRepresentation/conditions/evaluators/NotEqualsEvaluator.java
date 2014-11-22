package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.NumberEvaluator;
import game_engine.stateManaging.GameElementManager;

/**
 * A not equals evaluator
 *
 * @author Zach
 *
 */
public class NotEqualsEvaluator<A, B> extends Evaluator<A, B, Boolean>
		implements NumberEvaluator<Boolean> {
	public NotEqualsEvaluator(GameElementManager elementManager,
			String parameter1, String parameter2) {
		super("!=", elementManager, parameter1, parameter2);
	}

	@Override
	public Boolean evaluate(Object item1, Object item2) {
		return !item1.equals(item2);
	}

	@Override
	public Boolean evaluate(Number num1, Number num2) {
		return num1.doubleValue() != num2.doubleValue();
	}

}
