package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.NumberEvaluator;
import game_engine.stateManaging.GameElementManager;
/**
 * Perform addition on two numbers
 * @author Zach
 *
 * @param <A> the first number
 * @param <B> the second number
 */
public class AdditionEvaluator<A, B> extends Evaluator<A, B, Double> implements
		NumberEvaluator<Double> {

	public AdditionEvaluator(String evaluatorRepresentation,
			Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
		super(Double.class,evaluatorRepresentation, parameter1, parameter2);
	}
	//TODO fix this implementation. Currently does not set a value
	@Override
	public Double evaluate(Double num1, Double num2) {
		return num1.doubleValue() + num2.doubleValue();
	}

}
