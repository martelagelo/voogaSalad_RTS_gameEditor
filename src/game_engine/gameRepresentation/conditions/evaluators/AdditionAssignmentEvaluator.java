package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;

/**
 * Perform addition on two numbers. Return a boolean indicating whether the
 * addition was successful
 * 
 * @author Zach
 *
 * @param <A>
 *            the first number
 * @param <B>
 *            the second number
 */
public class AdditionAssignmentEvaluator<A, B> extends Evaluator<A, B, Boolean> {

	public AdditionAssignmentEvaluator(Evaluatable<A> parameter1,
			Evaluatable<B> parameter2) {
		super(Boolean.class, "+=", parameter1, parameter2);
	}

	@Override
	protected Boolean evaluate(Double num1, Double num2) {
		return getParameter1().setValue(
				getElementPair(),
				getParameter1().getType().cast(
						num1.doubleValue() + num2.doubleValue()));
	}

}
