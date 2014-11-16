package game_engine.gameRepresentation.conditions.referencedElementConditions;

import game_engine.gameRepresentation.conditions.Condition;
import game_engine.gameRepresentation.conditions.evaluators.Evaluator;

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

    /**
     * Create a referenced element condition to evaluate the attribute with a given name with the
     * given attribute value
     * 
     * @param evaluator
     * @param attributeName
     * @param attributeValue
     */
    public ReferencedElementCondition (Evaluator evaluator,
                                       String attributeName,
                                       double attributeValue) {
        super(evaluator);
        myAttributeName = attributeName;
        myAttributeValue = attributeValue;
    }

    protected String getAttributeName () {
        return myAttributeName;
    }

    protected double getAttributeValue () {
        return myAttributeValue;
    }

}
