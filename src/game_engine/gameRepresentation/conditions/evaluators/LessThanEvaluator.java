package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.NumberEvaluator;
import game_engine.stateManaging.GameElementManager;

/**
 * A less than evaluator
 *
 * @author Zach
 *
 */
public class LessThanEvaluator<A, B> extends Evaluator<A, B, Boolean> implements
		NumberEvaluator<Boolean> {

	public LessThanEvaluator(
			Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
		super("<",parameter1, parameter2);
	}

	@Override
	public Boolean evaluate(Double num1, Double num2) {
		System.out.println("Calling child method");
		return num1.doubleValue() < num2.doubleValue();
	}

}
