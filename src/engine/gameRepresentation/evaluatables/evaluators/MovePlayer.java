package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * An Evaluator that moves a player according to their velocity
 * 
 * @author Zach
 */
public class MovePlayer<A, B> extends Evaluator<A, B, Boolean> {

    public MovePlayer (String id,
                                Evaluatable<A> parameter1,
                                Evaluatable<B> parameter2) {
        super(Boolean.class, id, "Move", parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (GameElement element1, GameElement element2) {
        element1.setNumericalAttribute(StateTags.X_POSITION,
                                       element1.getNumericalAttribute(StateTags.X_POSITION)
                                               .doubleValue() +
                                               element1.getNumericalAttribute(StateTags.X_VELOCITY)
                                                       .doubleValue());
        element1.setNumericalAttribute(StateTags.Y_POSITION,
                                       element1.getNumericalAttribute(StateTags.Y_POSITION)
                                               .doubleValue() +
                                               element1.getNumericalAttribute(StateTags.Y_VELOCITY)
                                                       .doubleValue());
        return true;
    }

}
