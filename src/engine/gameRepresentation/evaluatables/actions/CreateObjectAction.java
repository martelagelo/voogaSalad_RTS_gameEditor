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
 * Create an object with a given type
 * 
 * @author Zach
 *
 */
public class CreateObjectAction extends Action {
    // TODO remove this. For testing only
    private boolean acted;

    public CreateObjectAction (String id,
                               EvaluatorFactory factory,
                               GameElementManager elementManager,
                               ParticipantManager participantManager,
                               String[] args) {
        super(id, factory, elementManager, participantManager, args);
        acted = false;
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

        return elementCreator;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        if (acted == false) {
            action.evaluate(elements);
            acted = true;
        }
        return true;
    }

}
