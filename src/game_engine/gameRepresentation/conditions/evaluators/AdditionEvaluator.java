package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.NumberEvaluator;
import game_engine.stateManaging.GameElementManager;
/**
 * Perform addition on two numbers
 * @author Zach
 *
 * @param <A> the first number
 * @param <B> the second number
 */
public class AdditionEvaluator<A, B> extends Evaluator<A, B, Number> implements
		NumberEvaluator<Number> {

	public AdditionEvaluator(String evaluatorRepresentation,
			GameElementManager elementManager, String parameter1,
			String parameter2) {
		super(evaluatorRepresentation, elementManager, parameter1, parameter2);
	}

	@Override
	public Number evaluate(Number num1, Number num2) {
		return num1.doubleValue() + num2.doubleValue();
	}

}
