package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.conditions.evaluators.Evaluator;


/**
 * The basic representation for a condition. Takes an evaluator and evaluates a given condition
 * based on the evaluator's logic
 * 
 * @author Zach
 *
 */
public abstract class Condition implements Evaluatable {

    private Evaluator myEvaluator;

    public Condition (Evaluator evaluator) {
        myEvaluator = evaluator;
    }

    protected Evaluator getEvaluator () {
        return myEvaluator;
    }

}
