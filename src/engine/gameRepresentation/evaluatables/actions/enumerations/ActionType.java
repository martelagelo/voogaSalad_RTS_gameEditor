package engine.gameRepresentation.evaluatables.actions.enumerations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An enumeration for the types of actions available e.g. collision, internal, vision
 * 
 * @author Zach, Stanley
 *
 */
public enum ActionType {
    COLLISION("Collision"),
    INTERNAL("Internal"),
    VISION("Vision"),
    BUTTON("Button"),
    FOCUSED("Focused Element"),
    OBJECTIVE("Objective"),
    SELECTION("Selection");

    private String myString;

    /**
     * Create an action type enumeration
     * 
     * @param stringRepresentation the key for the action type in the map.
     */
    private ActionType (String stringRepresentation) {
        myString = stringRepresentation;
    }

    @Override
    public String toString () {
        return myString;
    }

    public static ActionType getEnumFromValue (String value) {
        List<ActionType> matchingEnums =  Arrays.asList(ActionType.values()).stream()
                .filter(type -> type.toString().equals(value))
                .collect(Collectors.toList());
        // TODO: make null enum to return
        return matchingEnums.size()==0 ? ActionType.BUTTON : matchingEnums.get(0);
    }

}
