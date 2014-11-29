package game_engine.gameRepresentation.evaluatables.parameters;

import game_engine.gameRepresentation.evaluatables.Action;
import game_engine.gameRepresentation.evaluatables.ElementPair;


/**
 * A parameter that holds and returns an action
 * 
 * @author Zach
 *
 */
public class ActionParameter extends Parameter<Action> {

    private Action myAction;

    public ActionParameter (String id, Action action) {
        super(Action.class, id);
        myAction = action;
    }

    @Override
    public Action evaluate (ElementPair elements) {
        return myAction;
    }

    @Override
    public boolean setValue (ElementPair elements, Action value) {
        myAction = value;
        return true;
    }

}
