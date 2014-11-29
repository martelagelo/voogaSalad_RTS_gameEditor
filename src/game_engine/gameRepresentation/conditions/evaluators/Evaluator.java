package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.parameters.Parameter;
import game_engine.gameRepresentation.conditions.evaluators.parameters.ParameterFactory;
import game_engine.stateManaging.GameElementManager;


/**
 * An abstract class for evaluators that acts on parameters to provide for functionality such
 * as
 * < > <= >=. If a data type is not supported for an operation, the evaluator will evaluate to false
 * to fail silently in the case of improperly formatted input.
 *
 * @author Zach
 *
 */
public abstract class Evaluator implements Evaluatable {
    String myEvaluatorRepresentation;
    private Parameter myParameter1;
    private Parameter myParameter2;

    /**
     * Create the evaluator with the string representation of the evaluator. This string
     * representation will be set by the children of the evaluator and will be used in the creation
     * of evaluators by a factory.
     *
     * @param evaluatorRepresentation the representation of the evaluator e.g. "<=", "==","+="
     * @param elementManager the main game element manager
     * @param parameter1 a string representation of the parameter on the left side of the evaluator
     * @param parameter2 a string representation of the parameter on the right side of the evaluator
     */
    public Evaluator (String evaluatorRepresentation,
                      GameElementManager elementManager,
                      String parameter1,
                      String parameter2) {
        myEvaluatorRepresentation = evaluatorRepresentation;
        new ParameterFactory(elementManager);

    }

    /**
     * Evaluate a condition on the two given numbers and return the result
     *
     * @param number1 the leftmost number
     * @param number2 the rightmost number
     * @return the result of the evaluation on the numbers
     */
    protected abstract boolean evaluate (double number1, double number2);

    /**
     * Evaluate on two objects. By default returns false as only a subset of evaluators will act on
     * generic objects
     *
     * @param item1 the object on the left side of the evaluator
     * @param item2 the object on the right side of the evaluator
     * @return the result of the evaluation on the two objects
     */
    protected boolean evaluate (Object item1, Object item2) {
        return false;
    }

    /**
     * Evaluate the evaluator on the element pair
     *
     * @param elements a pairing of the current element and the element that is being examined by it
     */
    @Override
    public boolean evaluate (ElementPair elements) {
        String parameter1Value = myParameter1.getValue(elements);
        String parameter2Value = myParameter2.getValue(elements);
        // Attempt to evaluate the element pair values as numbers
        try {
            return evaluate(Double.valueOf(parameter1Value), Double.valueOf(parameter2Value));
        }
        catch (NumberFormatException e) {
            // If the string is not a double, evaluate it as a generic object. This should not slow
            // the program down significantly as 90% of parameters we intend to deal with are
            // doubles
            return evaluate(parameter1Value, parameter2Value);
        }
    }

    @Override
    public String toString () {
        return myEvaluatorRepresentation;
    }

}
