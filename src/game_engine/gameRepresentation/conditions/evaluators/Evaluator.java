package game_engine.gameRepresentation.conditions.evaluators;

/**
 * An abstract class for double evaluators that acts on datatypes to provide for functionality such
 * as
 * < > <= >=. If a data type is not supported for an operation, the evaluator will evaluate to false
 * to fail silently.
 * 
 * @author Zach
 *
 */
public abstract class Evaluator {
    String myEvaluatorRepresentation;

    /**
     * Create the evaluator with the string representation of the evaluator
     * 
     * @param evaluatorRepresentation
     */
    public Evaluator (String evaluatorRepresentation) {
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
    
    public boolean Evaluate (boolean item1, boolean item2) {
        return Evaluate(item1?1:0,item2?1:0);
    }

    /**
     * Evaluate on two objects. By default returns false as only a subset of evaluators will act on
     * generic objects
     * 
     */
    public boolean Evaluate (Object item1, Object item2) {
        return false;
    }

    @Override
    public String toString () {
        return myEvaluatorRepresentation;
    }

}
