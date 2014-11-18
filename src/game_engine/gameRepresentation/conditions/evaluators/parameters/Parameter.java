package game_engine.gameRepresentation.conditions.evaluators.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;


/**
 * A parameter for an evaluator. Contains whatever internal logic is required for the retrieving of
 * the parameter and returns the parameter
 *
 * @author Zach
 *
 */
public interface Parameter {
    /**
     * Given an element pair, extract required parameter and return it as a string
     *
     * @param elements the element pair to act on
     * @param elementTag the tag (if applicable) of the element of interest. If not applicable, set
     *        this to null
     * @return the value of the evaluator
     */
    public String getValue (ElementPair elements,
                            String elementTag);

    /**
     * Sets the value of the given parameter. Note: modifies the actual attribute of the object
     * being referenced.
     *
     * @param elements the element pair to act on
     * @param elementTag the tag (if applicable) of the element of interest. If not applicable, set
     *        this to null
     * @param value the value to set
     * @return true if value setting was successful. False if value could not be found
     */
    public boolean setValue (ElementPair elements,
                             String elementTag,
                             String value);
}
