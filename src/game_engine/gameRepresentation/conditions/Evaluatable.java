package game_engine.gameRepresentation.conditions;

/**
 * An object that can evaluate a condition on an element to return a value. Evaluators are generics to allow for the 
 * implementation of complex logic with evaluatables.
 *
 * @author Zach
 *
 */
public interface Evaluatable <T> {
    /**
     * Take in a pair of elements, evaluate a condition on the elements, and return a boolean
     * representing the
     * condition's return value.
     *
     */
    public T evaluate (ElementPair elements);
    /**
     * Use the null pattern to evaluate a condition if no elements are given
     */
    default T evaluate() {
		return evaluate(new NullElementPair());
	}

}
