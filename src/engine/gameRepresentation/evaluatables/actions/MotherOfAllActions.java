package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.And;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.ParticipantValueParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;


/**
 * This action is essentially the mother of all actions.
 * 
 * @author zach
 *
 */
public class MotherOfAllActions extends Action {
    private String mySetTimerName;
    private Evaluatable<Number> mySetTimerValue;
    private String myTimerName;
    private Evaluatable<Number> myTimerValue;
    private Evaluator<?, ?, Boolean> myConditionEvaluator;
    private Evaluator<?, ?, ?> myElementEvaluator;
    private Evaluator<?, ?, ?> myElementEvaluator2;
    private Evaluator<?, ?, ?> myGameObjectsEvaluator;
    private Evaluator<?, ?, ?> myParticipantsEvaluator;

    public MotherOfAllActions (String id,
                               EvaluatorFactory factory,
                               GameElementManager elementManager,
                               ParticipantManager participantManager,
                               String[] args) {
        super(id, factory, elementManager, participantManager, args);
    }

    // TODO fix this method
    @Override
    protected Evaluatable<?> initializeAction (String[] args,
                                               EvaluatorFactory factory,
                                               GameElementManager elementManager,
                                               ParticipantManager participantManager)
                                                                                     throws ClassNotFoundException,
                                                                                     EvaluatorCreationException {
        // Check if the participant's attribute is < > =, etc the other value
        Evaluatable<?> participantAttrToCheck =
                new ParticipantValueParameter("",
                                              identifyParticipantsOfInterest(args[0],
                                                                             participantManager),
                                              args[1]);
        Evaluatable<?> elemAttrToCheckAgainst =
                new NumericAttributeParameter("", args[3], elementManager,
                                              new ActorObjectIdentifier());
        Evaluator<?, ?, ?> checkParticipantAttr =
                factory.makeEvaluator(args[2], participantAttrToCheck, elemAttrToCheckAgainst);
        // Check if the element's attribute is < > =, etc. the other value
        Evaluatable<?> elementAttr =
                new NumericAttributeParameter("", args[5], elementManager,
                                              makeObjectIdentifier(args[4]));
        Evaluatable<?> elemToCompareAttr =
                new NumericAttributeParameter("", args[7], elementManager,
                                              new ActorObjectIdentifier());
        Evaluator<?, ?, ?> checkElementAttr =
                factory.makeEvaluator(args[6], elementAttr, elemToCompareAttr);
        // Combine the above into one logical statement
        myConditionEvaluator = new And<>("", checkElementAttr, checkParticipantAttr);
        // Create the element attribute to set
        Evaluatable<?> attrToSet =
                new NumericAttributeParameter("", args[9], elementManager,
                                              makeObjectIdentifier(args[8]));
        Evaluatable<?> valueToSet =
                new NumericAttributeParameter("", args[11], elementManager,
                                              new ActorObjectIdentifier());
        myElementEvaluator =
                factory.makeEvaluator(args[10], attrToSet, valueToSet);
        // Create the second element attribute to set
        Evaluatable<?> attrToSet2 =
                new NumericAttributeParameter("", args[13], elementManager,
                                              makeObjectIdentifier(args[12]));
        Evaluatable<?> valueToSet2 =
                new NumericAttributeParameter("", args[15], elementManager,
                                              new ActorObjectIdentifier());
        myElementEvaluator2 =
                factory.makeEvaluator(args[14], attrToSet2, valueToSet2);
        // Create the action that will act on both objects
        Evaluatable<?> me = new GameElementParameter("", new ActorObjectIdentifier());
        Evaluatable<?> you = new GameElementParameter("", new ActeeObjectIdentifier());
        myGameObjectsEvaluator = factory.makeEvaluator(args[16], me, you);
        // Grab the timer values to set
        mySetTimerName = args[17];
        mySetTimerValue =
                new NumericAttributeParameter("", args[18], elementManager,
                                              new ActorObjectIdentifier());
        // Create the action that will act on the participant
        Evaluatable<?> operand =
                new NumericAttributeParameter("", args[20], elementManager,
                                              new ActorObjectIdentifier());
        Evaluatable<?> participantAttrOperator =
                new ParticipantValueParameter("",
                                              identifyParticipantsOfInterest(args[21],
                                                                             participantManager),
                                              args[22]);
        myParticipantsEvaluator =
                factory.makeEvaluator(args[19], participantAttrOperator, operand);
        // Get the timer values to check
        myTimerName = args[23];
        myTimerValue =
                new NumericAttributeParameter("", args[24], elementManager,
                                              new ActorObjectIdentifier());
        return null;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        GameElement callingElement = elements.getActor();
        if (callingElement.getTimer(myTimerName) <= 0 && myConditionEvaluator.evaluate(elements)) {
            myElementEvaluator.evaluate(elements);
            myElementEvaluator2.evaluate(elements);
            myGameObjectsEvaluator.evaluate(elements);
            myParticipantsEvaluator.evaluate(elements);
            callingElement.setTimer(mySetTimerName, mySetTimerValue.evaluate(elements).longValue());
            callingElement.setTimer(myTimerName, myTimerValue.evaluate(elements).longValue());
        }
        return false;
    }

}
