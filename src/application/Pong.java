package application;

import java.awt.Toolkit;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MainModel;
import model.exceptions.SaveLoadException;
import model.state.CampaignState;
import model.state.GameState;
import model.state.LevelState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import util.SaveLoadUtility;
import engine.Engine;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;
import engine.visuals.ScrollablePane;
import engine.visuals.elementVisuals.animations.AnimatorState;


public class Pong extends Application {

    public static final java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int shittyWidth = 1000;

    @Override
    public void start (Stage primaryStage) {
        try {
            Engine engine = hardCodeAGame();
            Group g = new Group();
            ScrollablePane pane = engine.getScene();
            g.getChildren().add(pane);
            Scene s = new Scene(g, shittyWidth, 0.9 * screenSize.getHeight());
            primaryStage.setScene(s);
            primaryStage.show();
            engine.play();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Engine hardCodeAGame () throws Exception {
        double[] bounds = new double[] { 0, 0, 40, 0, 40, 40, 0, 40 };
        SelectableGameElementState paddle = createPaddle(350, 100, 1);
        paddle.attributes.setNumericalAttribute(StateTags.IS_SELECTED, 1);
        paddle.setBounds(bounds);
        SelectableGameElementState enemyPaddle = createPaddle(350, 700, 2);
        enemyPaddle.setBounds(bounds);
        enemyPaddle.attributes.setTextualAttribute(StateTags.TEAM_COLOR, "GREEN");
        enemyPaddle.addAction(new ActionWrapper(ActionType.COLLISION,
                                                ActionOptions.ACT_ON_OBJECTS_ACTION, "SetFocused"));
        enemyPaddle.addAction(new ActionWrapper(ActionType.FOCUSED,
                                                ActionOptions.ACT_ON_OBJECTS_ACTION, "FollowX"));
        enemyPaddle.addAction(new ActionWrapper(ActionType.INTERNAL,
                                                ActionOptions.CHECK_ATTR_SET_ATTR_ACTION,
                                                StateTags.Y_VELOCITY, "NotEquals", "0",
                                                StateTags.Y_VELOCITY, "EqualsAssignment", "0"));
        SelectableGameElementState ball = createBall(350, 350);
        ball.setBounds(bounds);
        // This one can be used for pathing
        paddle.addAction(new ActionWrapper(ActionType.INTERNAL,
                                           ActionOptions.ACT_ON_OBJECTS_ACTION,
                                           "HeadingUpdate"));
        enemyPaddle.addAction(new ActionWrapper(ActionType.COLLISION,
                                                ActionOptions.ACT_ON_OBJECTS_ACTION,
                                                "SetFocused"));
        enemyPaddle.attributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED, 3);
        // enemyPaddle.addAction(new
        // ActionWrapper(ActionType.INTERNAL,ActionOptions.OBJECT_CONDITION_ACTION));

        LevelState levelState = new LevelState("testLevel");

        levelState.addUnit(paddle);
        levelState.addUnit(enemyPaddle);
        levelState.addUnit(ball);
        levelState.attributes.setNumericalAttribute(StateTags.LEVEL_WIDTH, 1000);
        levelState.attributes.setNumericalAttribute(StateTags.LEVEL_HEIGHT, 1000);
        levelState.addGoal(createGoal());

        CampaignState campaignState = new CampaignState("testCampaign");
        campaignState.addLevel(levelState);

        GameState gameState = new GameState("testGame");
        gameState.addCampaign(campaignState);

        MainModel model = new MainModel();
        model.saveGame(gameState);
        model.loadGame("testGame");
        model.getGameUniverse().addSelectableGameElementState(paddle);
        model.saveGame();
        MainModel model2 = new MainModel();
        model2.loadGame("testGame");
        Engine engine =
                new Engine(model2, model2.getLevel("testCampaign", "testLevel"));
        return engine;
    }

    private GameElementState createGoal () {
        GameElementState ges = new GameElementState();
        ges.attributes.setNumericalAttribute("GoalSatisfied", 0);
        // ges.addAction(new ActionWrapper(ActionType.INTERNAL,
        // ActionOptions.PLAYER_ATTRIBUTE_CONDITION, "my",
        // "Resources", "GreaterThanEqual", "1000", "Won",
        // "EqualsAssignment", "1"));
        // ges.addAction(new ActionWrapper(ActionType.INTERNAL,
        // ActionOptions.OBJECT_LOCATION_DETECTION,
        // "my", "archer", "50", "50", "50", "Won", "EqualsAssignment", "1"));
        return ges;
    }

    private SelectableGameElementState createBall (double x, double y) throws Exception {
        SelectableGameElementState ball = new SelectableGameElementState(x, y);
        ball.attributes.setNumericalAttribute(StateTags.X_POSITION, x);
        ball.attributes.setNumericalAttribute(StateTags.Y_POSITION, y);
        ball.attributes.setNumericalAttribute(StateTags.X_GOAL_POSITION, x);
        ball.attributes.setNumericalAttribute(StateTags.Y_GOAL_POSITION, y);
        ball.attributes.setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION, x);
        ball.attributes.setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION, y);
        ball.attributes.setNumericalAttribute(StateTags.Y_VELOCITY, 1);
        ball.attributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED, 1);
        ball.attributes.setTextualAttribute(StateTags.CURRENT_ACTION, "STANDING");
        ball.attributes.setNumericalAttribute("teamID", 2);
        ball.attributes.setTextualAttribute(StateTags.TEAM_COLOR, "RED");
        ball.addType("ball");
        ball.addAction(new ActionWrapper(ActionType.COLLISION,
                                         ActionOptions.PERFORM_CALCULATION_ON_VALUE,
                                         "MultiplicationAssignment", "-1", StateTags.Y_VELOCITY));
        //TODO make this work
        ball.addAction(new ActionWrapper(ActionType.COLLISION,ActionOptions.PERFORM_CALCULATION_ON_VALUE,"MultiplicationAssignment","1.2",StateTags.Y_VELOCITY));
        AnimatorState ballAnimations;
        // This one moves the player
        ball.addAction(new ActionWrapper(ActionType.INTERNAL,
                                         ActionOptions.ACT_ON_OBJECTS_ACTION,
                                         "MovePlayer"));
        ballAnimations = SaveLoadUtility
                .loadResource(AnimatorState.class,
                              "resources/gameelementresources/animatorstate/archer.json");

        ball.myAnimatorState = ballAnimations;
        return ball;
    }

    private SelectableGameElementState createPaddle (double x, double y, int teamID)
                                                                                    throws Exception {

        SelectableGameElementState paddle = new SelectableGameElementState(x, y);
        paddle.attributes.setNumericalAttribute(StateTags.X_POSITION, x);
        paddle.attributes.setNumericalAttribute(StateTags.Y_POSITION, y);
        paddle.attributes.setNumericalAttribute(StateTags.X_GOAL_POSITION, x);
        paddle.attributes.setNumericalAttribute(StateTags.Y_GOAL_POSITION, y);
        paddle.attributes.setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION, x);
        paddle.attributes.setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION, y);
        paddle.attributes.setTextualAttribute(StateTags.CURRENT_ACTION, "STANDING");
        paddle.attributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED, 6);
        paddle.attributes.setNumericalAttribute("teamID", teamID);
        paddle.attributes.setTextualAttribute(StateTags.TEAM_COLOR, "BLUE");
        paddle.addType("paddle");
        // TODO make directions changed

        // // Choose a random temporary waypoint if we collide with anything
        // archerState.addAction(new ActionWrapper(ActionType.COLLISION,
        // ActionOptions.OBJECT_CONDITION_ACTION,
        // "NotCollision", "RandomWaypoint"));
        // // On collision, attack an enemy
        // archerState.addAction(new ActionWrapper(ActionType.COLLISION,
        // ActionOptions.OBJECT_CONDITION_ACTION,
        // "Collision", "Attack"));
        // // Move back if we collide with anything
        // archerState.addAction(new ActionWrapper(ActionType.COLLISION,
        // ActionOptions.OBJECT_CONDITION_ACTION,
        // "Collision", "MoveBack"));
        // // Check to see if our health is <0. If so, die.
        // archerState
        // .addAction(new ActionWrapper(ActionType.INTERNAL,
        // ActionOptions.CHECK_ATTR_SET_ATTR_ACTION,
        // StateTags.HEALTH,
        // "LessThanEqual",
        // "0",
        // StateTags.IS_DEAD, "EqualsAssignment", "1"));
        // Update player direction
        paddle.addAction(new ActionWrapper(ActionType.INTERNAL,
                                           ActionOptions.ACT_ON_OBJECTS_ACTION,
                                           "UpdateMovementDirection"));
        // This one moves the player
        paddle.addAction(new ActionWrapper(ActionType.INTERNAL,
                                           ActionOptions.ACT_ON_OBJECTS_ACTION,
                                           "MovePlayer"));

        // // Make the element so it follows another player when right-clicked on
        // archerState.addAction(new ActionWrapper(ActionType.FOCUSED,
        // ActionOptions.ACT_ON_OBJECTS_ACTION, "Follow"));
        // archerState.addAction(new ActionWrapper(ActionType.BUTTON,
        // ActionOptions.CHECK_CONDITION_CREATE_OBJECT_ACTION,
        // StateTags.LAST_BUTTON_CLICKED_ID, "Equals", "1",
        // "archer", StateTags.HEALTH, "Resources",
        // "ArcherSpawnCooldown", "1000"));
        // archerState.attributes.setNumericalAttribute(StateTags.X_SPAWN_OFFSET, 100);
        // archerState.attributes.setNumericalAttribute(StateTags.Y_SPAWN_OFFSET, 100);
        //
        //
        AnimatorState paddleAnimations =
                SaveLoadUtility
                        .loadResource(AnimatorState.class,
                                      "resources/gameelementresources/animatorstate/archer.json");
        paddle.myAnimatorState = paddleAnimations;

        return paddle;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
