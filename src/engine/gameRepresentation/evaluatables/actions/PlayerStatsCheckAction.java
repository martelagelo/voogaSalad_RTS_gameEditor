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
import engine.gameRepresentation.evaluatables.parameters.ParticipantValueParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;

public class PlayerStatsCheckAction extends Action {

    public PlayerStatsCheckAction (EvaluatorFactory factory, GameElementManager elementManager,
            ParticipantManager participantManager, String[] args) {
        super(factory, elementManager, participantManager, args);
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args, EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager)
            throws ClassNotFoundException, EvaluatorCreationException {
        Evaluatable<?> playerAttr = new ParticipantValueParameter(identifyParticipantsOfInterest(
                args[0], participantManager), args[1]);
        Evaluatable<?> numberAttr = new NumberParameter(Double.valueOf(args[3]));
        Evaluator<?, ?, ?> participantValueEvaluator = factory.makeEvaluator(args[2], playerAttr,
                numberAttr);
        Evaluatable<?> attrToSet = new NumericAttributeParameter(args[4], elementManager,
                new ActorObjectIdentifier());
        Evaluatable<?> valueToSet = new NumberParameter(Double.valueOf(args[6]));
        Evaluator<?, ?, ?> valueSetter = factory.makeEvaluator(args[5], attrToSet, valueToSet);
        Evaluator<?, ?, ?> ifThenEvaluator = new IfThen<>(participantValueEvaluator, valueSetter);
        return ifThenEvaluator;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        return (Boolean) action.evaluate(elements);
    }

}
