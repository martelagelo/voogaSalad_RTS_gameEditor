package game_engine.gameRepresentation.conditions.evaluators;

/**
 * A greater than or equal to evaluator
 * 
 * @author Zach
 *
 */
public class GreaterThanEqualEvaluator extends DoubleEvaluator {

    public GreaterThanEqualEvaluator () {
        super(">=");

    }

    @Override
    public boolean Evaluate (double number1, double number2) {
        return number1 >= number2;
    }

}
