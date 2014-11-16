package game_engine.gameRepresentation.conditions.evaluators;

/**
 * A less than evaluator
 * 
 * @author Zach
 *
 */
public class LessThanEvaluator extends DoubleEvaluator {

    public LessThanEvaluator () {
        super("<");
    }

    @Override
    public boolean Evaluate (double number1, double number2) {
        return number1 < number2;
    }

}
