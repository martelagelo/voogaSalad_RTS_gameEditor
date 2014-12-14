package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.ParticipantValueParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;

/**
 * An action that interacts with a player's attribute with one of the current
 * element
 *
 * @see ActionOptions.ATTRIBUTE_INTERACTION_ACTION
 *
 * @author Zach
 *
 */
public class PlayerAttributeInteractionAction extends Action {

    public PlayerAttributeInteractionAction (EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager, String[] args) {
        super(factory, elementManager, participantManager, args);
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args, EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager)
            throws ClassNotFoundException, EvaluatorCreationException {
        Evaluatable<?> playerAttr = new ParticipantValueParameter(identifyParticipantsOfInterest(
                args[2], participantManager), args[3]);
        Evaluatable<?> elementAttr = new NumericAttributeParameter(args[1], elementManager,
                new ActorObjectIdentifier());
        Evaluator<?, ?, ?> participantValueEvaluator = factory.makeEvaluator(args[0], elementAttr,
                playerAttr);
        return participantValueEvaluator;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        action.evaluate(elements);
        return true;
    }
}
