package game_engine.gameRepresentation.conditions.evaluators.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;
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

    public NumericalAttributeParameter (String attributeTag,
                                        ObjectOfInterestIdentifier objectOfInterestIdentifier) {
        super(attributeTag, objectOfInterestIdentifier);
    }

    /**
     * Return the average value of the attribute of all the elements of interest
     */
    @Override
    public String getValue (ElementPair elements,
                            GameElementManager manager,
                            String elementTag,
                            String attributeTag) {
        List<GameElementState> elementsOfInterest =
                getElementsOfInterest(manager, elements, attributeTag);
        if (elementsOfInterest.size() == 0)
            return "0";
        double valueSum = 0.0;
        for (GameElementState element : elementsOfInterest) {
            valueSum += element.getNumericalAttribute(attributeTag).doubleValue();
        }
        return Double.toString(valueSum / elementsOfInterest.size());
    }

    @Override
    public boolean setValue (ElementPair elements,
                             GameElementManager manager,
                             String elementTag,
                             String attributeTag,
                             String value) {
        try {

            getElementsOfInterest(manager, elements, attributeTag).stream()
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
