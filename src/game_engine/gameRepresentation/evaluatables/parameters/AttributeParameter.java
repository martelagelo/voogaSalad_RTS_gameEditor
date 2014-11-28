package game_engine.gameRepresentation.evaluatables.parameters;

import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.stateManaging.GameElementManager;
import java.util.List;


/**
 * An evaluator parameter that extracts a parameter contained within an object
 *
 * @author Zach
 *
 */
public abstract class AttributeParameter<T> extends Parameter<T> {

    private String myAttributeTag;
    private String myElementTag;
    private ObjectOfInterestIdentifier myObjectIdentifier;
    private GameElementManager myManager;

    /**
     * Make an attribute parameter targeting parameters with a given tag.
     *
     * @param attributeTag
     *        the tag of the attribute that will be targeted
     * @param manager
     *        the GameElementManager containing all the game elements
     * @param objectOfInterestIdentifier
     *        an identifier that will return an object of interest
     */
    public AttributeParameter (Class<T> type, String attributeTag,
                               GameElementManager manager,
                               ObjectOfInterestIdentifier objectOfInterestIdentifier) {
        super(type);
        myAttributeTag = attributeTag;
        myManager = manager;
        myObjectIdentifier = objectOfInterestIdentifier;
        myElementTag = "";
    }

    /**
     * Specify an attribute parameter with an element tag. Used for referencing
     * global variables
     * 
     * @param attributeTag
     * @param manager
     * @param objectOfInterestIdentifier
     * @param elementTag
     */
    public AttributeParameter (Class<T> type, String attributeTag,
                               GameElementManager manager,
                               ObjectOfInterestIdentifier objectOfInterestIdentifier,
                               String elementTag) {
        this(type, attributeTag, manager, objectOfInterestIdentifier);
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
     * Get the elements of interest from the object of interest identifier
     * contained within the AttributeParameter
     *
     * @param elementManager
     *        the object holding all the current game elements
     * @param elementPair
     *        a pair of the game elements that the player is currently
     *        interested in
     * @param elementTag
     *        the tag of an element that the player is interested. Optional.
     *        Only really used for globals
     * @return
     */
    private List<GameElement> getElementsOfInterest (
                                                     GameElementManager elementManager,
                                                     ElementPair elementPair,
                                                     String elementTag) {
        return myObjectIdentifier.getElementOfInterest(elementManager,
                                                       elementPair, elementTag);
    }

    @Override
    public T getValue (ElementPair elements) {
        return getValue(
                        getElementsOfInterest(myManager, elements, myElementTag),
                        myAttributeTag);
    }

    /**
     * Get the desired value given the current list of elements of interest.
     * 
     * @param elements
     *        the elements whose values need to be examined
     * @param attributeTag
     *        the tag for the attribute that is being referenced by the
     *        parameter
     * @return the value of the parameter that is being requested
     */
    protected abstract T getValue (List<GameElement> elements,
                                   String attributeTag);

    @Override
    public boolean setValue (ElementPair elements, T attributeValue) {
        return setValue(
                        getElementsOfInterest(myManager, elements, myElementTag),
                        myAttributeTag, attributeValue);
    }

    /**
     * Set the referenced value given the current list of elements of interest
     * 
     * @param elements
     *        the current list of elements of interest
     * @param attributeTag
     *        the tag for the attribute to set
     * @param attributeValue
     *        the new value of the attribute
     */
    protected abstract boolean setValue (List<GameElement> elements,
                                         String attributeTag, T attributeValue);
}
