package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.And;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.IfThen;
import engine.gameRepresentation.evaluatables.evaluators.TypeCheck;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.ParticipantValueParameter;
import engine.gameRepresentation.evaluatables.parameters.StringParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;

//TODO cleanup/fix
/**
 * This action checks an object type and performs actions on the object's
 * parameters based on the object type
 *
 * @author Zach
 *
 */
public class ObjectTypeCheckAction extends Action {

    public ObjectTypeCheckAction (EvaluatorFactory factory, GameElementManager elementManager,
            ParticipantManager participantManager, String[] args) {
        super(factory, elementManager, participantManager, args);
    }

    // TODO: method too long
    @Override
    protected Evaluatable<?> initializeAction (String[] args, EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager)
            throws ClassNotFoundException, EvaluatorCreationException {
        // Check if the object is of a certain type
        Evaluatable<?> objectIdentifier = new GameElementParameter(new ActeeObjectIdentifier());
        Evaluatable<?> desiredObjectType = new StringParameter(args[0]);
        Evaluator<?, ?, ?> objectTypeCheck = new TypeCheck<>(objectIdentifier, desiredObjectType);
        // Set the parameter of the object
        Evaluatable<?> parameterToChange = new NumericAttributeParameter(args[2], elementManager,
                makeObjectIdentifier(args[1]));
        // Make the numeric object to set
        Evaluatable<?> number = new NumberParameter(Double.valueOf(args[4]));
        Evaluatable<?> parameterToSet = new NumericAttributeParameter(args[7], elementManager,
                makeObjectIdentifier(args[6]));
        Evaluator<?, ?, ?> numberArithmetic = factory
                .makeEvaluator(args[5], parameterToSet, number);
        Evaluator<?, ?, ?> setParameter = factory.makeEvaluator(args[3], parameterToChange,
                numberArithmetic);
        // Perform an action on both the elements
        Evaluatable<?> me = new GameElementParameter(new ActorObjectIdentifier(), "");
        Evaluator<?, ?, ?> objectAction = factory.makeEvaluator(args[8], me, objectIdentifier);
        // Perform an action on the player's attribute
        Evaluatable<?> participantAttr = new ParticipantValueParameter(
                identifyParticipantsOfInterest(args[11], participantManager), args[12]);
        Evaluatable<?> playerAttr = new NumericAttributeParameter(args[10], elementManager,
                new ActorObjectIdentifier());
        Evaluator<?, ?, ?> participantParameterSetter = factory.makeEvaluator(args[9],
                participantAttr, playerAttr);
        // Combine it all
        Evaluator<?, ?, ?> subAction = new And<>(objectAction, participantParameterSetter);
        Evaluator<?, ?, ?> action = new And<>(setParameter, subAction);
        Evaluator<?, ?, ?> condition = new IfThen<>(objectTypeCheck, action);

        return condition;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        return (Boolean) action.evaluate(elements);
    }

}
