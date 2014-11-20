package game_engine.gameRepresentation.conditions.evaluators.parameters.objectIdentifiers;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;
import java.util.ArrayList;
import java.util.List;


/**
 * Use the null pattern to identify no objects of interest for parameters that are not tied to an
 * object
 *
 * @author Zach
 *
 */
public class NullObjectIdentifier implements ObjectOfInterestIdentifier {

    @Override
    public List<GameElementState> getElementOfInterest (GameElementManager elementManager,
                                                        ElementPair elementPair,
                                                        String elementTag) {
        return new ArrayList<GameElementState>();
    }

}
