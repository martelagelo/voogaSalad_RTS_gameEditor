package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.NumberEvaluator;

/**
 * A less than or equal to evaluator
 * 
 * @author Zach
 *
 */
public class LessThanEqualEvaluator<A, B> extends Evaluator<A, B, Boolean>
		implements NumberEvaluator<Boolean> {

	public LessThanEqualEvaluator(Evaluatable<A> parameter1,
			Evaluatable<B> parameter2) {
		super(Boolean.class, "<=", parameter1, parameter2);
	}

	@Override
	public Boolean evaluate(Double number1, Double number2) {
		return number1.doubleValue() <= number2.doubleValue();
	}

}
