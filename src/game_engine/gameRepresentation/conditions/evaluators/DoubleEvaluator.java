package game_engine.gameRepresentation.conditions.evaluators;

/**
 * An abstract class for double evaluators that acts on doubles to provide for functionality such as
 * < > <= >=
 * 
 * @author Zach
 *
 */
public abstract class DoubleEvaluator {
    String myEvaluatorRepresentation;

    /**
     * Create the evaluator with the string representation of the evaluator
     * 
     * @param evaluatorRepresentation
     */
    public DoubleEvaluator (String evaluatorRepresentation) {
        myEvaluatorRepresentation = evaluatorRepresentation;
    }

    /**
     * Evaluate a condition on the two given numbers and return the result
     * 
     * @param number1
     * @param number2
     * @return
     */
    public abstract boolean Evaluate (double number1, double number2);

    public boolean Evaluate (int number1, int number2) {
        return Evaluate(number1 * 1.0, number2 * 1.0);
    }

    public boolean Evaluate (int number1, double number2) {
        return Evaluate(number1 * 1.0, number2);
    }

    public boolean Evaluate (double number1, int number2) {
        return Evaluate(number1, number2 * 1.0);
    }

    @Override
    public String toString () {
        return myEvaluatorRepresentation;
    }

}
