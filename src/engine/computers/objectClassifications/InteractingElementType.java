package engine.computers.objectClassifications;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;

/**
 * An enumeration for the types of elements available e.g. colliding, visible
 * 
 * @author Stanley
 *
 */
public enum InteractingElementType {
    COLLIDING("Colliding"),
    VISIBLE("Visible");

    private String myString;

    /**
     * Create an element type enumeration
     * 
     * @param stringRepresentation the key for the element type in the map.
     */
    private InteractingElementType (String stringRepresentation) {
        myString = stringRepresentation;
    }

    @Override
    public String toString () {
        return myString;
    }
    
    public static InteractingElementType getEnumFromValue (String value) {
        List<InteractingElementType> matchingEnums =  Arrays.asList(InteractingElementType.values()).stream()
                .filter(type -> type.toString().equals(value))
                .collect(Collectors.toList());
        // TODO: make null enum to return
        return matchingEnums.size()==0 ? InteractingElementType.COLLIDING : matchingEnums.get(0);
    }
    
}
