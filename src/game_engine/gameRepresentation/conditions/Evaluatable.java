package game_engine.gameRepresentation.conditions;

/**
 * An object that can evaluate a condition on an element to return a boolean value
 *
 * @author Zach
 *
 */
public interface Evaluatable {
    /**
     * Take in a pair of elements, evaluate a condition on the elements, and return a boolean
     * representing the
     * condition's return value.
     *
     */
    public boolean evaluate (ElementPair elements);

}
