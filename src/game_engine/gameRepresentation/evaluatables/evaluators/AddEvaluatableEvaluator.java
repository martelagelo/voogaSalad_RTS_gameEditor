package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Action;
import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * An evaluator that adds a given action evaluatable to the given game element
 * 
 * @author Zach
 */
public class AddEvaluatableEvaluator<A, B> extends Evaluator<A, B, Boolean> {

    public AddEvaluatableEvaluator (
                                    String id,
                                    Evaluatable<A> parameter1,
                                    Evaluatable<B> parameter2) {
        super(Boolean.class, id, "ADD_ACTION", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (GameElement item1, Action action) {
        item1.addAction(action.getActionType(), action.getEvaluatable());
        return true;
    }

}
