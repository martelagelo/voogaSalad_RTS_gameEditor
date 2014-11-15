package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.List;
import java.util.function.BiFunction;


/**
 * This type of Condition needs information about two GameElements that are interacting.
 * 
 * @author Steve
 *
 */
public class ElementInteractionCondition extends Condition {

    private BiFunction<GameElementState, GameElementState, Boolean> function;

    @Override
    public Boolean evaluate (List<GameElementState> parameters) {
        // TODO: Add exception return when error checking fails
        return function.apply(parameters.get(0), parameters.get(1));
    }

}
