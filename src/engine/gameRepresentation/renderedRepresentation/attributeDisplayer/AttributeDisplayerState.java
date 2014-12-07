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
	private AttributeDisplayerTags displayerTag;
	private String parameterTag;
	private double minAttributeValue;
	private double maxAttributeValue;
	private String myTextValue;
	private String BackgroundColor;
	private String ForegroundColor;

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
	public AttributeDisplayerState(AttributeDisplayerTags displayerTag,
			String numericParameterTag, double minAttributeValue,
			double maxAttributeValue, String BackgroundColor, String ForegroundColor) {
		this.displayerTag = displayerTag;
		this.parameterTag = numericParameterTag;
		this.minAttributeValue = minAttributeValue;
		this.maxAttributeValue = maxAttributeValue;
		this.BackgroundColor = BackgroundColor;
		this.ForegroundColor = ForegroundColor;
	}
	
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
	
	public String getBGColor() {
		return BackgroundColor;
	}
	
	public String getFGColor() {
		return ForegroundColor;
	}

}
