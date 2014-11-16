package game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes.textual;

import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.List;


public class TheirAttributeIsEqualToStringCondition extends AttributeIsEqualToStringCondition {

    public TheirAttributeIsEqualToStringCondition (String attribute, Number value) {
        super(attribute, value);
    }

    @Override
    protected GameElementState getPertinentElementState (List<GameElementState> parameters) {
        return parameters.get(1);
    }

}
