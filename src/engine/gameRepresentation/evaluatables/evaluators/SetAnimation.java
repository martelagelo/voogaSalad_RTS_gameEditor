package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;


/**
 * Set the animation of a given game element to the animation in its map referenced by a given
 * string
 * 
 * @author Zach
 */
public class SetAnimation<A, B> extends Evaluator<A, B, Boolean> {

    public SetAnimation (String id,
                                  Evaluatable<A> parameter1,
                                  Evaluatable<B> parameter2) {
        super(Boolean.class, id, "setAnimation", parameter1, parameter2);
    }
    // TODO: do we need this class?

//    @Override
//    protected Boolean evaluate (GameElement object1, String object2) {
//        try {
//            ((DrawableGameElement) object1).setAnimation(object2);
//            return true;
//        }
//        catch (Exception e) {
//            return false;
//        }
//
//    }

}
