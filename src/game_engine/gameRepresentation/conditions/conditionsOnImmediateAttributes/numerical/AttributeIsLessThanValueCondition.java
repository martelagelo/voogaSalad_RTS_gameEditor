package game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes.numerical;

import java.util.List;
import game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes.ConditionOnImmediateElements;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;


public abstract class AttributeIsLessThanValueCondition implements ConditionOnImmediateElements {

    protected String myAttribute;
    protected Number myValue;

    public AttributeIsLessThanValueCondition (String attribute, Number value) {
        myAttribute = attribute;
        myValue = value;
    }

    @Override
    public Boolean evaluate (List<GameElementState> parameters) {
        return getPertinentElementState(parameters).getNumericalAttribute(myAttribute).longValue() < myValue
                .longValue();
    }

    protected abstract GameElementState getPertinentElementState (List<GameElementState> parameters);

}
