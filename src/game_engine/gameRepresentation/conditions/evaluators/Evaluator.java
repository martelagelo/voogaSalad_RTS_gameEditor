package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.Evaluatable;

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
	 * Evaluate on two objects. By default returns null.
	 *
	 * @param item1
	 *            the object on the left side of the evaluator
	 * @param item2
	 *            the object on the right side of the evaluator
	 * @return the result of the evaluation on the two objects
	 */
	public T evaluate(Object item1, Object item2) {
		System.out.println("Calling parent evaluate with objects");
		return null;
	}

	public T evaluate(Double item1, Double item2) {
		System.out.println("Calling double parent value");
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
	public T evaluate(ElementPair elements) {
		A parameter1Value = myParameter1.evaluate(elements);
		B parameter2Value = myParameter2.evaluate(elements);
		System.out.println(parameter1Value.getClass());
		return evaluate(parameter1Value, parameter2Value);
	}

	@Override
	public String toString() {
		return myEvaluatorRepresentation;
	}

}
