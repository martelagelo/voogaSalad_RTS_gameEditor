package game_engine.gameRepresentation.conditions.globalElementConditions;

import game_engine.gameRepresentation.conditions.Condition;
import game_engine.gameRepresentation.conditions.evaluators.Evaluator;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;
import java.util.List;


/**
 * A condition that can be applied to a globally defined element type
 *
 * @author Zach
 *
 */
public abstract class GlobalElementCondition extends Condition {

    private String myElementName;
    private int myEvaluationValue;
    private GameElementManager myElementManager;

    public GlobalElementCondition (Evaluator evaluator, String elementName,
                                   int evaluationValue,
                                   GameElementManager elementManager) {
        super(evaluator);
        myElementName = elementName;
        myEvaluationValue = evaluationValue;
        myElementManager = elementManager;
    }

    @Override
    public boolean evaluate (GameElementState element) {
        return evaluateOnObjects(myElementManager.findAllElementsOfType(myElementName));
    }

    protected int getEvaluationValue () {
        return myEvaluationValue;
    }

    protected abstract boolean evaluateOnObjects (List<GameElementState> elements);

}
