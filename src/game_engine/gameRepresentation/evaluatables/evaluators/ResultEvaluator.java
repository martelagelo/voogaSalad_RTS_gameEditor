package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;

public class ResultEvaluator<A,B> extends Evaluator<A,B,Object> {

    public ResultEvaluator (String id,
                            Evaluatable<A> parameter1,
                            Evaluatable<B> parameter2) {
        super(Object.class, id, "RESULT", parameter1, parameter2);
    }

}
