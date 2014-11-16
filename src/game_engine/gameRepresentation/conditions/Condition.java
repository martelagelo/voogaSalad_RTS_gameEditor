package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


/**
 * Overarching Condition class able to hold both Conditions that operate on element state and also
 * conditions that need more information. Booleans in the maps allow for negation of conditions.
 * 
 * @author Steve
 *
 */
public class Condition {

    private Map<Boolean, ConditionOnImmediateElements> myImmediateConditions;
    private Map<Boolean, Map<ConditionOnRemoteElements, String>> myRemoteConditions;
    protected String conditionType;

    public String getType () {
        return conditionType;
    }

    public Condition (Map<Boolean, ConditionOnImmediateElements> immediateConditions,
                      Map<Boolean, Map<ConditionOnRemoteElements, String>> remoteConditions) {
        myImmediateConditions = immediateConditions;
        myRemoteConditions = remoteConditions;
    }

    public boolean evaluate (SelectableGameElementState parent,
                             DrawableGameElementState interactingElement) {
        List<GameElementState> immediateElements = new ArrayList<GameElementState>();
        immediateElements.add(parent);
        immediateElements.add(interactingElement);

        List<Boolean> returnValues = new ArrayList<Boolean>();
        for (Entry<Boolean, ConditionOnImmediateElements> entry : myImmediateConditions.entrySet()) {
            ConditionOnImmediateElements immediateCondition = entry.getValue();
            Boolean augmentingValue = entry.getKey();
            returnValues.add(augmentingValue && immediateCondition.evaluate(immediateElements));
        }
        for (Entry<Boolean, Map<ConditionOnRemoteElements, String>> entry : myRemoteConditions
                .entrySet()) {
            Boolean augmentingValue = entry.getKey();
            for (Entry<ConditionOnRemoteElements, String> subEntry : entry.getValue().entrySet()) {
                ConditionOnRemoteElements remoteCondition = subEntry.getKey();
                String parameters = subEntry.getValue();
                returnValues.add(augmentingValue && remoteCondition.evaluate(parameters));
            }
        }
        boolean finalValue = returnValues.get(0);
        returnValues.remove(0);
        for (Boolean b : returnValues) {
            finalValue = finalValue && b;
        }
        return finalValue;
    }
}
