package engine.gameRepresentation.evaluatables.actions;

import java.util.Arrays;
import java.util.List;

import model.state.gameelement.StateTags;
import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.FalseEvaluatable;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;
import engine.users.Participant;

/**
 * @see ActionOptions.OBJECT_LOCATION_DETECTION
 * @author Michael R.
 *
 */
public class ObjectLocationCheckAction extends Action {
    private String myPlayerTypeString;
    private String myGameElementType;
    private int myRadius;
    private int myXLocation;
    private int myYLocation;

    private GameElementManager myManager;
    private ParticipantManager myParticipantManager;
    private String myAttributeToSet;
    private Evaluatable<?> myEvaluatorToUse;
    private int myValueToSet;

    public ObjectLocationCheckAction (EvaluatorFactory factory, GameElementManager elementManager,
            ParticipantManager participantManager, String[] args) {
        super(factory, elementManager, participantManager, args);
        myManager = elementManager;
        myParticipantManager = participantManager;
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args, EvaluatorFactory factory,
            GameElementManager elementManager, ParticipantManager participantManager)
            throws ClassNotFoundException, EvaluatorCreationException {
        myManager = elementManager;
        myParticipantManager = participantManager;
        myAttributeToSet = args[5];
        myValueToSet = Integer.parseInt(args[7]);
        Evaluatable<?> elementParameter = new NumericAttributeParameter(myAttributeToSet,
                myManager, new ActorObjectIdentifier());
        Evaluatable<?> value = new NumberParameter(myValueToSet);

        myPlayerTypeString = args[0];
        myGameElementType = args[1];
        myRadius = Integer.parseInt(args[2]);
        myXLocation = Integer.parseInt(args[3]);
        myYLocation = Integer.parseInt(args[4]);

        myEvaluatorToUse = factory.makeEvaluator(args[6], elementParameter, value);

        return new FalseEvaluatable();
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        List<Participant> matchingPlayers;
        if ("my".equals(myPlayerTypeString)) {
            matchingPlayers = Arrays.asList(myParticipantManager.getUser());
        } else {
            matchingPlayers = myParticipantManager.getAI();
        }
        for (Participant participant : matchingPlayers) {
            for (GameElement element : myManager.findAllElementsOfType(myGameElementType)) {
                if (participant.checkSameTeam(element.getNumericalAttribute(StateTags.TEAM_COLOR
                        .getValue()))) {
                    int curXLocation = element.getNumericalAttribute(
                            StateTags.X_POSITION.getValue()).intValue();
                    int curYLocation = element.getNumericalAttribute(
                            StateTags.Y_POSITION.getValue()).intValue();
                    double xDelta = Math.abs(myXLocation - curXLocation);
                    double yDelta = Math.abs(myYLocation - curYLocation);
                    if (xDelta < myRadius && yDelta < myRadius) {
                        myEvaluatorToUse.evaluate(elements);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
