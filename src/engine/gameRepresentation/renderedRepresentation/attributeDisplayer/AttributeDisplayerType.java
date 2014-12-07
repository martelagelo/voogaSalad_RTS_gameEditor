package engine.gameRepresentation.renderedRepresentation.attributeDisplayer;

/**
 * An enumeration for the types of actions available e.g. collision, internal, vision
 * 
 * @author Michael D.
 *
 */
public enum AttributeDisplayerType {
    HEALTH_BAR("AttributeBarDisplayer");

    private String myString;

    /**
     * Create an action type enumeration
     * 
     * @param stringRepresentation the key for the action type in the map.
     */
    private AttributeDisplayerType (String stringRepresentation) {
        myString = stringRepresentation;
    }

    @Override
    public String toString () {
        return myString;
    }
    
}
