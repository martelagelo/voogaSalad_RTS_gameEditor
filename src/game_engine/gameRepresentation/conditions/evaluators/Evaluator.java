package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.parameters.ParameterFactory;
import game_engine.gameRepresentation.conditions.evaluators.parameters.Parameter;


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
    public Evaluator (String evaluatorRepresentation,  String parameter1, String parameter2) {
        myEvaluatorRepresentation = evaluatorRepresentation;
        ParameterFactory factory = new ParameterFactory();
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

    private boolean evaluate (int number1, int number2) {
        return evaluate(number1 * 1.0, number2 * 1.0);
    }

    private boolean evaluate (int number1, double number2) {
        return evaluate(number1 * 1.0, number2);
    }

    private boolean evaluate (double number1, int number2) {
        return evaluate(number1, number2 * 1.0);
    }

    private boolean evaluate (boolean item1, boolean item2) {
        return evaluate(item1 ? 1 : 0, item2 ? 1 : 0);
    }

    /**
     * Evaluate on two objects. By default returns false as only a subset of evaluators will act on
     * generic objects
     * 
     */
    protected boolean evaluate (Object item1, Object item2) {
        return false;
    }
    @Override
    public boolean evaluate (ElementPair elements){
       return evaluate(myParameter1.getValue(elements),myParameter2.getValue(elements));
    }

    @Override
    public String toString () {
        return myEvaluatorRepresentation;
    }

}
