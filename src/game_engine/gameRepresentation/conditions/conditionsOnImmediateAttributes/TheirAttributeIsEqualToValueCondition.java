package game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes;

import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.List;

public class TheirAttributeIsEqualToValueCondition extends AttributeIsEqualToValueCondition {

    public TheirAttributeIsEqualToValueCondition (String attribute, Number value) {
        super(attribute, value);
    }

    @Override
    protected GameElementState getPertinentElementState (List<GameElementState> parameters) {
        return parameters.get(1);
    }

}
