package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.gameElement.GameElement;
import java.util.List;
import java.util.function.Function;


public class ElementAttributeCondtion extends Condition {

    public ElementAttributeCondtion (String type, Function<GameElement, Boolean> function) {
        conditionType = type;
        myFunction = function;
    }

    private Function<GameElement, Boolean> myFunction;

    @Override
    public Boolean evaluate (List<GameElement> parameters) {
        // TODO: Add exception return when error checking fails
        return myFunction.apply(parameters.get(0));
    }

}
