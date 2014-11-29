package game_engine.gameRepresentation.conditions.evaluators.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;


/**
 * A parameter for an evaluator. Contains whatever internal logic is required for the retrieving and
 * setting of the parameter. Essentially, it provides the idea of a referenced value in our
 * Condition/Action framework. Parameters take in the currently important elements and return value
 * of the important element's parameter.
 *
 * @author Zach
 *
 */
public interface Parameter {
    /**
     * Given an element pair, extract required parameter and return it as a string
     *
     * @param elements the element pair to act on
     * @return the value of the evaluator
     */
    public String getValue (ElementPair elements);

    /**
     * Sets the value of the given parameter. Note: modifies the actual attribute of the object
     * being referenced. This is intended.
     *
     * @param elements the element pair to act on
     * @param value the value to set
     * @return true if value setting was successful. False if value could not be found
     */
    public boolean setValue (ElementPair elements,
                             String value);
}
