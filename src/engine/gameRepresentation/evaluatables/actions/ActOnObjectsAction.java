package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.stateManaging.GameElementManager;

/**
 * A basic action that just acts on two objects when given them on an update
 *
 * @see ActionOptions.ACT_ON_OBJECTS_ACTION
 * @author Zach
 *
 */
public class ActOnObjectsAction extends Action {

    public ActOnObjectsAction (EvaluatorFactory factory, GameElementManager manager,
            ParticipantManager participantManager, String[] args) {
        super(factory, manager, participantManager, args);
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args, EvaluatorFactory factory,
            GameElementManager manager, ParticipantManager participantManager)
            throws ClassNotFoundException, EvaluatorCreationException {
        return factory.makeEvaluator(args[0], ACTOR, ACTEE);
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        action.evaluate(elements);
        return true;
    }
}
