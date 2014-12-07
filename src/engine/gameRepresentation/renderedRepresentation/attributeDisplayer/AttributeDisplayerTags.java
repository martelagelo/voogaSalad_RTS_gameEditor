package engine.gameRepresentation.renderedRepresentation.attributeDisplayer;



/**
 * An enumeration for the types of attributes displayed.
 * 
 * @author Michael D., Stanley
 *
 */
public enum AttributeDisplayerTags {
    
    HEALTH_BAR_DISPLAYER("HealthBarDisplayer"),
    ATTRIBUTE_ARROW_DISPLAYER("AttributeArrowDisplayer");
    
    
    private String myValue;
    
    private AttributeDisplayerTags (String value) {
        myValue = value;
    }
    
    public String getValue() {
        return myValue;
    }

    public enum AttributeDisplayerType {
        HEALTH_BAR_DISPLAYER,
        ATTRIBUTE_ARROW_DISPLAYER
    }
    
}
