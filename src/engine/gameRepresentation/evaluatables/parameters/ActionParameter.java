package engine.gameRepresentation.evaluatables.parameters;

import engine.gameRepresentation.evaluatables.ActionRepresentation;
import engine.gameRepresentation.evaluatables.ElementPair;


/**
 * A parameter that holds and returns an action
 * 
 * @author Zach
 *
 */
public class ActionParameter extends Parameter<ActionRepresentation> {

    private ActionRepresentation myAction;

    public ActionParameter (String id, ActionRepresentation action) {
        super(ActionRepresentation.class, id);
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
