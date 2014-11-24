package game_engine.gameRepresentation.renderedRepresentation;

// import game_engine.gameRepresentation.conditions.Condition;
import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;

import java.util.Map;


/**
 * A basic game element. Contains a state and a map of conditions to actions. Also contains an
 * update method to update the element due to internal state.
 *
 * @author Jonathan, Nishad, Rahul, Steve
 *
 */
public class GameElement{

    private Map<Evaluatable<Boolean>, Evaluatable<?>> myConditionActionPairs;
    private GameElementState myGameElementState;


    /**
     * Create a game element with the given state
     *
     * @param gameElementState the state of the game element
     */
    public GameElement (GameElementState gameElementState,
                        Map<Evaluatable<Boolean>, Evaluatable<?>> conditionActionPairs) {
        myGameElementState = gameElementState;
        myConditionActionPairs = conditionActionPairs;
    }

    /**
     * @return the game element's state
     */
    public GameElementState getGameElementState () {
        return myGameElementState;
    }

    /**
     * Update the internal state of the game element based on its environment
     */
    public void update () {
        updateSelfDueToInternalFactors();
    }

    /**
     * Update the game element based on internally stored parameters and conditions and actions
     */
    private void updateSelfDueToInternalFactors () {
        // TODO Auto-generated method stub
    }

    //TODO isn't this giving up an important collection??
    public Map<Evaluatable<Boolean>, Evaluatable<?>> getConditionActionPairs () {
        return myConditionActionPairs;
    }
}
