package game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes.numerical;

import java.util.List;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;

public class MyAttributeIsEqualToValueCondition extends AttributeIsEqualToValueCondition {

    public MyAttributeIsEqualToValueCondition (String attribute, Number value) {
        super(attribute, value);
    }

    @Override
    protected GameElementState getPertinentElementState (List<GameElementState> parameters) {
        return parameters.get(0);
    }

}
