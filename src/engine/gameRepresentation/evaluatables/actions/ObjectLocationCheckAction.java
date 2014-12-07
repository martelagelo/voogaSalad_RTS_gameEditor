package engine.gameRepresentation.evaluatables.actions;

import java.util.Arrays;
import model.state.gameelement.StateTags;
import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionParameters;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.AttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.ParticipantValueParameter;
import engine.gameRepresentation.evaluatables.parameters.StringAttributeParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import engine.stateManaging.GameElementManager;


public class ObjectLocationCheckAction extends Action {
    private String playerTypeString;
    Evaluatable<Number> playerIdToGet;
    private String gameElementType;
    Evaluatable<String> gameElementTypeToGet;
    private int xLocation;
    Evaluatable<Number> xLocationToGet;
    private int yLocation;
    Evaluatable<Number> yLocationToGet;

    public ObjectLocationCheckAction (String id,
                                      EvaluatorFactory factory,
                                      GameElementManager elementManager,
                                      ParticipantManager participantManager,
                                      String[] args) {
        super(id, factory, elementManager, participantManager, args);
    }

    /*
     *     OBJECT_LOCATION_DETECTION(
                              "Object Location Check",
                              "ObjectLocationCheckAction",
                              "If # object of type # is at the location #,#",
                              ActionParameters.PLAYER_TYPE,
                              ActionParameters.STRING,
                              ActionParameters.NUMBER,
                              ActionParameters.NUMBER),
     */
    @Override
    protected Evaluatable<?> initializeAction (String[] args,
                                               EvaluatorFactory factory,
                                               GameElementManager elementManager,
                                               ParticipantManager participantManager)
                                                                                     throws ClassNotFoundException,
                                                                                     EvaluatorCreationException {
        GameElementParameter you = new GameElementParameter("", new ActeeObjectIdentifier());
        
        this.playerTypeString = args[0];
        this.gameElementType = args[1];
        this.xLocation = Integer.parseInt(args[2]);
        this.yLocation = Integer.parseInt(args[3]);

        playerIdToGet =
                new ParticipantValueParameter("",
                    ((playerTypeString.equals("my"))
                            ? Arrays.asList(participantManager.getUser())
                            : participantManager.getAI()),
                    StateTags.TEAM_ID);
        gameElementTypeToGet = new StringAttributeParameter("",
            StateTags.TYPE, elementManager, new ActeeObjectIdentifier());
        xLocationToGet = new NumericAttributeParameter("",
            StateTags.X_POSITION, elementManager, new ActeeObjectIdentifier());
        yLocationToGet = new NumericAttributeParameter("",
            StateTags.Y_POSITION, elementManager, new ActeeObjectIdentifier());
        return playerIdToGet;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        int gottenPlayerId = this.playerIdToGet.evaluate(elements).intValue();
        String gottenType = this.gameElementTypeToGet.evaluate(elements);
        int gottenXLocation = this.xLocationToGet.evaluate(elements).intValue();
        int gottenYLocation = this.yLocationToGet.evaluate(elements).intValue();
        int gameElementPlayerID = elements.getActee().getNumericalAttribute(StateTags.TEAM_ID).intValue();
        if (gottenXLocation == xLocation && gottenYLocation == yLocation
                && gottenType.equals(gameElementType)
                && gottenPlayerId == gameElementPlayerID) {
            return true;
        }
        return false;
    }

}
