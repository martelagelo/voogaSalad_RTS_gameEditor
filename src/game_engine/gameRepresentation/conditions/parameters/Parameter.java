package game_engine.gameRepresentation.conditions.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.Evaluatable;

/**
 * A parameter for an evaluator. Contains whatever internal logic is required
 * for the retrieving and setting of the parameter. Essentially, it provides the
 * idea of a referenced value in our Condition/Action framework. Parameters take
 * in the currently important elements and return value of the important
 * element's parameter.
 *
 * @author Zach
 *
 */
public abstract class Parameter<T> extends Evaluatable<T> {

	/**
	 * Create a parameter. Note that the type of the generic was included in the
	 * constructor as the Java-approved workaround for the fact that java
	 * generics are not refied at runtime. This allows Evaluators further up the
	 * tree to be instantiated properly with the correct parameter types without
	 * propogating generic uncertainty.
	 * 
	 * @param type
	 *            is the type of this generic
	 */
	public Parameter(Class<T> type) {
		super(type);
	}

	/**
	 * Given an element pair, extract required parameter and return it. Uses
	 * evaluatable methods to ensure compliance with the composite pattern.
	 *
	 * @param elements
	 *            the element pair to act on
	 * @return the value of the evaluator
	 */
	@Override
	public abstract T getValue(ElementPair elements);

	/**
	 * Sets the value of the given parameter. Note: modifies the actual
	 * attribute of the object being referenced. This is intended.
	 *
	 * @param elements
	 *            the element pair to act on
	 * @param value
	 *            the value to set
	 * @return true if value setting was successful. False if value could not be
	 *         found
	 */
	@Override
	public abstract boolean setValue(ElementPair elements, T value);

}
