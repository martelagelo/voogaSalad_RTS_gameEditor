package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.stateManaging.GameElementManager;


/**
 * A greater than or equal to evaluator
 *
 * @author Zach
 *
 */
public class GreaterThanEqualEvaluator extends Evaluator {

    public GreaterThanEqualEvaluator (GameElementManager elementManager,
                                      String parameter1,
                                      String parameter2) {
        super(">=", elementManager, parameter1, parameter2);

    }

    @Override
    public boolean evaluate (double number1, double number2) {
        return number1 >= number2;
    }

}
