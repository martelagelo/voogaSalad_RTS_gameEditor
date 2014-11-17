package game_engine.gameRepresentation.conditions;

/**
 * An object that can evaluate a condition on an element to return a boolean value
 *
 * @author Zach
 *
 */
public interface Evaluatable {
    /**
     * Evaluate and return a boolean of the result
     *
     * @param element an optional element for the Evaluatable to evaluate on.
     */
    // TODO implement the idea of null elements to allow this to evaluate on standard elements
    public boolean evaluate (ElementPair elements);

}
