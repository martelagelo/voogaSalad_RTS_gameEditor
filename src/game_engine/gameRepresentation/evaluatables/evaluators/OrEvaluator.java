package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * Implements the OR evaluator functionality
 * 
 * @author Zach
 */
public class OrEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public OrEvaluator (String id, Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, id, "||", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Boolean param1, Boolean param2) {
        return param1 || param2;
    }

}
