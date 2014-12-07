package engine.gameRepresentation.renderedRepresentation.attributeDisplayer;

import java.io.Serializable;
import model.state.gameelement.StateTags;
import util.JSONable;

/**
 * A wrapper for all the savable information needed to represent an attribute
 * displayer. This is just a data wrapper and as such all of its instance
 * variables are public.
 * 
 * @author Zach, Stanley
 *
 */
public class AttributeDisplayerState implements JSONable, Serializable {

	private static final long serialVersionUID = -4791954709522087485L;
	public AttributeDisplayerType displayerTag;					//The the string describing the class needed to create a widget. Created with enum.
	public String parameterTag;					//The StateTag of the attribute or the name of the attribute
	public double minAttributeValue;			//for a numerical attribute: The minimum value 
	public double maxAttributeValue;			//for a numerical attribute: The maximum value
	public String myTextValue;					//for a textual attribute: The value

	public AttributeDisplayerState(AttributeDisplayerType displayerTag,
			String numericParameterTag, double minAttributeValue,
			double maxAttributeValue) {
		this.displayerTag = displayerTag;
		this.parameterTag = numericParameterTag;
		this.minAttributeValue = minAttributeValue;
		this.maxAttributeValue = maxAttributeValue;

	}

	public AttributeDisplayerState(AttributeDisplayerType displayerTag,
			String textualParameterTag, String value) {
		this.displayerTag = displayerTag;
		this.parameterTag = textualParameterTag;
		this.myTextValue = value;
	}

}
