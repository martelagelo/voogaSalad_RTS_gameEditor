package game_engine.gameRepresentation.conditions.operators;

import game_engine.gameRepresentation.conditions.Evaluatable;


/**
 * The Or boolean
 *
 * @author Zach
 *
 */
public class OrOperator extends Operator {
    public OrOperator (Evaluatable firstCondition, Evaluatable secondCondition) {
        super(firstCondition, secondCondition,"||");
    }

    @Override
    protected boolean applyOperator (boolean boolean1, boolean boolean2) {
        return boolean1||boolean2;
    }

    @Override
    public String toString(){
        return "||";
    }
}
