package application;

import java.awt.Toolkit;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MainModel;
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
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerState;
import engine.visuals.ScrollablePane;
import engine.visuals.elementVisuals.animations.AnimatorState;


public class ShittyMain extends Application {

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
        SelectableGameElementState archerState =
                createArcher(new double[] { 0, 0, 40, 0, 40, 40, 0, 40 }, 200, 200, 1);
        archerState.attributes.setTextualAttribute(StateTags.TEAM_COLOR, "BLUE");
        archerState.attributes.setTextualAttribute(StateTags.NAME, "archer");
        archerState
                .addAttributeDisplayerState(new AttributeDisplayerState("attributeBar",
                                                                        StateTags.HEALTH, 0, 500));

        archerState.attributes
                .setTextualAttribute(StateTags.ATTRIBUTE_DESCRIPTION + 1,
                                     "resources/img/graphics/buttons/buildings/0001.bmp");

        SelectableGameElementState archerState1 =
                createArcher(new double[] { 0, 0, 40, 0, 40, 40, 0, 40 }, 200, 400, 1);
        archerState1.attributes.setTextualAttribute(StateTags.TEAM_COLOR, "BLUE");
        archerState1.addAttributeDisplayerState(new AttributeDisplayerState("attributeBar",
                                                                            StateTags.HEALTH, 0,
                                                                            500));
        archerState.addType("archerman");

