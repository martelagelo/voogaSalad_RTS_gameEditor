package game_engine.gameRepresentation.conditions.evaluators;

/**
 * A less than evaluator
 * 
 * @author Zach
 *
 */
public class LessThanEvaluator extends Evaluator {

    public LessThanEvaluator () {
        super("<");
    }

    @Override
    public boolean evaluate (double number1, double number2) {
        return number1 < number2;
    }

}
