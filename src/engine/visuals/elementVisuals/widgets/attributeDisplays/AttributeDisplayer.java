package engine.visuals.elementVisuals.widgets.attributeDisplays;

import java.util.function.Consumer;
import javafx.scene.Group;
import javafx.scene.Node;
import model.state.gameelement.AttributeContainer;
import model.state.gameelement.StateTags;
import model.state.gameelement.traits.Updatable;
import engine.visuals.elementVisuals.widgets.Widget;

/**
 * A parent class for a basic visual element that displays an attribute of an
 * element and updates its visual representation.
 * 
 * @author Zach, Stanley
 *
 */
public abstract class AttributeDisplayer implements Updatable, Widget {

	protected Group myGroup;
	private AttributeContainer attributesOfInterest;
	private String myParameterTag;
	private double myMinValue;
	private double myMaxValue;
	private String myTextValue;

	/**
	 * Create an attribute displayer
	 * 
	 * @param attributes
	 *            the element whose attribute will be reflected
	 * @param numericParameterTag
	 *            the tag of the numeric parameter to display
	 * @param minValue
	 *            the min value of the parameter
	 * @param maxValue
	 *            the max value of the parameter
	 */
	public AttributeDisplayer(AttributeContainer attributes,
			String numericParameterTag, double minValue, double maxValue) {
		myGroup = createDisplay();
		attributesOfInterest = attributes;
		myParameterTag = numericParameterTag;
		myMinValue = minValue;
		myMaxValue = maxValue;
	}

	public AttributeDisplayer(AttributeContainer attributes,
			String numericParameterTag, String value) {
		myGroup = createDisplay();
		attributesOfInterest = attributes;
		myParameterTag = numericParameterTag;
		myTextValue = value;
	}

	@Override
	public void registerAsComponent(Consumer<Node> registeringFunction) {
		registeringFunction.accept(myGroup);
	}

	@Override
	public boolean update() {
		toggleOpacity();
		updateDisplay(attributesOfInterest
				.getNumericalAttribute(myParameterTag).doubleValue(),
				myMinValue, myMaxValue);
		return true;
	}

	/**
	 * Hide the displayable widget if the player is not selected
	 */
	protected void toggleOpacity() {
		if (attributesOfInterest.getNumericalAttribute(StateTags.IS_SELECTED.getValue())
				.intValue() == 1) {
			myGroup.setVisible(true);
		} else {
			myGroup.setVisible(false);
		}
	}

	/**
	 * Create the display for the graphical element
	 * 
	 * @return a group containing the visual representation for the element
	 */
	protected abstract Group createDisplay();

	/**
	 * Update the display of the attribute displayer
	 * 
	 * @param attributeValue
	 *            the attribute's value
	 * @param attributeMinValue
	 *            the minimum value of the attribute
	 * @param attributeMaxValue
	 *            the maximum value of the attribute
	 */
	protected abstract void updateDisplay(double attributeValue,
			double attributeMinValue, double attributeMaxValue);
}
