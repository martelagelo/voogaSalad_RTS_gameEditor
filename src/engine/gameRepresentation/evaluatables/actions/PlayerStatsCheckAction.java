package engine.gameRepresentation.evaluatables.actions;

import java.util.Arrays;
import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import engine.gameRepresentation.evaluatables.parameters.ParticipantValueParameter;
import engine.stateManaging.GameElementManager;


public class PlayerStatsCheckAction extends Action {

    public PlayerStatsCheckAction (String id,
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
        // TODO Make this a hierarchy with the parameter type identifying
        Evaluatable<?> playerAttr =
                new ParticipantValueParameter(
                                              ((args[0].equals("me")) ? Arrays.asList(participantManager
                                                                             .getUser())
                                                                     : participantManager.getAI()),
                                              args[1], "");
        Evaluatable<?> numberAttr = new NumberParameter("", Double.valueOf(args[3]));
        Evaluator<?, ?, ?> participantValueEvaluator =
                factory.makeEvaluator(args[2], playerAttr, numberAttr);
        return participantValueEvaluator;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        return (Boolean) action.evaluate(elements);
    }

}
