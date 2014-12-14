package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;

/**
 * Perform a calculation on a attribute of an element
 *
 * @see ActionOptions.PERFORM_CALCULATION_ON_VALUE
 *
 * @author Zach
 *
 */
public class PerformAttrCalculationAction extends Action {

    public PerformAttrCalculationAction (EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager, String[] args) {
        super(factory, elementManager, participantManager, args);
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args, EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager)
            throws ClassNotFoundException, EvaluatorCreationException {
        Evaluatable<?> playerValue = new NumericAttributeParameter(args[2], elementManager,
                new ActorObjectIdentifier());
        Evaluatable<?> number = new NumberParameter(Double.valueOf(args[1]));

        return factory.makeEvaluator(args[0], playerValue, number);
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        action.evaluate(elements);
        return true;
    }

}
