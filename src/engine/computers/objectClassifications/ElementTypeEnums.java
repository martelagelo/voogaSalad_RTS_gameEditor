package engine.computers.objectClassifications;

/**
 * An enumeration for the types of elements available e.g. colliding, visible
 * 
 * @author Stanley
 *
 */
public enum ElementTypeEnums {
    COLLIDING("Colliding"),
    VISIBLE("Visible");

    private String myString;

    /**
     * Create an element type enumeration
     * 
     * @param stringRepresentation the key for the element type in the map.
     */
    private ElementTypeEnums (String stringRepresentation) {
        myString = stringRepresentation;
    }

    @Override
    public String toString () {
        return myString;
    }
    
}
