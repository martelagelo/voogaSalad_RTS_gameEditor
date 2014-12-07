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


public class ObjectLocationCheckAction extends Action {
    private String playerTypeString;
    private String gameElementType;
    private int radius;
    private int xLocation;
    private int yLocation;
    private static final ActionOptions description = ActionOptions.OBJECT_LOCATION_DETECTION; 

    GameElementManager manager;
    private ParticipantManager participants;
    private String attributeToSet;
    private Evaluatable<?> evaluatorToUse;
    private int valueToSet;

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
                                               ParticipantManager participantManager) throws ClassNotFoundException, EvaluatorCreationException {
        manager = elementManager;
        participants = participantManager;
        attributeToSet = args[5];
        valueToSet = Integer.parseInt(args[7]);
        Evaluatable<?> elementParameter = new NumericAttributeParameter("",attributeToSet,manager,new ActorObjectIdentifier());
        Evaluatable<?> value = new NumberParameter("", valueToSet);
        
        playerTypeString = args[0];
        gameElementType = args[1];
        radius = Integer.parseInt(args[2]);
        xLocation = Integer.parseInt(args[3]);
        yLocation = Integer.parseInt(args[4]);
       
        evaluatorToUse = factory.makeEvaluator(args[6], elementParameter, value);
        
        
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
                    int curXLocation = element.getNumericalAttribute(StateTags.X_POSITION).intValue();
                    int curYLocation = element.getNumericalAttribute(StateTags.Y_POSITION).intValue();
                    double xDelta = Math.abs(xLocation - curXLocation);
                    double yDelta = Math.abs(yLocation - curYLocation);
                    if (xDelta < radius && yDelta < radius) {
                        evaluatorToUse.evaluate(elements);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
