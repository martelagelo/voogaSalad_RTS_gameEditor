package game_engine.gameRepresentation.conditions.operators;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.conditions.evaluators.EqualsEvaluator;
import game_engine.gameRepresentation.conditions.evaluators.Evaluator;


/**
 * An abstract class that performs a boolean operation on two evaluatable objects. e.g. &&, ||, !
 * and returns the result
 * 
 * @author Zach
 *
 */
public abstract class Operator implements Evaluatable {

    private Evaluatable myFirstCondition;
    private Evaluatable mySecondCondition;
    private String myOperatorString;

    /**
     * Initialize the operator with the necessary conditions
     * 
     * @param firstCondition
     * @param secondCondition
     * @param operatorString the string representation for the operator
     */
    public Operator (Evaluatable firstCondition, Evaluatable secondCondition, String operatorString) {
        myFirstCondition = firstCondition;
        mySecondCondition = secondCondition;
        myOperatorString = operatorString;
    }
    public Operator(String firstCondition, String secondCondition, String operatorString){
       //TODO figure this logic out this(createCondition(firstCondition),createCondition(secondCondition),operatorString);
    }
    /**
     * Create a condition based on the condition string
     */
    private Evaluator createCondition(String conditionString){
        //TODO implement
        return new EqualsEvaluator();
    }

    @Override
    public boolean evaluate (ElementPair elements) {
        return applyOperator(myFirstCondition.evaluate(elements),
                             mySecondCondition.evaluate(elements));
    }

    /**
     * Apply the operator's function on two booleans
     * 
     * @param boolean1 the first boolean
     * @param boolean2 the second boolean
     * @return the result of the operator being applied
     */
    protected abstract boolean applyOperator (boolean boolean1, boolean boolean2);

    @Override
    public String toString () {
        return myOperatorString;
    }

}
