package engine.gameRepresentation.evaluatables.parameters.objectIdentifiers;

import java.util.List;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;

/**
 * Get all the objects corresponding to a global object identifier e.g. get all
 * the objects of type "tank"
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
