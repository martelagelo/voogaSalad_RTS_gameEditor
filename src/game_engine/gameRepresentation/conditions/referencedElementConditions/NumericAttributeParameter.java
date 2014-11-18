package game_engine.gameRepresentation.conditions.referencedElementConditions;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.evaluators.Evaluator;
import game_engine.gameRepresentation.conditions.evaluators.parameters.Parameter;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;


/**
 * Get a numerical attribute from an object
 * 
 * @author Zach
 *
 */
public abstract class NumericAttributeParameter implements Parameter {

    public NumericAttributeParameter (Evaluator evaluator,
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
