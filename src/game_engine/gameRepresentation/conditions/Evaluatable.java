package game_engine.gameRepresentation.conditions;

/**
 * An object that can evaluate a condition on an element to return a value.
 * Evaluators are generics to allow for the implementation of complex logic with
 * evaluatables.
 *
 * @author Zach
 *
 */
public abstract class Evaluatable<T> {
	private Class<T> myType;

	/**
	 * Create an evaluatable
	 * 
	 * @param type
	 *            the type of the class T of the evaluatable. Used as a
	 *            workaround for the poor implementation of generics by Java
	 */
	public Evaluatable(Class<T> type) {
		myType = type;
	}

	/**
	 * Take in a pair of elements, evaluate a condition on the elements, and
	 * return a boolean representing the condition's return value.
	 *
	 */
	public abstract T evaluate(ElementPair elements);

	/**
	 * Use the null pattern to evaluate a condition if no elements are given
	 */
	public T evaluate() {
		return evaluate(new NullElementPair());
	}

	/**
	 * Get the type of the object as a way of bypassing Java type erasure
	 * 
	 * @return the generic's type
	 */
	public Class<T> getType() {
		return myType;
	}
}
