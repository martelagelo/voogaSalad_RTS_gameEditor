package game_engine.gameRepresentation.conditions.referencedElementConditions;

import game_engine.gameRepresentation.conditions.Condition;
import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.evaluators.Evaluator;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;

/**
 * 
 * @author Zach
 * 
 * A condition that operates on an object that is passed in through a reference in the evaluate method.
 *
 */
public abstract class ReferencedElementCondition extends Condition {

    private String myAttributeName;
    private double myAttributeValue;
    private boolean mySelfOrOther;

    /**
     * Create a referenced element condition to evaluate the attribute with a given name with the
     * given attribute value
     * 
     * @param evaluator
     * @param attributeName
     * @param attributeValue
     * @param selfOrOther indicates whether this condition acts on the object itself or the other object
     */
    public ReferencedElementCondition (Evaluator evaluator,
                                       String attributeName,
                                       double attributeValue,
                                       boolean selfOrOther) {
        //TODO fix this self or other thing... Should find a way to combine elements into one big super class
        super(evaluator);
        myAttributeName = attributeName;
        myAttributeValue = attributeValue;
        mySelfOrOther  =selfOrOther;
    }
    @Override
    public boolean evaluate(ElementPair pair){
       return evaluate(mySelfOrOther?pair.getActor():pair.getActee());
    }
    /**
     * Evaluate the expression on the given element state
     * @param element
     * @return
     */
    protected abstract boolean evaluate(GameElementState element);
    
    protected String getAttributeName () {
        return myAttributeName;
    }

    protected double getAttributeValue () {
        return myAttributeValue;
    }

}
