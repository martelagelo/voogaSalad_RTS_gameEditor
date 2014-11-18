package game_engine.gameRepresentation.conditions.evaluators.evaluatorParameters;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.stateManaging.GameElementManager;


/**
 * A parameter for an evaluator. Contains whatever internal logic is required for the retrieving of
 * the parameter and returns the parameter
 * 
 * @author Zach
 *
 */
public interface EvaluatorParameter {
    /**
     * Given an element pair, extract required parameter and return it as a string
     * 
     * @param elements the element pair to act on
     * @param manager the gameElementManager containing all the game elements
     * @param elementTag the tag (if applicable) of the element of interest. If not applicable, set this to null
     * @param attributeTag the tag of the attribute of interest
     * @return the value of the evaluator
     */
    public String getValue (ElementPair elements,
                            GameElementManager manager,
                            String elementTag,
                            String attributeTag);

}
