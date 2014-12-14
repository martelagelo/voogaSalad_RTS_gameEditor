package engine.gameRepresentation.evaluatables.parameters.objectIdentifiers;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;
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
    public List<GameElement> getElementOfInterest (GameElementManager elementManager,
                                                   ElementPair elementPair, String elementTag) {
        List<GameElement> elementsOfInterest = new ArrayList<GameElement>();
        elementsOfInterest.add(elementPair.getActee());
        return elementsOfInterest;
    }
}
