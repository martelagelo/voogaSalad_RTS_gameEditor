package game_engine.gameRepresentation.conditions.globalElementConditions;

import java.util.List;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;

/**
 * Evaluates if the number of other elements is less than a condition
 * 
 * @author Steve
 * @author Zach
 *
 */
public class NumberRemoteElementsCondition extends GlobalElementCondition {

    public NumberRemoteElementsCondition (String elementName,
                                                           int evaluationValue,
                                                           GameElementManager elementManager) {
        super(elementName, evaluationValue, elementManager);
    }

    @Override
    protected boolean evaluateOnObjects (List<GameElementState> elements) {
        return elements.size()<getEvaluationValue();
    }


}
