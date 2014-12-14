package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;

/**
 * Tell the actor element to focus on the actee. Returns true if selected.
 *
 * @author Zach
 *
 */
public class SetFocused<A, B> extends Evaluator<A, B, Boolean> {

    public SetFocused (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.TYPE, "setFocused", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (GameElement element1, GameElement element2) {
        ((SelectableGameElement) element1).setFocusedElement(((SelectableGameElement) element2));
        return true;
    }
}
