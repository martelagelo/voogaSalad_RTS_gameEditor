package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.parameters.Parameter;
import game_engine.gameRepresentation.conditions.evaluators.parameters.ParameterFactory;
import game_engine.stateManaging.GameElementManager;


/**
 * An abstract class for double evaluators that acts on datatypes to provide for functionality such
 * as
 * < > <= >=. If a data type is not supported for an operation, the evaluator will evaluate to false
 * to fail silently.
 *
 * @author Zach
 *
 */
public abstract class Evaluator implements Evaluatable {
    String myEvaluatorRepresentation;
    private Parameter myParameter1;
    private Parameter myParameter2;

    /**
     * Create the evaluator with the string representation of the evaluator
     *
     * @param evaluatorRepresentation
     */
    public Evaluator (String evaluatorRepresentation,
                      GameElementManager elementManager,
                      String parameter1,
                      String parameter2) {
        myEvaluatorRepresentation = evaluatorRepresentation;
        ParameterFactory factory = new ParameterFactory(elementManager);
        myParameter1 = factory.createParameter(parameter1);
        myParameter2 = factory.createParameter(parameter2);

    }

    /**
     * Evaluate a condition on the two given numbers and return the result
     *
     * @param number1
     * @param number2
     * @return
     */
    protected abstract boolean evaluate (double number1, double number2);

    /**
     * Evaluate on two objects. By default returns false as only a subset of evaluators will act on
     * generic objects
     *
     */
    protected boolean evaluate (Object item1, Object item2) {
        return false;
    }

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
