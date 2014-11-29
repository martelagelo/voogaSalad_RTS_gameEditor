package game_engine.gameRepresentation.conditions.evaluators.parameters;

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
    /**
     * @see AttributeParameter
     */
    public StringAttributeParameter (String attributeTag, GameElementManager manager,
                                     ObjectOfInterestIdentifier objectOfInterestIdentifier) {
        super(attributeTag, manager, objectOfInterestIdentifier);
    }

    /**
     * Return the first string with the tag from the list of elements of interest
     * 
     * @see AttributeParameter#getValue
     */
    @Override
    public String getValue (List<GameElementState> elements,
                            String attributeTag) {
        return (elements.size() > 0) ? elements.get(0)
                .getTextualAttribute(attributeTag) : "";
    }

    @Override
    public boolean setValue (List<GameElementState> elements,
                             String attributeTag,
                             String value) {
        elements
                .forEach(element -> element.setTextualAttribute(attributeTag, value));
        return true;
    }

}
