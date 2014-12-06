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
 * Create an object with a given type with a given reload time on a given timer
 * 
 * @author Zach
 *
 */
public class CreateObjectAction extends Action {
    private String myTimerName;
    private int myReloadTime;

    public CreateObjectAction (String id,
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

        myTimerName = args[1];
        myReloadTime = Integer.valueOf(args[2]);
        Evaluatable<?> elementPromise =
                new ElementPromiseParameter("", new ElementPromise(args[0], elementManager));
        Evaluatable<?> me = new GameElementParameter("", new ActorObjectIdentifier());
        Evaluator<?, ?, ?> elementCreator = new SpawnSelectableElement<>("", me, elementPromise);

        return elementCreator;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        if (elements.getActor().getTimer(myTimerName) <= 0) {
            System.out.println("making element");
            action.evaluate(elements);
            elements.getActor().setTimer(myTimerName, myReloadTime);
        }
        return true;
    }

}
