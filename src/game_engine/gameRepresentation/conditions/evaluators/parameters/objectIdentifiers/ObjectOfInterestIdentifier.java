package game_engine.gameRepresentation.conditions.evaluators.parameters.objectIdentifiers;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;
import java.util.List;


/**
 * An interface for a set of classes whose only functionality is to get the objects of interest for
 * creating evaluator parameters. This set of classes was created with the choosing of composition
 * over inheritance for the evaluator parameters.
 * 
 * @author Zach
 *
 */
public interface ObjectOfInterestIdentifier {
    /**
     * Return the element that this object is interested in
     * @param elementManager the manager that contains all the global objects
     * @param elementPair the object pair that the elements can act on
     * @param objectTag the optional tag of the object type for global references
     * @return the element of interest
     */
    public List<GameElementState> getElementOfInterest (GameElementManager elementManager,
                                                        ElementPair elementPair,String elementTag);

}
