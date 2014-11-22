package game_engine.gameRepresentation.conditions.parameters.objectIdentifiers;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
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
    public List<GameElementState> getElementOfInterest (GameElementManager elementManager,
                                                        ElementPair elementPair, String elementName) {
        return elementManager.findAllElementsOfType(elementName);
    }

}
