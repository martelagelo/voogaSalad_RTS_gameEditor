package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.Addition;
import engine.gameRepresentation.evaluatables.evaluators.AdditionAssignment;
import engine.gameRepresentation.evaluatables.evaluators.And;
import engine.gameRepresentation.evaluatables.evaluators.EqualsAssignment;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.GreaterThanEqual;
import engine.gameRepresentation.evaluatables.evaluators.IfThen;
import engine.gameRepresentation.evaluatables.evaluators.LessThan;
import engine.gameRepresentation.evaluatables.evaluators.Subtraction;
import engine.gameRepresentation.evaluatables.evaluators.SubtractionAssignment;
import engine.gameRepresentation.evaluatables.evaluators.TypeCheck;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.StringParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;

//TODO fix this
/**
 * An action that decrements a value on the other object's values and increments
 * the value on the other object's parameters.
 *
 * @see ActionOptions.INCREMENT_DECREMENT_ACTION
 *
 * @author Zach
 *
 */

// TODO refactor to make smaller
public class DecrementIncrementAttributeAction extends Action {
    private String myTimer;
    private long myTimerAmount;

    public DecrementIncrementAttributeAction (EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager, String[] args) {
        super(factory, elementManager, participantManager, args);
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args, EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager)
            throws ClassNotFoundException, EvaluatorCreationException {
        // TODO fix this crap
        // Check that the object is of a given type
        Evaluatable<?> typeString = new StringParameter(args[0]);
        Evaluatable<?> otherObject = new GameElementParameter(new ActeeObjectIdentifier());
        Evaluator<?, ?, ?> typeCheckEvaluator = new TypeCheck<>(otherObject, typeString);
        // Subtract my amount from the other object's attribute
        Evaluatable<?> otherObjectAttribute = new NumericAttributeParameter(args[1],
                elementManager, new ActeeObjectIdentifier());
        Evaluatable<?> myAmountAttribute = new NumericAttributeParameter(args[2], elementManager,
                new ActorObjectIdentifier());
        Evaluator<?, ?, ?> difference = new Subtraction<>(otherObjectAttribute, myAmountAttribute);
        // If the difference is less than min, subtract the difference of this
        // negative attribute from my attribute
        Evaluatable<?> minValue = new NumberParameter(Double.valueOf(args[3]));
        Evaluator<?, ?, ?> differenceEvaluator = new Addition<>(myAmountAttribute, difference);
        Evaluator<?, ?, ?> differenceLessThan0 = new LessThan<>(difference, minValue);
        Evaluatable<?> myAttributeToSet = new NumericAttributeParameter(args[4], elementManager,
                new ActorObjectIdentifier());
        Evaluator<?, ?, ?> setAttributeIfNegative = new SubtractionAssignment<>(myAttributeToSet,
                differenceEvaluator);
        Evaluator<?, ?, ?> ifDiffLessThan0SetAttr = new IfThen<>(differenceLessThan0,
                setAttributeIfNegative);
        // If the difference is greater than or equal to 0, add my subtraction
        // attribute to my attribute
        Evaluator<?, ?, ?> differenceGreaterThan0 = new GreaterThanEqual<>(difference, minValue);
        Evaluator<?, ?, ?> setAttributeIfPositive = new AdditionAssignment<>(myAttributeToSet,
                myAmountAttribute);
        Evaluator<?, ?, ?> ifDiffGreatorThan0SetAttr = new IfThen<>(differenceGreaterThan0,
                setAttributeIfPositive);
        // Make an overall evaluator for the above two conditions for logic for
        // player attribute setting
        Evaluator<?, ?, ?> attributeSetter = new And<>(ifDiffLessThan0SetAttr,
                ifDiffGreatorThan0SetAttr);

        // Make evaluators to decide how to decrement the parameter value of the
        // other object
        // Subtract the amount
        Evaluator<?, ?, ?> otherObjectAttributeSubtraction = new SubtractionAssignment<>(
                otherObjectAttribute, myAmountAttribute);
        // If the amount is less than the min, make it the min
        Evaluator<?, ?, ?> checkLessThanMin = new LessThan<>(otherObjectAttribute, minValue);
        Evaluator<?, ?, ?> setMin = new EqualsAssignment<>(otherObjectAttribute, minValue);
        Evaluator<?, ?, ?> ifAttributeLessThanMinMakeMin = new IfThen<>(checkLessThanMin, setMin);
        Evaluator<?, ?, ?> parameterSubtraction = new And<>(otherObjectAttributeSubtraction,
                ifAttributeLessThanMinMakeMin);
        // Put it all together
        Evaluator<?, ?, ?> parameterSubtracting = new And<>(attributeSetter, parameterSubtraction);
        Evaluator<?, ?, ?> ifTypeAssignment = new IfThen<>(typeCheckEvaluator, parameterSubtracting);
        myTimer = args[5];
        myTimerAmount = Long.valueOf(args[6]);
        return ifTypeAssignment;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        if (elements.getActor().getTimer(myTimer) <= 0) {
            action.evaluate(elements);
            elements.getActor().setTimer(myTimer, myTimerAmount);
            return true;
        }
        return false;
    }

}
