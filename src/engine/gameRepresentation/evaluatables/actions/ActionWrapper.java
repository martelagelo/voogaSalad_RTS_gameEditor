package engine.gameRepresentation.evaluatables.actions;

import java.io.Serializable;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;


/**
 * A basic wrapper for an action that consists of a String containing the action type name and a
 * list of strings constituting the action's paramaters. Each Action defines the number of Strings
 * it will need, causing us to necessitate the use of Java's varargs functionality. This class is a
 * data wrapper class and is necessary in this design as it provides a cohesive way in which the
 * editor can specify very complex actions without requiring the user to write VScript code.
 * 
 * @author Zach
 *
 */
public class ActionWrapper implements Serializable {

    private static final long serialVersionUID = 5596741540525917388L;
    private ActionOptions myActionName;
    private String[] myParameters;
    private ActionType myActionType;

    /**
     * Create the action wrapper with a given action name and a string of parameters required by the
     * action.
     * 
     * @param actionType the type of the action i.e. collision, internal, vision, etc.
     * @param actionName the class name of the action as located from an enumeration
     * @param parameters any parameters that might be needed for the action
     */
    public ActionWrapper (ActionType actionType, ActionOptions actionName, String ... parameters) {
        myActionType = actionType;
        myActionName = actionName;
        myParameters = parameters;
    }

    public String getActionClassName () {
        return myActionName.getClassString();
    }

    public String[] getParameters () {
        return myParameters;
    }

    public ActionType getActionType () {
        return myActionType;
    }

}
