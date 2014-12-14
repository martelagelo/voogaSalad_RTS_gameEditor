package engine.gameRepresentation.evaluatables.actions;

import java.util.Arrays;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.And;
import engine.gameRepresentation.evaluatables.evaluators.EqualsAssignment;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.GreaterThanEqual;
import engine.gameRepresentation.evaluatables.evaluators.SpawnSelectableElement;
import engine.gameRepresentation.evaluatables.evaluators.SubtractionAssignment;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.ElementPromiseParameter;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.ParticipantValueParameter;
import engine.gameRepresentation.evaluatables.parameters.helpers.ElementPromise;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import engine.stateManaging.GameElementManager;

// TODO clean this up

/**
 * An action that checks an attribute then creates an action
 *
 * @author Zach
 *
 */
public class CheckAttributeCreateObjectAction extends Action {
    private String myTimerName;
    private long myTimerAmount;
    private Evaluator<?, ?, ?> myAttributeDecrementer;
    private Evaluator<?, ?, SelectableGameElement> myElementCreator;
    private Evaluator<?, ?, ?> myElementAttributeDecrementer;
    private String myAttributeList;
    private String myAttributeValues;

    public CheckAttributeCreateObjectAction (EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager, String[] args) {
        super(factory, elementManager, participantManager, args);
    }

    // TODO: This method is too long.
    @Override
    protected Evaluatable<?> initializeAction (String[] args, EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager)
            throws ClassNotFoundException, EvaluatorCreationException {
        myTimerName = args[6];
        myTimerAmount = Long.valueOf(args[7]);
        Evaluatable<?> parameterEvaluator = new NumericAttributeParameter(args[0], elementManager,
                new ActorObjectIdentifier());
        Evaluatable<?> comparisonNumber = new NumberParameter(Double.valueOf(args[2]));
        Evaluator<?, ?, ?> conditionEvaluator = factory.makeEvaluator(args[1], parameterEvaluator,
                comparisonNumber);

        // Check to see if the player has enough resources to create the element
        Evaluatable<?> costAttribute = new NumericAttributeParameter(args[4], elementManager,
                new ActorObjectIdentifier());
        Evaluatable<?> attributeToRemove = new ParticipantValueParameter(
                Arrays.asList(participantManager.getUser()), args[5]);
        Evaluator<?, ?, ?> checkIfEnoughAttr = new GreaterThanEqual<>(attributeToRemove,
                costAttribute);
        myAttributeDecrementer = new SubtractionAssignment<>(attributeToRemove, costAttribute);

        // Create the element
        Evaluatable<?> elementPromise = new ElementPromiseParameter(new ElementPromise(args[3],
                elementManager));
        Evaluatable<?> me = new GameElementParameter(new ActorObjectIdentifier());
        myElementCreator = new SpawnSelectableElement<>(me, elementPromise);

        // Make a grouping of checking both values
        Evaluator<?, ?, ?> checkEvaluator = new And<>(checkIfEnoughAttr, conditionEvaluator);

        myAttributeList = args[8];
        myAttributeValues = args[9];

        // Make an evaluator to decrement the value
        Evaluatable<?> myParameter = new NumericAttributeParameter(args[10], elementManager,
                new ActorObjectIdentifier());
        Evaluatable<?> newValue = new NumericAttributeParameter(args[11], elementManager,
                new ActorObjectIdentifier());
        myElementAttributeDecrementer = new EqualsAssignment<>(myParameter, newValue);
        return checkEvaluator;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        return executeActionIfTimerExpired(elements.getActor(), myTimerName, myTimerAmount,
                elements, elementPair -> {
                    if ((Boolean) action.evaluate(elements)) {
                        myAttributeDecrementer.evaluate(elements);
                        SelectableGameElement element = myElementCreator.evaluate(elements);
                        myElementAttributeDecrementer.evaluate(elements);
                        elements.getActor().setTimer(myTimerName, myTimerAmount);
                        // Go through all the attributes to set and set
                        // their values
                String[] attributes = myAttributeList.split(",");
                String[] values = myAttributeValues.split(",");
                for (int i = 0; i < attributes.length; i++) {
                    element.setNumericalAttribute(attributes[i], elements.getActor()
                            .getNumericalAttribute(values[i]));
                }

            }
        });
    }
}
