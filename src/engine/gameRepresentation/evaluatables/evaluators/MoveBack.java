package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * Moves the primary element backwards
 * 
 * @author Zach
 */
public class MoveBack<A, B> extends Evaluator<A, B, Boolean> {

    public MoveBack (String id,
                              Evaluatable<A> parameter1,
                              Evaluatable<B> parameter2) {
        super(Boolean.class, id, "moveBack", parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (GameElement object1, GameElement object2) {
        object1.setNumericalAttribute(StateTags.X_POSITION,
                                      object1.getNumericalAttribute(StateTags.X_POSITION)
                                              .doubleValue()-
                                              object1.getNumericalAttribute(StateTags.X_VELOCITY)
                                                      .doubleValue());
        object1.setNumericalAttribute(StateTags.Y_POSITION,
                                      object1.getNumericalAttribute(StateTags.Y_POSITION)
                                              .doubleValue()-
                                              object1.getNumericalAttribute(StateTags.Y_VELOCITY)
                                                      .doubleValue());
        return true;
    }
}
