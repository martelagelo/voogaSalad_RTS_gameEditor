package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * An evaluator that tries to assign the value at parameter 2 to the first
 * parameter
 * 
 * @author Zach
 */
public class EqualsAssignment<A, B> extends Evaluator<A, B, Boolean> {
    public EqualsAssignment (Evaluatable<A> parameter1,
                             Evaluatable<B> parameter2) {
        super(Boolean.class, "==", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Number item1, Number item2) {
        return evaluate((Object) item1, (Object) item2);
    }

    @Override
    protected Boolean evaluate (Object input1, Object input2) {
        return setParameter1Value(input2);
    }

}
