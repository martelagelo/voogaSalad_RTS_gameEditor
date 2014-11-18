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

    /**
     * Make an attribute parameter with the given tag
     *
     * @param attributeTag the tag of the attribute that will be targeted
     * @param objectOfInterestIdentifier an identifier that will return an object of interest
     */
    public AttributeParameter (String attributeTag,
                               ObjectOfInterestIdentifier objectOfInterestIdentifier) {
        myAttributeTag = attributeTag;
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

    protected List<GameElementState> getElementsOfInterest(GameElementManager elementManager, ElementPair elementPair, String elementTag){
        return myObjectIdentifier.getElementOfInterest(elementManager, elementPair, elementTag);
    }
}
