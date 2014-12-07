package engine.gameRepresentation.evaluatables.actions;

import java.util.Arrays;
import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.And;
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
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import engine.stateManaging.GameElementManager;


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
    private String myAttributeList;
    private String myAttributeValues;

    public CheckAttributeCreateObjectAction (String id,
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
        myTimerName = args[6];
        myTimerAmount = Long.valueOf(args[7]);
        Evaluatable<?> parameterEvaluator =
                new NumericAttributeParameter("", args[0], elementManager,
                                              new ActorObjectIdentifier());
        Evaluatable<?> comparisonNumber = new NumberParameter("", Double.valueOf(args[2]));
        Evaluator<?, ?, ?> conditionEvaluator =
                factory.makeEvaluator(args[1], parameterEvaluator, comparisonNumber);

        // Check to see if the player has enough resources to create the element
        Evaluatable<?> costAttribute =
                new NumericAttributeParameter("", args[4], elementManager,
                                              new ActorObjectIdentifier());
        Evaluatable<?> attributeToRemove =
                new ParticipantValueParameter("", Arrays.asList(participantManager.getUser()),
                                              args[5]);
        Evaluator<?, ?, ?> checkIfEnoughAttr =
                new GreaterThanEqual<>("", attributeToRemove, costAttribute);
        myAttributeDecrementer =
                new SubtractionAssignment<>("", attributeToRemove, costAttribute);

        // Create the element
        Evaluatable<?> elementPromise =
                new ElementPromiseParameter("", new ElementPromise(args[3], elementManager));
        Evaluatable<?> me = new GameElementParameter("", new ActorObjectIdentifier());
        myElementCreator = new SpawnSelectableElement<>("", me, elementPromise);

        // Make a grouping of checking both values
        Evaluator<?, ?, ?> checkEvaluator =
                new And<>("", checkIfEnoughAttr, conditionEvaluator);

        myAttributeList = args[8];
        myAttributeValues = args[9];

        return checkEvaluator;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        GameElement player = elements.getActor();
        if (elements.getActor().getTimer(myTimerName) <= 0) {
            if ((Boolean) action.evaluate(elements)) {
                myAttributeDecrementer.evaluate(elements);
                SelectableGameElement element = myElementCreator.evaluate(elements);
                elements.getActor().setTimer(myTimerName, myTimerAmount);
                // Go through all the attributes to set and set their values
                String[] attributes = myAttributeList.split(",");
                String[] values = myAttributeValues.split(",");
                for (int i = 0; i < attributes.length; i++) {
                    System.out.println(attributes[i]);
                    System.out.println( player.getNumericalAttribute(values[i]));
                    element.setNumericalAttribute(attributes[i],
                                                  player.getNumericalAttribute(values[i]));
                }

            }
        }

        return true;
    }

}
