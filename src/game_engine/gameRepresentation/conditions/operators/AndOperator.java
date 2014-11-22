package game_engine.gameRepresentation.conditions.operators;

import game_engine.gameRepresentation.conditions.Evaluatable;


/**
 * The AND operator
 * 
 * @author Zach
 *
 */
public class AndOperator extends Operator {

    public AndOperator (Evaluatable<Boolean> firstCondition, Evaluatable<Boolean> secondCondition) {
        super(firstCondition, secondCondition, "&&");
    }

    @Override
    protected Boolean applyOperator (boolean boolean1, boolean boolean2) {
        return boolean1 && boolean2;
    }

}
