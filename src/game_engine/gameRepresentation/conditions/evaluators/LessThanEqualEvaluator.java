package game_engine.gameRepresentation.conditions.evaluators;
/**
 * 
 * @author zed
 *
 */
public class LessThanEqualEvaluator extends Evaluator {

    public LessThanEqualEvaluator () {
        super("<=");
    }

    @Override
    public boolean evaluate (double number1, double number2) {
        return number1 <= number2;
    }

}