        SelectableGameElementState archerState2 =
                createArcher(new double[] { 0, 0, 40, 0, 40, 40, 0, 40 }, 400, 100, 2);
        archerState2.attributes.setTextualAttribute(StateTags.TEAM_COLOR, "RED");
        archerState2.attributes.setNumericalAttribute(StateTags.HEALTH, 100);
        archerState2.attributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED, 0);
        archerState2.attributes.setNumericalAttribute("MAX_HEALTH",1000);
        archerState2.addAttributeDisplayerState(new AttributeDisplayerState("attributeBar",
                                                                            StateTags.HEALTH, 0,
                                                                            1000));
        archerState2.addAction(new ActionWrapper(ActionType.INTERNAL,
                                                 ActionOptions.ATTRIBUTE_INCRIMENT_ACTION,StateTags.HEALTH,StateTags.ATTACK,"MAX_HEALTH","50","Random Timer"));

        SelectableGameElementState archerState3 =
                createArcher(new double[] { 0, 0, 40, 0, 40, 40, 0, 40 }, 400, 300, 1);
        archerState3.attributes.setTextualAttribute(StateTags.TEAM_COLOR, "GREEN");
        // Make the third archer spawn archers on collision
        archerState3.attributes.setNumericalAttribute(StateTags.X_SPAWN_OFFSET, 500);
        archerState3.attributes.setNumericalAttribute(StateTags.Y_SPAWN_OFFSET, 500);

        // archerState3.addAction(new ActionWrapper(ActionType.COLLISION,
        // ActionOptions.INCREMENT_DECREMENT_ACTION,
        // "archerman", StateTags.HEALTH, StateTags.ATTACK,
        // "50", StateTags.HEALTH, "LeechTimer", "100"));

        // archerState3.addAction(new
        // ActionWrapper(ActionType.COLLISION,ActionOptions.ATTRIBUTE_INTERACTION_ACTION,"Transfer",StateTags.HEALTH,"my","Resources"));
        archerState3.addAttributeDisplayerState(new AttributeDisplayerState("attributeBar",
                                                                            StateTags.HEALTH, 0,
                                                                            500));

        // TerrainGrid grid = new TerrainGrid(ScrollablePane.FIELD_WIDTH,
        // ScrollablePane.FIELD_HEIGHT);
        // List<DrawableGameElementState> grassTerrain = grid.renderTerrain();

        LevelState levelState = new LevelState("testLevel");
        // for (DrawableGameElementState s : grassTerrain) {
        // levelState.addTerrain(s);
        // }
        levelState.addUnit(archerState);
        // levelState.addUnit(archerState1);
        levelState.addUnit(archerState2);
        levelState.addUnit(archerState3);
        levelState.attributes.setNumericalAttribute(StateTags.LEVEL_WIDTH, 2000);
        levelState.attributes.setNumericalAttribute(StateTags.LEVEL_HEIGHT, 2000);
        levelState.addGoal(createGoal());

        CampaignState campaignState = new CampaignState("testCampaign");
        campaignState.addLevel(levelState);

        GameState gameState = new GameState("testGame");
        gameState.addCampaign(campaignState);

        MainModel model = new MainModel();
        model.saveGame(gameState);
        model.loadGame("testGame");
        model.getGameUniverse().addSelectableGameElementState(archerState);
        model.getGameUniverse().addSelectableGameElementState(archerState1);
        model.getGameUniverse().addSelectableGameElementState(archerState2);
        model.getGameUniverse().addSelectableGameElementState(archerState3);
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
        ges.addAction(new ActionWrapper(ActionType.INTERNAL,
                                        ActionOptions.PLAYER_ATTRIBUTE_CONDITION, "my",
                                        "Resources", "GreaterThanEqual", "1000", "Won",
                                        "EqualsAssignment", "1"));
        return ges;
    }

    private SelectableGameElementState createArcher (double[] bounds, double x, double y, int teamID)
                                                                                                     throws Exception {
        SelectableGameElementState archerState = new SelectableGameElementState(x, y);
        archerState.attributes.setNumericalAttribute(StateTags.TEAM_ID, teamID);
        archerState.attributes.setNumericalAttribute(StateTags.X_POSITION, x);
        archerState.attributes.setNumericalAttribute(StateTags.Y_POSITION, y);
        archerState.attributes.setNumericalAttribute(StateTags.X_GOAL_POSITION, x);
        archerState.attributes.setNumericalAttribute(StateTags.Y_GOAL_POSITION, y);
        archerState.attributes.setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION, x);
        archerState.attributes.setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION, y);
        archerState.attributes.setNumericalAttribute(StateTags.HEALTH, 500);
        archerState.attributes.setNumericalAttribute(StateTags.ATTACK, 75);
        archerState.attributes.setNumericalAttribute(StateTags.RELOAD_TIME, 50);
        archerState.attributes.setTextualAttribute(StateTags.CURRENT_ACTION, "STANDING");
        archerState.attributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED, 2);
        archerState.addType("archer");
        // Choose a random temporary waypoint if we collide with anything
        archerState.addAction(new ActionWrapper(ActionType.COLLISION,
                                                ActionOptions.OBJECT_CONDITION_ACTION,
                                                "NotCollision", "RandomWaypoint"));
        // On collision, attack an enemy
        archerState.addAction(new ActionWrapper(ActionType.COLLISION,
                                                ActionOptions.OBJECT_CONDITION_ACTION,
                                                "Collision", "Attack"));
        // Move back if we collide with anything
        archerState.addAction(new ActionWrapper(ActionType.COLLISION,
                                                ActionOptions.OBJECT_CONDITION_ACTION,
                                                "Collision", "MoveBack"));
        // Check to see if our health is <0. If so, die.
        archerState
                .addAction(new ActionWrapper(ActionType.INTERNAL,
                                             ActionOptions.CHECK_ATTR_SET_ATTR_ACTION,
                                             StateTags.HEALTH,
                                             "LessThanEqual",
                                             "0",
                                             StateTags.IS_DEAD, "EqualsAssignment", "1"));
        // Update player direction
        archerState.addAction(new ActionWrapper(ActionType.INTERNAL,
                                                ActionOptions.ACT_ON_OBJECTS_ACTION,
                                                "UpdateMovementDirection"));
        // This one moves the player
        archerState.addAction(new ActionWrapper(ActionType.INTERNAL,
                                                ActionOptions.ACT_ON_OBJECTS_ACTION,
                                                "MovePlayer"));
        // This one can be used for pathing
        archerState.addAction(new ActionWrapper(ActionType.INTERNAL,
                                                ActionOptions.ACT_ON_OBJECTS_ACTION,
                                                "HeadingUpdate"));
        // Make the element so it follows another player when right-clicked on
        archerState.addAction(new ActionWrapper(ActionType.FOCUSED,
                                                ActionOptions.ACT_ON_OBJECTS_ACTION, "Follow"));
        archerState.addAction(new ActionWrapper(ActionType.BUTTON,
                                                ActionOptions.CHECK_CONDITION_CREATE_OBJECT_ACTION,
                                                StateTags.LAST_BUTTON_CLICKED_ID, "Equals", "1",
                                                "archer", StateTags.HEALTH, "Resources",
                                                "ArcherSpawnCooldown", "1000"));
        archerState.attributes.setNumericalAttribute(StateTags.X_SPAWN_OFFSET, 100);
        archerState.attributes.setNumericalAttribute(StateTags.Y_SPAWN_OFFSET, 100);

        archerState.setBounds(bounds);
        // TESTING SAVING SGES
        SaveLoadUtility.save(archerState, "resources/sges.json");
        AnimatorState archerAnimations =
                SaveLoadUtility
                        .loadResource(AnimatorState.class,
                                      "resources/gameelementresources/animatorstate/archer.json");
        archerState.myAnimatorState = archerAnimations;
        // TESTING LOADING SGES
        SelectableGameElementState sges =
                SaveLoadUtility.loadResource(
                                             SelectableGameElementState.class,
                                             "resources/sges.json");
        Map<String, List<ActionWrapper>> map = sges.getActions();

        return archerState;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
