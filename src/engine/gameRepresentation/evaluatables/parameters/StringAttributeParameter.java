package engine.gameRepresentation.evaluatables.parameters;

import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;
import java.util.List;


/**
 * An attribute parameter that wraps around a string from the given object
 *
 * @author Zach
 *
 */
public class StringAttributeParameter extends AttributeParameter<String> {
    /**
     * @see AttributeParameter
     */
    public StringAttributeParameter (String attributeTag,
                                     GameElementManager manager,
                                     ObjectOfInterestIdentifier objectOfInterestIdentifier) {
        super(String.class, attributeTag, manager, objectOfInterestIdentifier);
    }

    /**
     * Return the first string with the tag from the list of elements of
     * interest
     * 
     * @see AttributeParameter#getValue
     */
    @Override
    public String getValue (List<GameElement> elements, String attributeTag) {
        return (elements.size() > 0) ? elements.get(0)
                .getTextualAttribute(attributeTag) : "";
    }

    @Override
    public boolean setValue (List<GameElement> elements, String attributeTag,
                             String value) {
        elements.forEach(element -> element
                .setTextualAttribute(attributeTag, value));
        return true;
    }

}
