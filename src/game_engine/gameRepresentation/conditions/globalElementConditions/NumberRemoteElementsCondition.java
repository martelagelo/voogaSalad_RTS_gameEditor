package game_engine.gameRepresentation.conditions.globalElementConditions;

import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;
import java.util.List;



/**
 * Evaluates if the number of other elements is less than a condition
 * 
 * @author Steve
 * @author Zach
 *
 */
public class NumberRemoteElementsCondition extends GlobalElementCondition {
    public NumberRemoteElementsCondition (Evaluator evaluator,String elementName,
                                          int evaluationValue,
                                          GameElementManager elementManager) {
        super(evaluator,elementName, evaluationValue, elementManager);
    }

    @Override
    protected boolean evaluateOnObjects (List<GameElementState> elements) {
        return getEvaluator().evaluate(elements.size(), getEvaluationValue());
    }

}
