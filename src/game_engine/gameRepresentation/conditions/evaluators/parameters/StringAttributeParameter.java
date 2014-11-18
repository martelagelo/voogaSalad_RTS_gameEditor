package game_engine.gameRepresentation.conditions.evaluators.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.evaluators.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;
import java.util.List;


/**
 * An attribute parameter that acts on a string from the given object
 *
 * @author Zach
 *
 */
public class StringAttributeParameter extends AttributeParameter {

    public StringAttributeParameter (String attributeTag,
                                     ObjectOfInterestIdentifier objectOfInterestIdentifier) {
        super(attributeTag, objectOfInterestIdentifier);
    }

    /**
     * Return the first string with the tag from the list of elements of interest
     */
    @Override
    public String getValue (ElementPair elements,
                            GameElementManager manager,
                            String elementTag,
                            String attributeTag) {
        List<GameElementState> elementsOfInterest =
                getElementsOfInterest(manager, elements, attributeTag);
        return (elementsOfInterest.size() > 0) ? elementsOfInterest.get(0)
                .getTextualAttribute(attributeTag) : "";
    }

    @Override
    public boolean setValue (ElementPair elements,
                             GameElementManager manager,
                             String elementTag,
                             String attributeTag,
                             String value) {
        getElementsOfInterest(manager, elements, attributeTag).stream()
                .forEach(element -> element.setTextualAttribute(attributeTag, value));
        return true;
    }

}
