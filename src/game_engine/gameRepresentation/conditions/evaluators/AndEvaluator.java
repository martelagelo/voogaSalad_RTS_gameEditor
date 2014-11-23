package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.BooleanEvaluator;

/**
 * An evaluator that implements the AND functionality
 * 
 * @author Zach
 */
public class AndEvaluator<A, B> extends Evaluator<A, B, Boolean> implements
		BooleanEvaluator<Boolean> {

	public AndEvaluator(Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
		super(Boolean.class, "&&", parameter1, parameter2);
	}

	@Override
	public Boolean evaluate(Boolean param1, Boolean param2) {
		return param1 && param2;
	}

}
