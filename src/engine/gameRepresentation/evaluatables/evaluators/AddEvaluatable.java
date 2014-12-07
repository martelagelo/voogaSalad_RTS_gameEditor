package engine.gameRepresentation.evaluatables.evaluators;

import engine.gameRepresentation.evaluatables.ActionRepresentation;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;
import engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * An evaluator that adds a given action evaluatable to the given game element
 * 
 * @author Zach, Stanley
 */
public class AddEvaluatable<A, B> extends Evaluator<A, B, Boolean> {

    public AddEvaluatable (
                                    String id,
                                    Evaluatable<A> parameter1,
                                    Evaluatable<B> parameter2) {
        super(Boolean.class, id, "ADD_ACTION", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (GameElement item1, ActionRepresentation action) {
        item1.addAction(action.getActionType(), action.getEvaluatable());
        return true;
    }

}
