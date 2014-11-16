package game_engine.gameRepresentation.conditions.evaluators;

/**
 * A not equals evaluator
 * 
 * @author Zach
 *
 */
public class NotEqualsEvaluator extends Evaluator {
    public NotEqualsEvaluator () {
        super("!=");
    }

    @Override
    public boolean Evaluate (double number1, double number2) {
        return number1 != number2;
    }

    @Override
    public boolean Evaluate (Object item1, Object item2) {
        return !item1.equals(item2);
    }

}
