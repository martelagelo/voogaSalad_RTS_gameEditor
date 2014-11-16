package game_engine.gameRepresentation.conditions.referencedElementConditions;

import game_engine.gameRepresentation.conditions.evaluators.Evaluator;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;


/**
 * A condition that applies an evaluator on a numerical condition on a given element
 * 
 * @author Zach
 *
 */
public class NumericalReferencedElementCondition extends ReferencedElementCondition {

    public NumericalReferencedElementCondition (Evaluator evaluator,
                                                String attributeName,
                                                double attributeValue) {
        super(evaluator, attributeName, attributeValue);
    }

    @Override
    public boolean evaluate (GameElementState element) {
        return getEvaluator().evaluate(element.getNumericalAttribute(getAttributeName())
                .doubleValue(), getAttributeValue());
    }

}
