package game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers;

import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.stateManaging.GameElementManager;

import java.util.List;


/**
 * Get all the objects corresponding to a global object identifier e.g. all the objects of type
 * "tank"
 * 
 * @author Zach
 *
 */
public class GlobalObjectIdentifier implements ObjectOfInterestIdentifier {

    @Override
    public List<GameElement> getElementOfInterest (GameElementManager elementManager,
                                                        ElementPair elementPair, String elementName) {
        return elementManager.findAllElementsOfType(elementName);
    }

}