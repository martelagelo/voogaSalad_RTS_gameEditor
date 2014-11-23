package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;

/**
 * An abstract class for evaluators that acts on parameters to provide for
 * functionality such as <, >, <=, >=, +=, getNearest, etc. All implementations
 * of evaluators must implement the evaluate method for their appropriate data
 * type, allowing for a large variety of functions to be implemented.
 *
 * @author Zach
 * @param <A>
 *            The type returned by the first parameter of the evaluatable
 * @param <B>
 *            the type returned by the second parameter of the evaluatable
 * @param <T>
 *            the type returned by the evaluatable
 */
public abstract class Evaluator<A, B, T> extends Evaluatable<T> {
	private String myEvaluatorRepresentation;
	private Evaluatable<A> myParameter1;
	private Evaluatable<B> myParameter2;

	/**
	 * Create the evaluator with the string representation of the evaluator.
	 * This string representation will be set by the children of the evaluator
	 * and will be used in the creation of evaluators by a factory.
	 *
	 * @param type
	 *            the return type of the evaluator. Used to bypass Java generic
	 *            type erasure
	 * @param evaluatorRepresentation
	 *            the representation of the evaluator e.g. "<=", "==","+="
	 * @param elementManager
	 *            the main game element manager
	 * @param parameter1
	 *            a string representation of the parameter on the left side of
	 *            the evaluator
	 * @param parameter2
	 *            a string representation of the parameter on the right side of
	 *            the evaluator
	 */
	public Evaluator(Class<T> type, String evaluatorRepresentation,
			Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
		super(type);
		myEvaluatorRepresentation = evaluatorRepresentation;
		myParameter1 = parameter1;
		myParameter2 = parameter2;

	}

	/**
	 * Evaluate on two objects. By default returns null as all child evaluate
	 * methods implements necessary class overrides.
	 *
	 * @param item1
	 *            the object on the left side of the evaluator
	 * @param item2
	 *            the object on the right side of the evaluator
	 * @return the result of the evaluation on the two objects
	 */
	protected T evaluate(Object item1, Object item2) {
		System.out.println(item1.getClass());
		System.out.println("Calling parent evaluate with objects");
		return null;
	}

	/**
	 * Evaluate on two doubles
	 */
	protected T evaluate(Double item1, Double item2) {
		return null;
	}

	/**
	 * Evaluate on two booleans
	 */
	protected T evaluate(Boolean item1, Boolean item2) {
		return null;
	}

	/**
	 * Evaluate on two elements
	 */
	protected T evaluate(GameElementState item1, GameElementState item2) {
		return null;
	}

	/**
	 * Evaluate the evaluator on the element pair
	 *
	 * @param elements
	 *            a pairing of the current element and the element that is being
	 *            examined by it
	 */
	@Override
	public T getValue(ElementPair elements) {
		A parameter1Value = myParameter1.getValue(elements);
		B parameter2Value = myParameter2.getValue(elements);
		return delegateEvaluator(parameter1Value, parameter2Value);
	}

	/**
	 * A method that delegates the public evaluate method to private evaluate
	 * methods with given casting. This method is messy but is the only way to
	 * work around generic erasure and compile-time typing by java.
	 * 
	 * @param parameter1Value
	 *            the value of the first parameter
	 * @param parameter2Value
	 *            the value of the second parameter
	 * @return the result of evaluating on the two parameters
	 */
	public T delegateEvaluator(A parameter1Value, B parameter2Value) {
		Class<A> type1 = myParameter1.getType();
		Class<B> type2 = myParameter2.getType();
		if (type1.equals(Double.class) && type2.equals(Double.class)) {
			return evaluate((Double) parameter1Value, (Double) parameter2Value);
		}
		if (type1.equals(GameElementState.class)
				&& type2.equals(GameElementState.class)) {
			return evaluate((GameElementState) parameter1Value,
					(GameElementState) parameter2Value);
		}
		if (type1.equals(Boolean.class) && type2.equals(Boolean.class)) {
			return evaluate((Boolean) parameter1Value,
					(Boolean) parameter2Value);
		}
		return evaluate(parameter1Value, parameter2Value);
	}

	/**
	 * @return the first parameter
	 */
	protected Evaluatable<A> getParameter1() {
		return myParameter1;
	}

	/**
	 * @return the second parameter
	 */
	protected Evaluatable<B> getParameter2() {
		return myParameter2;
	}

	@Override
	public String toString() {
		return myEvaluatorRepresentation;
	}

}
