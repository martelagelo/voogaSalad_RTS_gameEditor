package game_engine.gameRepresentation.conditions.evaluators.parameters.objectIdentifiers;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;
import java.util.ArrayList;
import java.util.List;
/**
 * An identifier that returns the object evaluating the condition
 * @author Zach
 *
 */
public class ActorObjectIdentifier implements ObjectOfInterestIdentifier{

    @Override
    public List<GameElementState> getElementOfInterest (GameElementManager elementManager,
                                                        ElementPair elementPair, String elementTag) {
        List<GameElementState> elementsOfInterest = new ArrayList<GameElementState>();
        elementsOfInterest.add(elementPair.getActor());
        return elementsOfInterest;
    }

}
