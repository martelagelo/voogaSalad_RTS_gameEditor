package engine.gameRepresentation.evaluatables;

import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;

/**
 * A basic data wrapper to wrap a string of a type and an evaluatable to create the representation
 * of an action.
 * 
 * @author Zach, Stanley
 *
 */
public class ActionRepresentation {
    private ActionType myActionType;
    private Evaluatable<?> myEvaluatable;

    /**
     * Create an action with a type string and an evaluatable for the executable for the action
     */
    public ActionRepresentation (ActionType actionType, Evaluatable<?> action) {
        myEvaluatable = action;
        myActionType = actionType;
    }

    public ActionType getActionType () {
        return myActionType;
    }

    public Evaluatable<?> getEvaluatable () {
        return myEvaluatable;
    }

}
