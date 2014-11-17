package game_engine.gameRepresentation.conditions.evaluators;
/**
 * An equals evaluator
 * @author Zach
 *
 */
public class EqualsEvaluator extends Evaluator{

    public EqualsEvaluator () {
        super("=");
    }

    @Override
    public boolean evaluate (double number1, double number2) {
        return number1==number2;
    }
    @Override
    public boolean evaluate (Object item1, Object item2){
        return item1.equals(item2);
    }

}
