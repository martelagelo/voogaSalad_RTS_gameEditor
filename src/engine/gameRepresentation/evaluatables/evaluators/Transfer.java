package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * An evaluator that takes all of one parameter's value and transfers it to another parameter.
 * 
 * @author Zach
 *
 */
public class Transfer<A, B> extends Evaluator<A, B, Boolean> {

    public Transfer (Evaluatable<A> parameter1,
                     Evaluatable<B> parameter2) {
        super(Boolean.class, "transfer", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (Number val1, Number val2) {
        setParameter2Value(val1.doubleValue() + val2.doubleValue());
        setParameter1Value(0);
        return true;

    }

}
