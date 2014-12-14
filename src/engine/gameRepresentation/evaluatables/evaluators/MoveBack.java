package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;

/**
 * Moves the primary element backwards by its velocity components. Always
 * evaluates to true.
 *
 * @author Zach
 */
public class MoveBack<A, B> extends Evaluator<A, B, Boolean> {

    public MoveBack (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, "moveBack", parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (GameElement object1, GameElement object2) {
        object1.setNumericalAttribute(StateTags.X_POSITION.getValue(), object1
                .getNumericalAttribute(StateTags.X_POSITION.getValue()).doubleValue()
                - object1.getNumericalAttribute(StateTags.X_VELOCITY.getValue()).doubleValue());
        object1.setNumericalAttribute(StateTags.Y_POSITION.getValue(), object1
                .getNumericalAttribute(StateTags.Y_POSITION.getValue()).doubleValue()
                - object1.getNumericalAttribute(StateTags.Y_VELOCITY.getValue()).doubleValue());
        return true;
    }
}
