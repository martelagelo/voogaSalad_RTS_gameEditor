package game_engine.gameRepresentation.conditions.evaluators;

/**
 * A greater than evaluator
 * 
 * @author Zach
 *
 */
public class GreaterThanEvaluator extends Evaluator {

    public GreaterThanEvaluator () {
        super(">");
    }

    @Override
    public boolean evaluate (double number1, double number2) {
        return number1 > number2;
    }

}
