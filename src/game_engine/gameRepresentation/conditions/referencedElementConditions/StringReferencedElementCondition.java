package game_engine.gameRepresentation.conditions.referencedElementConditions;

import game_engine.gameRepresentation.conditions.evaluators.Evaluator;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
/**
 * A condition that checks the value of a string to be equal to a given value
 * 
 * @author Zach
 *
 */
public class StringReferencedElementCondition extends ReferencedElementCondition{

    public StringReferencedElementCondition (Evaluator evaluator,
                                             String attributeName,
                                             double attributeValue) {
        super(evaluator, attributeName, attributeValue);
    }

    @Override
    public boolean evaluate (GameElementState element) {
       return getEvaluator().evaluate(element.getTextualAttribute(getAttributeName()), new Double(getAttributeValue()));
    }

}
