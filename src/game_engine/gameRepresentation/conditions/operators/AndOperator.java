package game_engine.gameRepresentation.conditions.operators;

import game_engine.gameRepresentation.conditions.Evaluatable;
/**
 * The AND operator
 * 
 * @author Zach
 *
 */
public class AndOperator extends Operator{

    public AndOperator (Evaluatable firstCondition, Evaluatable secondCondition) {
        super(firstCondition, secondCondition, "&&");
    }

    @Override
    protected boolean applyOperator (boolean boolean1, boolean boolean2) {
       return boolean1&&boolean2;
    }

}
