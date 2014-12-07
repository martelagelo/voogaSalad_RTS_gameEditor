package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;


/**
 * Set the focused element for the actor to the actee
 * 
 * @author Zach
 *
 */
public class SetFocused<A, B> extends Evaluator<A, B, Boolean> {

    public SetFocused (
                       String id,
                       Evaluatable<A> parameter1,
                       Evaluatable<B> parameter2) {
        super(Boolean.TYPE, id, "setFocused", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (GameElement element1, GameElement element2) {
        ((SelectableGameElement) element1).setFocusedElement(((SelectableGameElement) element2));
        return true;
    }
}
