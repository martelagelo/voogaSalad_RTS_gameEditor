package game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers;

import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.stateManaging.GameElementManager;

import java.util.ArrayList;
import java.util.List;


/**
 * An identifier that returns the object evaluating the condition on other objects
 *
 * @author Zach
 *
 */
public class ActorObjectIdentifier implements ObjectOfInterestIdentifier {

    @Override
    public List<GameElement> getElementOfInterest (GameElementManager elementManager,
                                                        ElementPair elementPair, String elementTag) {
        List<GameElement> elementsOfInterest = new ArrayList<GameElement>();
        elementsOfInterest.add(elementPair.getActor());
        return elementsOfInterest;
    }

}
