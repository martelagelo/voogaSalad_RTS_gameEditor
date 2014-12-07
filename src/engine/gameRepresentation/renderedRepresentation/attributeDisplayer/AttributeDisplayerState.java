package engine.gameRepresentation.renderedRepresentation.attributeDisplayer;

import java.io.Serializable;
import model.state.gameelement.StateTags;
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
	private AttributeDisplayerTags displayerTag;					//The the string describing the class needed to create a widget. Created with enum.
	private String parameterTag;					//The StateTag of the attribute or the name of the attribute
	private double minAttributeValue;			//for a numerical attribute: The minimum value 
	private double maxAttributeValue;			//for a numerical attribute: The maximum value
	private String myTextValue;					//for a textual attribute: The value

	public AttributeDisplayerState(AttributeDisplayerTags displayerTag,
			String numericParameterTag, double minAttributeValue,
			double maxAttributeValue) {
		this.displayerTag = displayerTag;
		this.parameterTag = numericParameterTag;
		this.minAttributeValue = minAttributeValue;
		this.maxAttributeValue = maxAttributeValue;

	}

	public AttributeDisplayerState(AttributeDisplayerTags displayerTag,
			String textualParameterTag, String value) {
		this.displayerTag = displayerTag;
		this.parameterTag = textualParameterTag;
		this.myTextValue = value;
	}
	
	public AttributeDisplayerTags getDisplayerTag() {
		return displayerTag;
	}
	
	public String getParameterTag() {
		return parameterTag;
	}
	
	public double getMinValue() {
		return minAttributeValue;
	}
	
	public double getMaxValue() {
		return maxAttributeValue;
	}
	
	public String getTextValue() {
		return myTextValue;
	}

}
