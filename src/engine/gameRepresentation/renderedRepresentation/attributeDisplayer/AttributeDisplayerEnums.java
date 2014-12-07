package engine.gameRepresentation.renderedRepresentation.attributeDisplayer;

/**
 * An enumeration for the types of attributes displayed.
 * 
 * @author Stanley
 *
 */
public enum AttributeDisplayerEnums {
    ATTRIBUTEBAR("AttributeBarDisplayer");
    
    private String myString;

    /**
     * Create an attribute displayer type enumeration
     * 
     * @param stringRepresentation the key for the action type in the map.
     */
    private AttributeDisplayerEnums (String stringRepresentation) {
        myString = stringRepresentation;
    }

    @Override
    public String toString () {
        return myString;
    }
    
}
