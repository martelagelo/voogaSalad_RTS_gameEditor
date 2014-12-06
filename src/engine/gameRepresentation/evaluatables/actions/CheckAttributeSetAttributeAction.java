package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.IfThen;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;


/**
 * An action that checks an attribute value with an evaluator then sets an attribute value with an
 * evaluator of the current object
 * 
 * @author Zach
 *
 */
public class CheckAttributeSetAttributeAction extends Action {

    public CheckAttributeSetAttributeAction (String id,
                                             EvaluatorFactory factory,
                                             GameElementManager manager,
                                             ParticipantManager participantManager,
                                             String[] args) {
        super(id, factory, manager, participantManager, args);
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args,
                                               EvaluatorFactory factory,
                                               GameElementManager manager,
                                               ParticipantManager participantManager)
                                                                                     throws ClassNotFoundException,
                                                                                     EvaluatorCreationException {
        Evaluatable<?> attr1Param =
                new NumericAttributeParameter("", args[0], null, new ActorObjectIdentifier());
        Evaluatable<?> desiredValueParam = new NumberParameter("", Double.valueOf(args[2]));
        Evaluator<?, ?, ?> conditionEvaluator =
                factory.makeEvaluator(args[1], attr1Param, desiredValueParam);
        Evaluatable<?> attr2Param =
                new NumericAttributeParameter("", args[3], null, new ActorObjectIdentifier());
        Evaluatable<?> val2Param = new NumberParameter("", Double.valueOf(args[5]));
        Evaluator<?, ?, ?> actionEvaluator = factory.makeEvaluator(args[4], attr2Param, val2Param);
        return new IfThen<>("", conditionEvaluator, actionEvaluator);
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        return (Boolean) action.evaluate(elements);
    }

}
