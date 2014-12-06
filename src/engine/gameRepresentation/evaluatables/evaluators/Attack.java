package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * An evaluator that attacks an enemy if their team is not the same as the current team. Uses the
 * attack timer of the attacker object
 * 
 * @author Zach
 */
public class Attack<A, B> extends Evaluator<A, B, Boolean> {

    public static final String ATTACK_TIMER = "AttackTimer";

    public Attack (String id,
                            Evaluatable<A> parameter1,
                            Evaluatable<B> parameter2) {
        super(Boolean.class, id, "Attack", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (GameElement object1, GameElement object2) {
        if (object1.getTimer(ATTACK_TIMER) <= 0 &&
            object2.getNumericalAttribute(StateTags.TEAM_ID).intValue() !=
            object1.getNumericalAttribute(StateTags.TEAM_ID).intValue()) {
            object2.setNumericalAttribute(StateTags.HEALTH,
                                          object2.getNumericalAttribute(StateTags.HEALTH)
                                                  .doubleValue() -
                                                  object1.getNumericalAttribute(StateTags.ATTACK)
                                                          .doubleValue());
            object1.setTimer(ATTACK_TIMER, object1.getNumericalAttribute(StateTags.RELOAD_TIME)
                    .longValue());
        }
        return true;
    }

}