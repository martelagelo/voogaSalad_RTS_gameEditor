package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;

public class Result<A,B> extends Evaluator<A,B,Object> {

    public Result (String id,
                            Evaluatable<A> parameter1,
                            Evaluatable<B> parameter2) {
        super(Object.class, id, "RESULT", parameter1, parameter2);
    }
    @Override
    protected Object evaluate (Object item1, Object item2) {
        return item1;
    }
}
