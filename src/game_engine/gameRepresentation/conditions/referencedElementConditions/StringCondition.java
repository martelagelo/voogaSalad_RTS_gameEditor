package game_engine.gameRepresentation.conditions.referencedElementConditions;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.evaluators.Evaluator;
import game_engine.gameRepresentation.conditions.evaluators.evaluatorParameters.AttributeParameter;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;


/**
 * A condition that checks the value of a string to be equal to a given value
 * 
 * @author Zach
 *
 */
public class StringCondition extends AttributeParameter {

    public StringCondition (Evaluator evaluator,
                            String attributeName,
                            double attributeValue, boolean selfOrOther) {
        super(evaluator, attributeName, attributeValue, selfOrOther);
    }

    @Override
    public boolean evaluate (GameElementState element) {
        return getEvaluator().evaluate(element.getTextualAttribute(getAttributeName()),
                                       new Double(getAttributeValue()));
    }

}
