package game_engine.gameRepresentation.conditions.referencedElementConditions;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.evaluators.Evaluator;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;


/**
 * A condition that applies an evaluator on a numerical condition on a given element
 * 
 * @author Zach
 *
 */
public class NumericalCondition extends ReferencedElementCondition {

    public NumericalCondition (Evaluator evaluator,
                               String attributeName,
                               double attributeValue, boolean selfOrOther) {
        super(evaluator, attributeName, attributeValue, selfOrOther);
    }

    @Override
    protected boolean evaluate (GameElementState element) {
        return getEvaluator().evaluate(element.getNumericalAttribute(getAttributeName())
                .doubleValue(), getAttributeValue());
    }

}
