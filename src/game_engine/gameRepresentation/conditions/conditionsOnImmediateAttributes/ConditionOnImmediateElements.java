package game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes;

import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.List;


/**
 * The ConditionOnImmediateElements interface is an interface implemented by Condition strategies
 * that can evaluate knowing only GameElements given to them.
 * 
 * @author Steve
 *
 */

public interface ConditionOnImmediateElements {

    /**
     * @param parameters - by default, the GameElement holding this condition is the first element
     *        in the list.
     * @return the result of evaluating the condition
     */
    public abstract Boolean evaluate (List<GameElementState> parameters);

}
