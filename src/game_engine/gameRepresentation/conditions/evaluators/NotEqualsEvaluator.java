package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.stateManaging.GameElementManager;


/**
 * A not equals evaluator
 *
 * @author Zach
 *
 */
public class NotEqualsEvaluator extends Evaluator {
    public NotEqualsEvaluator (GameElementManager elementManager,
                               String parameter1,
                               String parameter2) {
        super("!=", elementManager, parameter1, parameter2);
    }

    @Override
    public boolean evaluate (double number1, double number2) {
        return number1 != number2;
    }

    @Override
    public boolean evaluate (Object item1, Object item2) {
        return !item1.equals(item2);
    }

}
