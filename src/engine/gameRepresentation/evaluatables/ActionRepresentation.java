package engine.gameRepresentation.evaluatables;

/**
 * A basic data wrapper to wrap a string of a type and an evaluatable to create the representation
 * of an action.
 * 
 * @author Zach
 *
 */
public class ActionRepresentation {
    private String myActionType;
    private Evaluatable<?> myEvaluatable;

    /**
     * Create an action with a type string and an evaluatable for the executable for the action
     */
    public ActionRepresentation (String actionType, Evaluatable<?> action) {
        myEvaluatable = action;
        myActionType = actionType;
    }

    public String getActionType () {
        return myActionType;
    }

    public Evaluatable<?> getEvaluatable () {
        return myEvaluatable;
    }

}
