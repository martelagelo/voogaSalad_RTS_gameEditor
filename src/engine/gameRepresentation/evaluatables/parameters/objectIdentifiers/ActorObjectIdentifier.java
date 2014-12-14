package engine.gameRepresentation.evaluatables.parameters.objectIdentifiers;

import java.util.ArrayList;
import java.util.List;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;

/**
 * An identifier that returns the object evaluating the condition on other
 * objects
 *
 * @author Zach
 *
 */
public class ActorObjectIdentifier implements ObjectOfInterestIdentifier {

    @Override
    public List<GameElement> getElementOfInterest (GameElementManager elementManager,
            ElementPair elementPair, String elementTag) {
        List<GameElement> elementsOfInterest = new ArrayList<>();
        elementsOfInterest.add(elementPair.getActor());
        return elementsOfInterest;
    }

}
