package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * An evaluator that implements the AND functionality
 * 
 * @author Zach
 */
public class AndEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public AndEvaluator (String id, Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, id, "&&", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Boolean param1, Boolean param2) {
        return param1 && param2;
    }

}
