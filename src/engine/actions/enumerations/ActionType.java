package engine.actions.enumerations;

/**
 * An enumeration for the types of actions available e.g. collision, internal, vision
 * 
 * @author Zach
 *
 */
public enum ActionType {
    COLLISION("Collision"),
    INTERNAL("Internal"),
    VISION("Vision");

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

}

