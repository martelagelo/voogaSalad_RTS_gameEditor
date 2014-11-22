package game_engine.gameRepresentation.conditions.operators;

import game_engine.gameRepresentation.conditions.Evaluatable;


/**
 * The Or operator for booleans
 *
 * @author Zach
 *
 */
public class OrOperator extends Operator {
    public OrOperator (Evaluatable<Boolean> firstCondition, Evaluatable<Boolean> secondCondition) {
        super(firstCondition, secondCondition, "||");
    }

    @Override
    protected Boolean applyOperator (boolean boolean1, boolean boolean2) {
        return boolean1 || boolean2;
    }

    @Override
    public String toString () {
        return "||";
    }
}
