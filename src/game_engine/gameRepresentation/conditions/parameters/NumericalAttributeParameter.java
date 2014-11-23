package game_engine.gameRepresentation.conditions.parameters;

import game_engine.gameRepresentation.conditions.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;

import java.util.List;

/**
 * An attribute parameter that acts on a numerical value from the given object
 *
 * @author Zach
 *
 */
public class NumericalAttributeParameter extends AttributeParameter<Double> {
	/**
	 * @see AttriubteParameter
	 */
	public NumericalAttributeParameter(String attributeTag,
			GameElementManager elementManager,
			ObjectOfInterestIdentifier objectOfInterestIdentifier) {
		super(Double.class, attributeTag, elementManager,
				objectOfInterestIdentifier);
	}

	/**
	 * Return the average value of the attribute of all the elements of interest
	 * 
	 * @see AttributeParameter#getValue
	 */
	@Override
	public Double getValue(List<GameElementState> elements, String attributeTag) {
		if (elements.size() == 0)
			return 0d;
		double valueSum = 0.0;
		for (GameElementState element : elements) {
			valueSum += element.getNumericalAttribute(attributeTag)
					.doubleValue();
		}
		return valueSum / elements.size();
	}

	/**
	 * Set the value of each of the attributes to the given value
	 * 
	 * @see AttributeParameter#setValue
	 */
	@Override
	public boolean setValue(List<GameElementState> elements,
			String attributeTag, Double value) {
		elements.stream().forEach(
				element -> element.setNumericalAttribute(attributeTag, value));
		return true;
	}

}
