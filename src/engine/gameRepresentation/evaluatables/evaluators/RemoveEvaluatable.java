package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * A class that removes an evaluatable action from a game element if the game element currently
 * holds an action with the given ID
 * 
 * @author Zach
 *
 */
public class RemoveEvaluatable<A, B> extends Evaluator<A, B, Boolean> {

    public RemoveEvaluatable (String id,
                                       Evaluatable<A> parameter1,
                                       Evaluatable<B> parameter2) {
        super(Boolean.class, id, "REMOVE_EVALUATOR", parameter1, parameter2);
    }

    /**
     * Remove the action with the given string from the game element
     */
    protected Boolean evaluate (GameElement item1, String item2) {
        item1.removeAction(item2);
        return true;
    }

}
