package engine.gameRepresentation.evaluatables.parameters;

import java.util.List;

import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;

/**
 * An attribute parameter that acts on a numerical value from the given object
 *
 * @author Zach
 *
 */
public class NumericAttributeParameter extends AttributeParameter<Number> {
    /**
     * @see AttriubteParameter
     */
    public NumericAttributeParameter (String attributeTag, GameElementManager elementManager,
            ObjectOfInterestIdentifier objectOfInterestIdentifier) {
        super(Number.class, attributeTag, elementManager, objectOfInterestIdentifier);
    }

    /**
     * Return the average value of the attribute of all the elements of interest
     *
     * @see AttributeParameter#getValue
     */
    @Override
    public Number getValue (List<GameElement> elements, String attributeTag) {
        if (elements.size() == 0) {
            return 0d;
        }
        double valueSum = 0.0;
        for (GameElement element : elements) {
            valueSum += element.getNumericalAttribute(attributeTag).doubleValue();
        }
        return valueSum / elements.size();
    }

    /**
     * Set the value of each of the attributes to the given value
     *
     * @see AttributeParameter#setValue
     */
    @Override
    public boolean setValue (List<GameElement> elements, String attributeTag, Number value) {
        elements.stream().forEach(element -> element.setNumericalAttribute(attributeTag, value));
        return true;
    }

}
