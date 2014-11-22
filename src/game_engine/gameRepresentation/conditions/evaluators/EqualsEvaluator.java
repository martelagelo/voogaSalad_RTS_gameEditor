package game_engine.gameRepresentation.conditions.evaluators;

import game_engine.gameRepresentation.conditions.evaluators.evaluatorTypes.NumberEvaluator;
import game_engine.stateManaging.GameElementManager;


/**
 * An equals evaluator
 * 
 * @author Zach
 *
 */
public class EqualsEvaluator<A,B> extends Evaluator<A,B,Boolean> implements NumberEvaluator<Boolean> {

    public EqualsEvaluator (GameElementManager elementManager,
                            String parameter1,
                            String parameter2) {
        super("=", elementManager, parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (Object item1, Object item2) {
        return item1.equals(item2);
    }

	@Override
	public Boolean evaluate(Number num1, Number num2) {
		return num1 == num2;
	}

}
