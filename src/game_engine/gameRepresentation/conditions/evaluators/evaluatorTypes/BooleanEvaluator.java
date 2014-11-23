package game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes;

/**
 * An evaluator that acts on booleans
 * 
 * @author Zach
 *
 * @param <T>
 *            the return type of the evaluator
 */
public interface BooleanEvaluator<T> {
	public T evaluate(Boolean param1, Boolean param2);
}