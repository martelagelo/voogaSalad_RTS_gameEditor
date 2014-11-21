package game_engine.gameRepresentation.conditions.evaluators.parameters.objectIdentifiers;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;
import java.util.ArrayList;
import java.util.List;


/**
 * Returns the actee of a given condition (i.e. the object that the currently selected object is
 * examining)
 *
 * @author Zach
 *
 */
public class ActeeObjectIdentifier implements ObjectOfInterestIdentifier {

    @Override
    public List<GameElementState> getElementOfInterest (GameElementManager elementManager,
                                                        ElementPair elementPair, String elementTag) {
        List<GameElementState> elementsOfInterest = new ArrayList<GameElementState>();
        elementsOfInterest.add(elementPair.getActee());
        return elementsOfInterest;
    }
}
