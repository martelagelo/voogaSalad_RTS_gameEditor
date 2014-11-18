package game_engine.gameRepresentation.conditions.evaluators.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.evaluators.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;
import java.util.List;


/**
 * An evaluator parameter that extracts a parameter contained within an object
 *
 * @author Zach
 *
 */
public abstract class AttributeParameter implements Parameter {

    private String myAttributeTag;
    private ObjectOfInterestIdentifier myObjectIdentifier;
    private GameElementManager myManager;

    /**
     * Make an attribute parameter with the given tag
     *
     * @param attributeTag the tag of the attribute that will be targeted
     * @param manager the GameElementManager containing all the game elements
     * @param objectOfInterestIdentifier an identifier that will return an object of interest
     */
    public AttributeParameter (String attributeTag,
                               GameElementManager manager,
                               ObjectOfInterestIdentifier objectOfInterestIdentifier) {
        myAttributeTag = attributeTag;
        myManager = manager;
        myObjectIdentifier = objectOfInterestIdentifier;
    }

    /**
     * Get the tag for the attribute this references
     *
     * @return the attribute tag
     */
    protected String getTag () {
        return myAttributeTag;
    }

    /**
     * Get the elements of interest from the object of interest identifier contained within the
     * AttributeParameter
     *
     * @param elementManager
     * @param elementPair
     * @param elementTag
     * @return
     */
    private List<GameElementState> getElementsOfInterest (GameElementManager elementManager,
                                                          ElementPair elementPair,
                                                          String elementTag) {
        return myObjectIdentifier.getElementOfInterest(elementManager, elementPair, elementTag);
    }

    @Override
    public String getValue (ElementPair elements,
                            String elementTag) {
        return getValue(getElementsOfInterest(myManager, elements, elementTag), myAttributeTag);
    }

    /**
     * Get the desired value given the current list of elements of interest
     *
     * @return
     */
    protected abstract String getValue (List<GameElementState> elements, String attributeTag);

    @Override
    public boolean setValue (ElementPair elements, String elementTag, String attributeValue) {
        return setValue(getElementsOfInterest(myManager, elements, elementTag), myAttributeTag,
                        attributeValue);
    }

    /**
     * Set the referenced value given the current list of elements of interest
     */
    protected abstract boolean setValue (List<GameElementState> elements,
                                         String attributeTag,
                                         String attributeValue);
}
