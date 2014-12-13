package engine.gameRepresentation.evaluatables.parameters;

import engine.gameRepresentation.evaluatables.ActionRepresentation;
import engine.gameRepresentation.evaluatables.ElementPair;


/**
 * A parameter that holds and returns an action. This was created after the action wrapper was
 * created in order to allow actions to call other actions.
 * 
 * @author Zach
 *
 */
public class ActionParameter extends Parameter<ActionRepresentation> {

    private ActionRepresentation myAction;

    public ActionParameter (ActionRepresentation action) {
        super(ActionRepresentation.class);
        myAction = action;
    }

    @Override
    public ActionRepresentation evaluate (ElementPair elements) {
        return myAction;
    }

    @Override
    public boolean setValue (ElementPair elements, ActionRepresentation value) {
        myAction = value;
        return true;
    }

}
