package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.IfThen;
import engine.gameRepresentation.evaluatables.evaluators.SpawnSelectableElement;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.ElementPromiseParameter;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.helpers.ElementPromise;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;


/**
 * An action that checks an attribute then creates an action
 * 
 * @author Zach
 *
 */
public class CheckAttributeCreateObjectAction extends Action {
    String myTimerName;
    long myTimerAmount;

    public CheckAttributeCreateObjectAction (String id,
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
        myTimerName = args[4];
        myTimerAmount = Long.valueOf(args[5]);
        Evaluatable<?> parameterEvaluator =
                new NumericAttributeParameter("", args[0], elementManager,
                                              new ActorObjectIdentifier());
        Evaluatable<?> comparisonNumber = new NumberParameter("", Double.valueOf(args[2]));
        Evaluator<?, ?, ?> conditionEvaluator =
                factory.makeEvaluator(args[1], parameterEvaluator, comparisonNumber);
        Evaluatable<?> elementPromise =
                new ElementPromiseParameter("", new ElementPromise(args[3], elementManager));
        Evaluatable<?> me = new GameElementParameter("", new ActorObjectIdentifier());
        Evaluator<?, ?, ?> elementCreator = new SpawnSelectableElement<>("", me, elementPromise);
        return new IfThen<>("", conditionEvaluator, elementCreator);
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        if (elements.getActor().getTimer(myTimerName) <= 0) {
            action.evaluate(elements);
            elements.getActor().setTimer(myTimerName, myTimerAmount);
        }

        return true;
    }
}
