package game_engine.gameRepresentation.conditions.evaluators.evaluatorParameters;

import game_engine.gameRepresentation.conditions.ElementPair;

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
     * @param elements
     * @return
     */
    public String getValue(ElementPair elements);

}
