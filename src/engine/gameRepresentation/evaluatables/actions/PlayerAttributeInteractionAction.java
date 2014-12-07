package engine.gameRepresentation.evaluatables.actions;

import java.util.Arrays;
import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.ParticipantValueParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;


/**
 * An action that interacts with a player's attribute with one of the current element
 * 
 * @author Zach
 *
 */
public class PlayerAttributeInteractionAction extends Action {

    public PlayerAttributeInteractionAction (String id,
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
        Evaluatable<?> playerAttr =
                new ParticipantValueParameter("",
                                              ((args[2].equals("my")) ? Arrays
                                                      .asList(participantManager
                                                              .getUser())
                                                                     : participantManager.getAI()),
                                              args[3]);
        Evaluatable<?> elementAttr =
                new NumericAttributeParameter("", args[1], elementManager,
                                              new ActorObjectIdentifier());
        Evaluator<?, ?, ?> participantValueEvaluator =
                factory.makeEvaluator(args[0], elementAttr,playerAttr);
        return participantValueEvaluator;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        action.evaluate(elements);
        return true;
    }
}
