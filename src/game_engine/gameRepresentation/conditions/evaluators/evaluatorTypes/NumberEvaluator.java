package game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes;

/**
 * An evaluator that acts on numbers
 * 
 * @author Zach
 *
 * @param <T>
 *            the return type of the evaluator
 */
public interface NumberEvaluator<T> {
	public T evaluate(Number num1, Number num2);
}
