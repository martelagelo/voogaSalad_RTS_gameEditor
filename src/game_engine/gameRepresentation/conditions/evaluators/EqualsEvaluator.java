package game_engine.gameRepresentation.conditions.evaluators;
/**
 * An equals evaluator
 * @author Zach
 *
 */
public class EqualsEvaluator extends DoubleEvaluator{

    public EqualsEvaluator () {
        super("=");
    }

    @Override
    public boolean Evaluate (double number1, double number2) {
        return number1==number2;
    }

}
