package application;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MainModel;
import model.state.CampaignState;
import model.state.GameState;
import model.state.LevelIdentifier;
import model.state.LevelState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import util.exceptions.SaveLoadException;
import engine.Engine;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;
import engine.visuals.Dimension;
import engine.visuals.ScrollablePane;
import engine.visuals.elementVisuals.animations.AnimationSequence;
import engine.visuals.elementVisuals.animations.AnimationTag;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Engine hardCodeAGame () throws Exception {
        double[] bounds = new double[] { 0, 0, 40, 0, 40, 40, 0, 40 };
        SelectableGameElementState paddle = createPaddle(350, 100, 1);
        paddle.myAttributes.setNumericalAttribute(StateTags.IS_SELECTED.getValue(), 1);
        paddle.setBounds(bounds);
        paddle.addAction(new ActionWrapper(ActionType.BUTTON,
                ActionOptions.CHECK_ATTR_SET_ATTR_ACTION, "me", StateTags.LAST_BUTTON_CLICKED_ID
                        .getValue(), "Equals", "1", "me", StateTags.X_VELOCITY.getValue(),
                "EqualsAssignment", "-3"));
        paddle.addAction(new ActionWrapper(ActionType.BUTTON,
                ActionOptions.CHECK_ATTR_SET_ATTR_ACTION, "me", StateTags.LAST_BUTTON_CLICKED_ID
                        .getValue(), "Equals", "2", "me", StateTags.X_VELOCITY.getValue(),
                "EqualsAssignment", "3"));
        SelectableGameElementState enemyPaddle = createPaddle(350, 700, 2);
        enemyPaddle.setBounds(bounds);
        enemyPaddle.myAttributes.setNumericalAttribute(StateTags.TEAM_COLOR.getValue(),
                Long.parseLong("008000", 16));
        enemyPaddle.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.ACT_ON_OBJECTS_ACTION, "SetFocused"));
        enemyPaddle.addAction(new ActionWrapper(ActionType.FOCUSED,
                ActionOptions.ACT_ON_OBJECTS_ACTION, "FollowX"));
        enemyPaddle.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.CHECK_ATTR_SET_ATTR_ACTION, "me", StateTags.Y_VELOCITY.getValue(),
                "NotEquals", "0", "me", StateTags.Y_VELOCITY.getValue(), "EqualsAssignment", "0"));
        SelectableGameElementState ball = createBall(350, 350);
        ball.setBounds(bounds);
        ball.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.CHECK_ATTR_SET_ATTR_ACTION, "me",
                StateTags.MOVEMENT_SPEED.getValue(), "GreaterThan", "8", "me",
                StateTags.MOVEMENT_SPEED.getValue(), "EqualsAssignment", "8"));
        // This one can be used for pathing
        paddle.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.ACT_ON_OBJECTS_ACTION, "HeadingUpdate"));
        paddle.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.PERFORM_CALCULATION_ON_VALUE, "EqualsAssignment", "1",
                StateTags.IS_SELECTED.getValue()));
        paddle.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_TYPE_CHECK_ACTION, "boundary", "me", StateTags.X_VELOCITY
                        .getValue(), "EqualsAssignment", "-1", "MultiplicationAssignment", "me",
                StateTags.X_VELOCITY.getValue(), "FalseEvaluator", "Equals", "0", "my", "0"));
        enemyPaddle.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.ACT_ON_OBJECTS_ACTION, "SetFocused"));
        enemyPaddle.myAttributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED.getValue(), 3);
        enemyPaddle.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.ACT_ON_OBJECTS_ACTION, "UpdateMovementDirection"));

        GameElementState winningGoal = new GameElementState();
        winningGoal.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.PLAYER_ATTRIBUTE_CONDITION, "my", StateTags.RESOURCES.getValue(),
                "GreaterThanEqual", "5", "Won", "EqualsAssignment", "1"));
        winningGoal.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.PLAYER_ATTRIBUTE_CONDITION, "other", StateTags.RESOURCES.getValue(),
                "GreaterThanEqual", "5", "Lost", "EqualsAssignment", "1"));
        LevelState levelState = new LevelState("testLevel", "testCampaign");
        levelState.addUnit(paddle);
        levelState.addUnit(createBoundary(-10, 0));
        levelState.addUnit(createBoundary(800, 0));
        levelState.addUnit(createGoalRegion(0, -10, true));
        levelState.addUnit(createGoalRegion(0, 850, false));
        levelState.addUnit(enemyPaddle);
        levelState.addUnit(ball);
        levelState.addGoal(winningGoal);
        levelState.myAttributes.setNumericalAttribute(StateTags.LEVEL_WIDTH.getValue(), 1000);
        levelState.myAttributes.setNumericalAttribute(StateTags.LEVEL_HEIGHT.getValue(), 1000);
        levelState.myAttributes.setTextualAttribute(StateTags.BACKGROUND_PATH.getValue(),
                "resources/img/graphics/terrain/grass/GrassTile.jpg");

        CampaignState campaignState = new CampaignState("testCampaign");
        campaignState.addLevel(levelState);

        GameState gameState = new GameState("testGame");
        gameState.addCampaign(campaignState);

        MainModel model = new MainModel();
        System.out.println("<---------- nothing yet saved ---------->");
        model.saveGame(gameState);
        System.out.println("<---------- game saved once ---------->");
        model.loadGame("testGame");
        System.out.println("<---------- game loaded once ---------->");
        model.getGameUniverse().addSelectableGameElementState(paddle);
        model.getGameUniverse().addSelectableGameElementState(createGoalDisplayMarker());
        model.saveGame();
        System.out.println("<---------- game saved twice ---------->");
        MainModel model2 = new MainModel();
        model2.loadGame("testGame");
        System.out.println("<---------- game loaded twice ---------->");
        Engine engine = new Engine(model2, model2.getLevel(new LevelIdentifier("testLevel",
                "testCampaign")));
        return engine;
    }

    private SelectableGameElementState createGoalDisplayMarker () throws SaveLoadException {
        SelectableGameElementState goalDisplayMarker = new SelectableGameElementState(0, 0, null);
        goalDisplayMarker.addType("goalMarker");
        goalDisplayMarker.myAttributes.setTextualAttribute(StateTags.NAME.getValue(), "goalMarker");
        AnimatorState goalAnimations = generateNullAnimatorState();

        goalDisplayMarker.myAttributes.setTextualAttribute(StateTags.CURRENT_ACTION.getValue(),
                "STANDING");
        goalDisplayMarker.myAttributes.setNumericalAttribute(StateTags.TEAM_COLOR.getValue(),
                Long.parseLong("0000FF", 16));
        goalDisplayMarker.myAnimatorState = goalAnimations;

        return goalDisplayMarker;

    }

    private SelectableGameElementState createBall (double x, double y) throws Exception {
        SelectableGameElementState ball = new SelectableGameElementState(x, y, null);
        ball.myAttributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), x);
        ball.myAttributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), y);
        ball.myAttributes.setNumericalAttribute(StateTags.X_GOAL_POSITION.getValue(), x);
        ball.myAttributes.setNumericalAttribute(StateTags.Y_GOAL_POSITION.getValue(), y);
        ball.myAttributes.setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION.getValue(), x);
        ball.myAttributes.setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION.getValue(), y);
        ball.myAttributes.setNumericalAttribute(StateTags.Y_VELOCITY.getValue(), 2);
        ball.myAttributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED.getValue(), 2);
        ball.myAttributes.setTextualAttribute(StateTags.CURRENT_ACTION.getValue(), "STANDING");
        ball.myAttributes.setNumericalAttribute(StateTags.TEAM_COLOR.getValue(),
                Long.parseLong("FF0000", 16));
        ball.myAttributes.setNumericalAttribute("MinMovementSpeed", 4);
        ball.addType("ball");

        ball.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_TYPE_CHECK_ACTION, "paddle", "me", "0", "Equals", "0",
                "Equals", "me", "0", "Bounce", "Equals", "0", "my", "0"));
        ball.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_TYPE_CHECK_ACTION, "boundary", "me", StateTags.X_VELOCITY
                        .getValue(), "EqualsAssignment", "-1", "MultiplicationAssignment", "me",
                StateTags.X_VELOCITY.getValue(), "FalseEvaluator", "Equals", "0", "my", "0"));

        ball.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.PERFORM_CALCULATION_ON_VALUE, "MultiplicationAssignment", "1.2",
                StateTags.MOVEMENT_SPEED.getValue()));
        ball.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_TYPE_CHECK_ACTION, "goal", "me", StateTags.X_POSITION
                        .getValue(), "EqualsAssignment", "1", "Multiplication", "me",
                StateTags.X_GOAL_POSITION.getValue(), "FalseEvaluator", "Equals", "0", "my", "0"));
        ball.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_TYPE_CHECK_ACTION, "goal", "me", StateTags.Y_POSITION
                        .getValue(), "EqualsAssignment", "1", "Multiplication", "me",
                StateTags.Y_GOAL_POSITION.getValue(), "FalseEvaluator", "Equals", "0", "my", "0"));
        ball.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_TYPE_CHECK_ACTION, "goal", "me", StateTags.X_VELOCITY
                        .getValue(), "EqualsAssignment", "0", "Multiplication", "me", "0",
                "FalseEvaluator", "Equals", "0", "my", "0"));
        ball.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_TYPE_CHECK_ACTION, "goal", "me", StateTags.MOVEMENT_SPEED
                        .getValue(), "EqualsAssignment", "1", "Multiplication", "me",
                "MinMovementSpeed", "FalseEvaluator", "Equals", "0", "my", "0"));
        ball.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_TYPE_CHECK_ACTION, "goal", "me", StateTags.Y_VELOCITY
                        .getValue(), "EqualsAssignment", "1", "Multiplication", "me",
                StateTags.MOVEMENT_SPEED.getValue(), "FalseEvaluator", "Equals", "0", "my", "0"));

        AnimatorState ballAnimations = generateNullAnimatorState();
        // This one moves the player
        ball.addAction(new ActionWrapper(ActionType.INTERNAL, ActionOptions.ACT_ON_OBJECTS_ACTION,
                "MovePlayer"));

        ball.myAnimatorState = ballAnimations;
        return ball;
    }

    private AnimatorState generateNullAnimatorState () {
        Set<AnimationSequence> sequence = new HashSet<>();
        List<AnimationTag> tag = new ArrayList<>();
        tag.add(AnimationTag.DO_NOTHING);
        sequence.add(new AnimationSequence(tag, 0, 0));
        AnimatorState ballAnimations = new AnimatorState(
                "resourcesold/gameelementresources/icons/commands/22.png", "",
                new Dimension(39, 39), 1, sequence);
        return ballAnimations;
    }

    private SelectableGameElementState createBoundary (double x, double y) throws SaveLoadException {
        SelectableGameElementState boundary = new SelectableGameElementState(x, y, null);
        boundary.myAttributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), x);
        boundary.myAttributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), y);
        boundary.myAttributes.setNumericalAttribute(StateTags.X_GOAL_POSITION.getValue(), x);
        boundary.myAttributes.setNumericalAttribute(StateTags.Y_GOAL_POSITION.getValue(), y);
        boundary.myAttributes.setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION.getValue(), x);
        boundary.myAttributes.setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION.getValue(), y);
        boundary.myAttributes.setTextualAttribute(StateTags.CURRENT_ACTION.getValue(), "STANDING");
        boundary.myAttributes.setNumericalAttribute(StateTags.TEAM_COLOR.getValue(),
                Long.parseLong("0000FF", 16));
        boundary.addType("boundary");
        double[] bounds = { 0, 0, 0, 1000, 10, 1000, 10, 0 };
        boundary.setBounds(bounds);

        AnimatorState boundaryAnimations = generateNullAnimatorState();
        boundary.myAnimatorState = boundaryAnimations;

        return boundary;

    }

    private SelectableGameElementState createGoalRegion (double x, double y, boolean mine)
            throws SaveLoadException {
        SelectableGameElementState boundary = new SelectableGameElementState(x, y, null);
        boundary.myAttributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), x);
        boundary.myAttributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), y);
        boundary.myAttributes.setNumericalAttribute(StateTags.X_GOAL_POSITION.getValue(), x);
        boundary.myAttributes.setNumericalAttribute(StateTags.Y_GOAL_POSITION.getValue(), y);
        boundary.myAttributes.setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION.getValue(), x);
        boundary.myAttributes.setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION.getValue(), y);
        boundary.myAttributes.setTextualAttribute(StateTags.CURRENT_ACTION.getValue(), "STANDING");
        boundary.myAttributes.setNumericalAttribute(StateTags.TEAM_COLOR.getValue(),
                Long.parseLong("FFFF00", 16));
        boundary.myAttributes.setNumericalAttribute("1", 1);
        boundary.myAttributes.setNumericalAttribute(StateTags.X_SPAWN_OFFSET.getValue(), 900);
        boundary.myAttributes.setNumericalAttribute(StateTags.Y_SPAWN_OFFSET.getValue(),
                (mine ? 500 : -800));
        boundary.addType("goal");
        boundary.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_TYPE_CHECK_ACTION, "ball", "me", "spawnScore",
                "EqualsAssignment", "1", "Addition", "me", "0", "FalseEvaluator",
                "AdditionAssignment", "1", (mine ? "other" : "my"), StateTags.RESOURCES.getValue()));
        boundary.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.CHECK_CONDITION_CREATE_OBJECT_ACTION, "spawnScore", "Equals", "1",
                "goalMarker", "0", "0", "0", "0", "", "", "spawnScore", "0"));
        boundary.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_TYPE_CHECK_ACTION, "ball", "me", StateTags.Y_SPAWN_OFFSET
                        .getValue(), "EqualsAssignment", "75", "AdditionAssignment", "me",
                StateTags.Y_SPAWN_OFFSET.getValue(), "FalseEvaluator", "Addition", "0", "0", "0"));
        // TODO make this create a score sprite when a point is scored...
        double[] bounds = { 0, 0, 1500, 0, 1500, 10, 0, 10 };
        boundary.setBounds(bounds);

        AnimatorState boundaryAnimations = generateNullAnimatorState();
        boundary.myAnimatorState = boundaryAnimations;

        return boundary;

    }

    private SelectableGameElementState createPaddle (double x, double y, int teamID)
            throws Exception {

        SelectableGameElementState paddle = new SelectableGameElementState(x, y, null);
        paddle.myAttributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), x);
        paddle.myAttributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), y);
        paddle.myAttributes.setNumericalAttribute(StateTags.X_GOAL_POSITION.getValue(), x);
        paddle.myAttributes.setNumericalAttribute(StateTags.Y_GOAL_POSITION.getValue(), y);
        paddle.myAttributes.setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION.getValue(), x);
        paddle.myAttributes.setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION.getValue(), y);
        paddle.myAttributes.setTextualAttribute(StateTags.CURRENT_ACTION.getValue(), "STANDING");
        paddle.myAttributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED.getValue(), 6);
        paddle.myAttributes.setNumericalAttribute(StateTags.TEAM_COLOR.getValue(),
                Long.parseLong("0000FF", 16));

        paddle.addType("paddle");
        paddle.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.ACT_ON_OBJECTS_ACTION, "MovePlayer"));

        AnimatorState paddleAnimations = generateNullAnimatorState();
        paddle.myAnimatorState = paddleAnimations;

        return paddle;
    }

    public static void main (String[] args) {
        launch(args);
    }

}
