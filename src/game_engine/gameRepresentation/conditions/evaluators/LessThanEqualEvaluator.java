package game_engine.gameRepresentation.conditions.evaluators;

public class LessThanEqualEvaluator extends DoubleEvaluator{

    public LessThanEqualEvaluator (String evaluatorRepresentation) {
        super(evaluatorRepresentation);
    }

    @Override
    public boolean Evaluate (double number1, double number2) {
        return number1<=number2;
    }

}
