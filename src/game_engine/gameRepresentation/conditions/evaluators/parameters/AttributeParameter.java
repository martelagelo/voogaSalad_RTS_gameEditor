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
    private String myElementTag;
    private ObjectOfInterestIdentifier myObjectIdentifier;
    private GameElementManager myManager;

    /**
     * Make an attribute parameter targeting parameters with a given tag.
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
        myElementTag = "";
    }

    /**
     * Specify an attribute parameter with an element tag. Used for referencing global variables
     * 
     * @param attributeTag
     * @param manager
     * @param objectOfInterestIdentifier
     * @param elementTag
     */
    public AttributeParameter (String attributeTag,
                               GameElementManager manager,
                               ObjectOfInterestIdentifier objectOfInterestIdentifier,
                               String elementTag) {
        this(attributeTag, manager, objectOfInterestIdentifier);
        myElementTag = elementTag;
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
     * @param elementManager the object holding all the current game elements
     * @param elementPair a pair of the game elements that the player is currently interested in
     * @param elementTag the tag of an element that the player is interested. Optional. Only really
     *        used for globals
     * @return
     */
    // TODO make this take in fewer parameters
    private List<GameElementState> getElementsOfInterest (GameElementManager elementManager,
                                                          ElementPair elementPair,
                                                          String elementTag) {
        return myObjectIdentifier.getElementOfInterest(elementManager, elementPair, elementTag);
    }

    @Override
    public String getValue (ElementPair elements) {
        return getValue(getElementsOfInterest(myManager, elements, myElementTag), myAttributeTag);
    }

    /**
     * Get the desired value given the current list of elements of interest.
     * 
     * @param elements the elements whose values need to be examined
     * @param attributeTag the tag for the attribute that is being referenced by the parameter
     * @return the value of the parameter that is being requested
     */
    protected abstract String getValue (List<GameElementState> elements, String attributeTag);

    @Override
    public boolean setValue (ElementPair elements, String attributeValue) {
        return setValue(getElementsOfInterest(myManager, elements, myElementTag), myAttributeTag,
                        attributeValue);
    }

    /**
     * Set the referenced value given the current list of elements of interest
     * 
     * @param elements the current list of elements of interest
     * @param attributeTag the tag for the attribute to set
     * @param attributeValue the new value of the attribute
     */
    protected abstract boolean setValue (List<GameElementState> elements,
                                         String attributeTag,
                                         String attributeValue);
}
