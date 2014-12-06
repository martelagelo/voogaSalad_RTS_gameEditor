package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.IfThen;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;


/**
 * An action that checks a condition on objects and executes an action on the objects if the
 * condition holds true.
 * 
 * @author Zach
 *
 */
public class ObjectConditionAction extends Action {

    public ObjectConditionAction (String id,
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
        GameElementParameter me = new GameElementParameter("", new ActorObjectIdentifier());
        GameElementParameter you = new GameElementParameter("", new ActeeObjectIdentifier());
        Evaluatable<?> condition = factory.makeEvaluator(args[0], me, you);
        Evaluatable<?> action = factory.makeEvaluator(args[1], me, you);
        Evaluator<?, ?, ?> conditionActionEvaluator = new IfThen<>("", condition, action);
        return conditionActionEvaluator;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        return (Boolean) action.evaluate(elements);
    }
}
