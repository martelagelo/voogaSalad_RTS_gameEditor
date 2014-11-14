package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.GameElement;
import java.util.List;
import java.util.function.Function;


public class ElementAttributeCondtion extends Condition {

    private Function<GameElement, Boolean> function;

    @Override
    public Boolean evaluate (List<GameElement> parameters) {
        // TODO: Add exception return when error checking fails
        return function.apply(parameters.get(0));
    }

}