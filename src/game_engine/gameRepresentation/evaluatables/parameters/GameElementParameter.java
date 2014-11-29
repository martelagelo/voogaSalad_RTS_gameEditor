package game_engine.gameRepresentation.evaluatables.parameters;

import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.stateManaging.GameElementManager;


/**
 * A parameter that takes in an object of interest identifier and returns the
 * object that is being referenced based on the currently supplied elementPair.
 * 
 * @author Zach
 *
 */
public class GameElementParameter extends Parameter<GameElement> {

    private GameElementManager myManager;
    private ObjectOfInterestIdentifier myObjectIdentifier;
    private String myElementTag;

    /**
     * Create a parameter that references a game element
     * 
     * @param objectOfInterestIdentifier
     *        the object identifier that notified the element of what it is
     *        interested in
     * @param elementTag
     *        Optional: the tag for the element. Only used if parameter is
     *        referencing a global object
     */
    public GameElementParameter (String id,
                                 ObjectOfInterestIdentifier objectOfInterestIdentifier,
                                 String elementTag) {
        super(GameElement.class, id);
        myObjectIdentifier = objectOfInterestIdentifier;
        myElementTag = elementTag;

    }

    public GameElementParameter (String id,
                                 ObjectOfInterestIdentifier objectOfInterestIdentifier) {
        this(id, objectOfInterestIdentifier, null);
    }

    @Override
    public GameElement getValue (ElementPair elements) {
        return myObjectIdentifier.getElementOfInterest(myManager, elements,
                                                       myElementTag).get(0);
    }

    @Override
    public boolean setValue (ElementPair elements, GameElement value) {
        // A parameter does not have the ability to set a referenced element's
        // identifier
        return false;
    }

}
