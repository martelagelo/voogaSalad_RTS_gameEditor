package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.parameters.helpers.ElementPromise;
import engine.gameRepresentation.renderedRepresentation.GameElement;

/**
 * Spawn a selectable element at the location of the game element with the given x and y positions
 * @author Zach
 *
 */
public class SpawnSelectableElement<A, B> extends Evaluator<A, B, Boolean> {

    public SpawnSelectableElement (String id,
                        Evaluatable<A> parameter1,
                        Evaluatable<B> parameter2) {
        super(Boolean.class, id, "Spawn Element", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (GameElement element, ElementPromise promise) {
        promise.getManager()
                .addSelectableGameElementToLevel(promise.getElementName(),
                                                 element.getNumericalAttribute(StateTags.X_POSITION)
                                                         .doubleValue() +
                                                         element.getNumericalAttribute(StateTags.X_SPAWN_OFFSET)
                                                                 .doubleValue(),
                                                 element.getNumericalAttribute(StateTags.Y_POSITION)
                                                         .doubleValue() +
                                                         element.getNumericalAttribute(StateTags.Y_SPAWN_OFFSET)
                                                                 .doubleValue());
        return true;
    }

}
