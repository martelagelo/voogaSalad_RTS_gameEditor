package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;

/**
 * An evaluator that makes one element follow another on the X axis
 *
 * @author Zach
 *
 */
public class FollowX<A, B> extends Evaluator<A, B, Boolean> {

    public FollowX (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, "follow", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (GameElement elem1, GameElement elem2) {
        elem1.setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION.getValue(),
                elem2.getNumericalAttribute(StateTags.X_POSITION.getValue()));
        return true;
    }

}
