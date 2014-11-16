package game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes.textual;

import java.util.List;
import game_engine.gameRepresentation.conditions.ConditionOnImmediateElements;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;


public abstract class AttributeIsEqualToStringCondition implements ConditionOnImmediateElements {

    protected String myAttribute;
    protected Number myValue;

    public AttributeIsEqualToStringCondition (String attribute, Number value) {
        myAttribute = attribute;
        myValue = value;
    }

    @Override
    public Boolean evaluate (List<GameElementState> parameters) {
        return getPertinentElementState(parameters).getTextualAttribute(myAttribute)
                .equals(myValue);
    }

    protected abstract GameElementState getPertinentElementState (List<GameElementState> parameters);

}
