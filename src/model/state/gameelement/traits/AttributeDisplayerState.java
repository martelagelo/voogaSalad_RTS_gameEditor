package model.state.gameelement.traits;

import java.io.Serializable;

import util.JSONable;

/**
 * A wrapper for all the savable information needed to represent an attribute
 * displayer. This is just a data wrapper and as such all of its instance
 * variables are public.
 * 
 * @author Zach, Michael D., Stanley
 *
 */
public class AttributeDisplayerState implements JSONable, Serializable {

    private static final long serialVersionUID = -4791954709522087485L;
    private AttributeDisplayerTags myDisplayerTags;
    private String myParameterTag;
    private double myMinAttributeValue;
    private double myMaxAttributeValue;
    private String myTextValue;

    /**
     * Constructor
     * 
     * @param displayerTag
     *            The reference to a widget's class
     * @param numericParameterTag
     *            The attribute name
     * @param minAttributeValue
     *            The max attribute value
     * @param maxAttributeValue
     *            The min attribute value
     * @param BackgroundColor
     *            The color in the background
     * @param ForegroundColor
     *            The color in the foreground
     */
    public AttributeDisplayerState (AttributeDisplayerTags displayerTag,
            String numericParameterTag, double minAttributeValue, double maxAttributeValue) {
        this.myDisplayerTags = displayerTag;
        this.myParameterTag = numericParameterTag;
        this.myMinAttributeValue = minAttributeValue;
        this.myMaxAttributeValue = maxAttributeValue;
    }

    public AttributeDisplayerState (AttributeDisplayerTags displayerTag,
            String textualParameterTag, String value) {
        this.myDisplayerTags = displayerTag;
        this.myParameterTag = textualParameterTag;
        this.myTextValue = value;
    }

    public AttributeDisplayerTags getDisplayerTag () {
        return myDisplayerTags;
    }

    public String getParameterTag () {
        return myParameterTag;
    }

    public double getMinValue () {
        return myMinAttributeValue;
    }

    public double getMaxValue () {
        return myMaxAttributeValue;
    }

    public String getTextValue () {
        return myTextValue;
    }

}
