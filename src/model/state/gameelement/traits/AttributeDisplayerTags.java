package model.state.gameelement.traits;

/**
 * An enumeration for the types of attributes displayed.
 * 
 * @author Michael D., Stanley
 *
 */
public enum AttributeDisplayerTags {

    ATTRIBUTE_BAR_DISPLAYER("AttributeBarDisplayer"), 
    ATTRIBUTE_ARROW_DISPLAYER("AttributeArrowDisplayer");

    private String myValue;

    private AttributeDisplayerTags (String value) {
        myValue = value;
    }

    public String getValue () {
        return myValue;
    }

}
