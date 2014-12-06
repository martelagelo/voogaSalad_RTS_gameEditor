package engine.gameRepresentation.renderedRepresentation.attributeDisplayer;

import java.io.Serializable;
import util.JSONable;

/**
 * A wrapper for all the savable information needed to represent an attribute
 * displayer. This is just a data wrapper and as such all of its instance
 * variables are public.
 * 
 * @author Zach
 *
 */
public class AttributeDisplayerState implements JSONable, Serializable {

	private static final long serialVersionUID = -4791954709522087485L;
	public String displayerTag;
	public String parameterTag;
	public double minAttributeValue;
	public double maxAttributeValue;
	public String myTextValue;

	public AttributeDisplayerState(String displayerTag,
			String numericParameterTag, double minAttributeValue,
			double maxAttributeValue) {
		this.displayerTag = displayerTag;
		this.parameterTag = numericParameterTag;
		this.minAttributeValue = minAttributeValue;
		this.maxAttributeValue = maxAttributeValue;

	}

	public AttributeDisplayerState(String displayerTag,
			String textualParameterTag, String value) {
		this.displayerTag = displayerTag;
		this.parameterTag = textualParameterTag;
		this.myTextValue = value;
	}

}
