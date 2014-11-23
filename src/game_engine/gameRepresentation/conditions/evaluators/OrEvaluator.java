package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.Evaluatable;

/**
 * Implements the OR evaluator functionality
 * 
 * @author Zach
 */
public class OrEvaluator<A, B> extends Evaluator<A, B, Boolean> {

	public OrEvaluator(Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
		super(Boolean.class, "||", parameter1, parameter2);
	}

	@Override
	protected Boolean evaluate(Boolean param1, Boolean param2) {
		return param1 || param2;
	}

}
