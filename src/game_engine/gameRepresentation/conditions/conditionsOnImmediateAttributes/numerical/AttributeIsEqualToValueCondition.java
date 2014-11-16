package game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes.numerical;

import game_engine.gameRepresentation.conditions.ConditionOnImmediateElements;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.List;


public abstract class AttributeIsEqualToValueCondition implements ConditionOnImmediateElements {

    protected String myAttribute;
    protected Number myValue;

    public AttributeIsEqualToValueCondition (String attribute, Number value) {
        myAttribute = attribute;
        myValue = value;
    }

    @Override
    public Boolean evaluate (List<GameElementState> parameters) {
        return getPertinentElementState(parameters).getNumericalAttribute(myAttribute).longValue() == myValue
                .longValue();
    }

    protected abstract GameElementState getPertinentElementState (List<GameElementState> parameters);

}
