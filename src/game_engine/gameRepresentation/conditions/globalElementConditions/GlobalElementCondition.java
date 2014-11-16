package game_engine.gameRepresentation.conditions.globalElementConditions;

import java.util.List;
import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;


/**
 * A condition that can be applied to a globally defined element type
 * 
 * @author Zach
 *
 */
public abstract class GlobalElementCondition implements Evaluatable {

    private String myElementName;
    private int myEvaluationValue;
    private GameElementManager myElementManager;

    public GlobalElementCondition (String elementName,
                                   int evaluationValue,
                                   GameElementManager elementManager) {
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
