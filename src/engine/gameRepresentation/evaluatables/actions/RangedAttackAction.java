package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.SpawnSelectableElement;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.ElementPromiseParameter;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.helpers.ElementPromise;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;


/**
 * Execute a basic ranged attack
 * 
 * @author Zach
 *
 */
public class RangedAttackAction extends Action {
    private final static String RANGED_TIMER = "RangedTimer";

    public RangedAttackAction (String id,
                               EvaluatorFactory factory,
                               GameElementManager elementManager,
                               ParticipantManager participantManager,
                               String[] args) {
        super(id, factory, elementManager, participantManager, args);
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args,
                                               EvaluatorFactory factory,
                                               GameElementManager elementManager,
                                               ParticipantManager participantManager)
                                                                                     throws ClassNotFoundException,
                                                                                     EvaluatorCreationException {
        Evaluatable<?> elementPromise =
                new ElementPromiseParameter("", new ElementPromise(args[0], elementManager));
        Evaluatable<?> me = new GameElementParameter("", new ActorObjectIdentifier());
        Evaluator<?, ?, ?> elementCreator = new SpawnSelectableElement<>("", me, elementPromise);
        return null;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        // TODO Auto-generated method stub
        return null;
    }

}
