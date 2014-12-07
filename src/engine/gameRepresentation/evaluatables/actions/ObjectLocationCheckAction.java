package engine.gameRepresentation.evaluatables.actions;

import java.util.Arrays;
import java.util.List;
import model.state.gameelement.StateTags;
import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.FalseEvaluatable;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionParameters;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.AttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.ParticipantValueParameter;
import engine.gameRepresentation.evaluatables.parameters.StringAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;
import engine.users.Participant;


public class ObjectLocationCheckAction extends Action {
    private String playerTypeString;
    private String gameElementType;
    private int xLocation;
    private int yLocation;


    /*

    Evaluatable<Number> playerIdToGet;
    Evaluatable<String> gameElementTypeToGet;
    Evaluatable<Number> xLocationToGet;
    Evaluatable<Number> yLocationToGet;
    */
    GameElementManager manager;
    private ParticipantManager participants;

    public ObjectLocationCheckAction (String id,
                                      EvaluatorFactory factory,
                                      GameElementManager elementManager,
                                      ParticipantManager participantManager,
                                      String[] args) {
        super(id, factory, elementManager, participantManager, args);
        this.manager = elementManager;
        this.participants = participantManager;
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args,
                                               EvaluatorFactory factory,
                                               GameElementManager elementManager,
                                               ParticipantManager participantManager) {
        return new FalseEvaluatable();
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        List<Participant> matchingPlayers;
        if ("my".equals(playerTypeString)) {
            matchingPlayers = Arrays.asList(participants.getUser());
        }
        else {
            matchingPlayers = participants.getAI();
        }
        for (Participant participant: matchingPlayers) {
            for (GameElement element: manager.findAllElementsOfType(gameElementType)) {
                if (participant.checkSameTeam(element.getTextualAttribute(StateTags.TEAM_COLOR))) {
                    if (xLocation == element.getNumericalAttribute(StateTags.X_POSITION).intValue()
                            && yLocation == element.getNumericalAttribute(StateTags.Y_POSITION).intValue()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
