package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * Set the animation of a given game element to the animation in its map referenced by a given
 * string
 * 
 * @author Zach
 */
public class SetAnimationEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public SetAnimationEvaluator (String id,
                                  Evaluatable<A> parameter1,
                                  Evaluatable<B> parameter2) {
        super(Boolean.class, id, "setAnimation", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (GameElement object1, String object2) {
        try {
            ((DrawableGameElement) object1).setAnimation(object2);
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }

}
