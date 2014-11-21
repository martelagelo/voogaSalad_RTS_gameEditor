package game_engine.gameRepresentation.conditions.evaluators.parameters;

import game_engine.gameRepresentation.conditions.evaluators.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;
import java.util.List;


/**
 * An attribute parameter that acts on a numerical value from the given object
 *
 * @author Zach
 *
 */
public class NumericalAttributeParameter extends AttributeParameter {
    /**
     *  @see AttriubteParameter
     */
    public NumericalAttributeParameter (String attributeTag, GameElementManager elementManager,
                                        ObjectOfInterestIdentifier objectOfInterestIdentifier) {
        super(attributeTag, elementManager, objectOfInterestIdentifier);
    }

    /**
     * Return the average value of the attribute of all the elements of interest
     * @see AttributeParameter#getValue
     */
    @Override
    public String getValue (List<GameElementState> elements,
                            String attributeTag) {
        if (elements.size() == 0)
            return "0";
        double valueSum = 0.0;
        for (GameElementState element : elements) {
            valueSum += element.getNumericalAttribute(attributeTag).doubleValue();
        }
        return Double.toString(valueSum / elements.size());
    }
    /**
     * Set the value of each of the attributes to the given value
     * @see AttributeParameter#setValue
     */
    @Override
    public boolean setValue (List<GameElementState> elements,
                             String attributeTag,
                             String value) {
        try {

            elements.stream()
                    .forEach(element -> element
                            .setNumericalAttribute(attributeTag,
                                                   new Double(value)));
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

}
