package vooga.engine.gameRepresentation.conditions;

import java.util.List;
import java.util.function.Function;
import vooga.engine.gameRepresentation.GameElement;


public class ElementAttributeCondtion extends Condition {

    private Function<GameElement, Boolean> function;

    @Override
    public Boolean evaluate (List<GameElement> parameters) {
        // TODO: Add exception return when error checking fails
        return function.apply(parameters.get(0));
    }

}
