package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.actions.Action;
//import game_engine.gameRepresentation.conditions.Condition;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author Jonathan Tseng, Nishad, Rahul, Steve
 *
 */
public class GameElement {

    // TODO: FIX THIS SHIT, not sure if right ???
//    protected Map<Condition, Action> myConditionActionPairs;

    private GameElementState myGameElementState;

    public GameElement (GameElementState gameElementState) {
        myGameElementState = gameElementState;
//        myConditionActionPairs = new HashMap<>();
        // TODO: Parse the condition action pairs in state (string->string) into (condition->action)
    }

    public GameElementState getGameElementState () {
        return myGameElementState;
    }

    public void update () {
        updateSelfDueToInternalFactors();
    }

    private void updateSelfDueToInternalFactors () {
        // TODO Auto-generated method stub
    }

}
