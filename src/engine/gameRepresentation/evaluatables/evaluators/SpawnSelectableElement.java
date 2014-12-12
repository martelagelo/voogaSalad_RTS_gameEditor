package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.parameters.helpers.ElementPromise;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;


/**
 * Spawn a selectable element at the location of the game element with the given x and y positions
 * 
 * @author Zach
 *
 */
public class SpawnSelectableElement<A, B> extends Evaluator<A, B, SelectableGameElement> {

    public SpawnSelectableElement (String id,
                                   Evaluatable<A> parameter1,
                                   Evaluatable<B> parameter2) {
        super(SelectableGameElement.class, id, "Spawn Element", parameter1, parameter2);
    }

    @Override
    protected SelectableGameElement evaluate (GameElement element, ElementPromise promise) {
        return promise.getManager()
                .addSelectableGameElementToLevel(promise.getElementName(),
                                                 element.getNumericalAttribute(StateTags.X_POSITION.getValue())
                                                         .doubleValue() +
                                                         element.getNumericalAttribute(StateTags.X_SPAWN_OFFSET.getValue())
                                                                 .doubleValue(),
                                                 element.getNumericalAttribute(StateTags.Y_POSITION.getValue())
                                                         .doubleValue() +
                                                         element.getNumericalAttribute(StateTags.Y_SPAWN_OFFSET.getValue())
                                                                 .doubleValue(),element.getTextualAttribute(StateTags.TEAM_COLOR.getValue()));
        
    }
}
