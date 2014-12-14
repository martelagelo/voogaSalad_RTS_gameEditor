package engine.gameRepresentation.evaluatables.parameters.objectIdentifiers;

import java.util.ArrayList;
import java.util.List;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;

/**
 * Use the null pattern to identify no objects of interest for parameters that
 * are not tied to an object. Useful for stuff like getting a game time
 * parameter or goal attributes, etc.
 *
 * @author Zach
 *
 */
public class NullObjectIdentifier implements ObjectOfInterestIdentifier {

    @Override
    public List<GameElement> getElementOfInterest (GameElementManager elementManager,
            ElementPair elementPair, String elementTag) {
        return new ArrayList<>();
    }

}
