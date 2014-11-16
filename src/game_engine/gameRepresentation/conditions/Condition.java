package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Condition {

    private List<ConditionOnImmediateElements> myImmediateConditions;
    private Map<ConditionOnRemoteElements, String> myRemoteConditions;
    protected String conditionType;

    public String getType () {
        return conditionType;
    }

    public Condition (List<ConditionOnImmediateElements> immediateConditions,
                      Map<ConditionOnRemoteElements, String> remoteConditions) {
        myImmediateConditions = immediateConditions;
        myRemoteConditions = remoteConditions;
    }

    public boolean evaluate (SelectableGameElement parent, DrawableGameElement interactingElement) {
        List<GameElementState> immediateElements = new ArrayList<GameElementState>();
        immediateElements.add(parent.getState());
        immediateElements.add(interactingElement.getState());
        
        List<Boolean> returnValues = new ArrayList<Boolean>();
        for (ConditionOnImmediateElements immediateCondition : myImmediateConditions) {
            returnValues.add(immediateCondition.evaluate(immediateElements));
        }
        for (Entry<ConditionOnRemoteElements, String> entry : myRemoteConditions.entrySet()) {
            ConditionOnRemoteElements remoteCondition = entry.getKey();
            String parameters = entry.getValue();
            returnValues.add(remoteCondition.evaluate(parameters));
        }
        boolean finalValue = returnValues.get(0);
        returnValues.remove(0);
        for (Boolean b : returnValues) {
            finalValue = finalValue && b;
        }
        return finalValue;
    }

}
