package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;

/**
 * An evaluator that checks to see if the type of an element is equal to a given
 * string.
 *
 * @author Zach
 *
 */
public class TypeCheck<A, B> extends Evaluator<A, B, Boolean> {

    public TypeCheck (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, "typeEquals", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (GameElement elem, String type) {
        return elem.isType(type);
    }

}
