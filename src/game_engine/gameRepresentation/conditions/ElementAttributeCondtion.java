package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.List;
import java.util.function.Function;


/**
 * This type of Condition can be evaluated without knowledge of more than the GameElement that holds
 * the Condition.
 * 
 * @author Steve
 *
 */

public class ElementAttributeCondtion extends Condition {

    public ElementAttributeCondtion (String type, Function<GameElementState, Boolean> function) {
        conditionType = type;
        myFunction = function;
    }

    private Function<GameElementState, Boolean> myFunction;

    @Override
    public Boolean evaluate (List<GameElementState> parameters) {
        // TODO: Add exception return when error checking fails
        return myFunction.apply(parameters.get(0));
    }

}
