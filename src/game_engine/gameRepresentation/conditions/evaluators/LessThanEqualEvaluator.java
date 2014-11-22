package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.NumberEvaluator;
import game_engine.stateManaging.GameElementManager;

/**
 * A less than or equal to evaluator
 * 
 * @author Zach
 *
 */
public class LessThanEqualEvaluator<A, B> extends Evaluator<A, B, Boolean>
		implements NumberEvaluator<Boolean> {

	public LessThanEqualEvaluator(GameElementManager elementManager,
			String parameter1, String parameter2) {
		super("<=", elementManager, parameter1, parameter2);
	}

	@Override
	public Boolean evaluate(Number number1, Number number2) {
		return number1.doubleValue() <= number2.doubleValue();
	}

}
