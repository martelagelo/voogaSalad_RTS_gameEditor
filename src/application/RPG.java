package application;

import java.awt.Toolkit;
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
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerType;
import engine.visuals.ScrollablePane;
import engine.visuals.elementVisuals.animations.AnimatorState;


public class RPG extends Application {

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
        String uri = "resources/img/graphics/terrain/grass/1.png";
        double[] normalBounds = new double[] { 0, 0, 100, 0, 100, 100, 0, 100 };
        SelectableGameElementState hero =
                createMovingUnit(normalBounds, 2000, 10000, 30, 20, 20, 10, 1, uri);
        hero.attributes.setTextualAttribute(StateTags.TEAM_COLOR, "BLUE");
        hero
                .addAttributeDisplayerState(new AttributeDisplayerState(
                                                                        AttributeDisplayerType.AttributeBarDisplayer,
                                                                        StateTags.HEALTH, 0, 1000));
        hero
                .addAction(new ActionWrapper(ActionType.INTERNAL,
                                             ActionOptions.CHECK_ATTR_SET_ATTR_ACTION,
                                             "me",
                                             "toDie",
                                             "Equals",
                                             "1", "me",
                                             StateTags.IS_DEAD, "EqualsAssignment", "1"));

        // set up a healing ability
        hero.addAction(new ActionWrapper(ActionType.BUTTON, ActionOptions.MOTHER_OF_ALL_ACTIONS,
                                         "my", StateTags.RESOURCES, "GreaterThanEqual", "healCost",
                                         "me", StateTags.LAST_BUTTON_CLICKED_ID, "Equals",
                                         "active", "me", StateTags.HEALTH, "AdditionAssignment",
                                         "healAmount", "me", "blah", "Equals", "active", "Equals",
                                         "a", "zero", "SubtractionAssignment", "healCost", "my",
                                         StateTags.RESOURCES, "1", "HealingCooldown"));
        hero.attributes.setNumericalAttribute("healCost", 150);
        hero.attributes.setNumericalAttribute("healAmount", 5000);
        hero.attributes.setNumericalAttribute("active", 1);
        hero.attributes.setNumericalAttribute("HealingCooldown", 1000);
        String defendURIString = "resources/img/graphics/buttons/commands/defend.png";
        hero.attributes.setTextualAttribute(StateTags.ATTRIBUTE_DESCRIPTION + 1, defendURIString);

        // set the health to have a limiter
        hero.addAction(new ActionWrapper(ActionType.INTERNAL, ActionOptions.MOTHER_OF_ALL_ACTIONS,
                                         "my", "", "Equals", "", "me", StateTags.HEALTH,
                                         "GreaterThanEqual", "maxHealth", "me", StateTags.HEALTH,
                                         "EqualsAssignment", "maxHealth", "me", "", "Equals", "",
                                         "Equals", "a", "zero", "Equals", "df", "my", "w", "f", "w"));

        String enemyUri = "resources/img/graphics/terrain/grass/1.png";
        SelectableGameElementState enemy =
                this.createEnemyUnit(normalBounds, 300, 100, 75, 600, 600, 2, 2, enemyUri);
        enemy
                .addAttributeDisplayerState(new AttributeDisplayerState(
                                                                        AttributeDisplayerType.AttributeBarDisplayer,
                                                                        StateTags.HEALTH, 0, 300));
        enemy.attributes.setTextualAttribute(StateTags.NAME, "Enemy");
        enemy.attributes.setTextualAttribute(StateTags.TEAM_COLOR, "GREEN");

        LevelState levelState = new LevelState("testLevel");

        SelectableGameElementState spawner1 = createSpawnPoint(1500, 1500, "GREEN");
        levelState.addUnit(spawner1);

        levelState.addUnit(hero);
        levelState.addUnit(enemy);

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
        model.getGameUniverse().addSelectableGameElementState(spawner1);
        model.getGameUniverse().addSelectableGameElementState(hero);
        model.getGameUniverse().addSelectableGameElementState(enemy);

        model.saveGame();
        MainModel model2 = new MainModel();
        model2.loadGame("testGame");
        Engine engine =
                new Engine(model2, model2.getLevel("testCampaign", "testLevel"));

        engine.setAnimationEnabled(false);

        return engine;
    }

    private SelectableGameElementState createSpawnPoint (double x, double y, String Color)
                                                                                          throws Exception {
        SelectableGameElementState archerState = new SelectableGameElementState(x, y);
        archerState.attributes.setNumericalAttribute(StateTags.X_POSITION, x);
        archerState.attributes.setNumericalAttribute(StateTags.Y_POSITION, y);
        archerState.attributes.setNumericalAttribute(StateTags.RELOAD_TIME, 50);
        archerState.attributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED, 0);
        archerState.addType("spawner");
        archerState.attributes.setNumericalAttribute("isSpawn", 1);
        archerState.attributes.setNumericalAttribute("unitCost", 0);

        // set empty bounds to do nothing
        archerState.setBounds(new double[8]);

        // make spawn point action
        archerState.addAction(new ActionWrapper(ActionType.INTERNAL,
                                                ActionOptions.CHECK_CONDITION_CREATE_OBJECT_ACTION,
                                                "isSpawn", "Equals", "1",
                                                "Enemy", "unitCost", "Resources",
                                                "1", "500"));

        archerState.attributes.setNumericalAttribute(StateTags.X_SPAWN_OFFSET, -100);
        archerState.attributes.setNumericalAttribute(StateTags.Y_SPAWN_OFFSET, -100);

        AnimatorState archerAnimations =
                SaveLoadUtility
                        .loadResource(AnimatorState.class,
                                      "resources/gameelementresources/animatorstate/archer.json");
        archerState.myAnimatorState = archerAnimations;

        return archerState;
    }

    private GameElementState createGoal () {
        GameElementState ges = new GameElementState();
        ges.attributes.setNumericalAttribute("GoalSatisfied", 0);
        ges.addAction(new ActionWrapper(ActionType.INTERNAL,
                                        ActionOptions.PLAYER_ATTRIBUTE_CONDITION, "my",
                                        "Resources", "GreaterThanEqual", "1000", "Won",
                                        "EqualsAssignment", "1"));
        // ges.addAction(new ActionWrapper(ActionType.INTERNAL,
        // ActionOptions.OBJECT_LOCATION_DETECTION,
        // "my", "archer", "50", "50", "50", "Won", "EqualsAssignment", "1"));
        return ges;
    }

    private SelectableGameElementState createEnemyUnit (double[] bounds,
                                                        double health,
                                                        double attack,
                                                        double reloadTime,
                                                        double x,
                                                        double y,
                                                        double movementSpeed,
                                                        int teamID,
                                                        String pictureURI) throws Exception {
        SelectableGameElementState enemy =
                createMovingUnit(bounds, health, attack, reloadTime, x, y, movementSpeed, teamID,
                                 pictureURI);
        enemy.addType("Enemy");

        enemy.addAction(new ActionWrapper(ActionType.INTERNAL, ActionOptions.MOTHER_OF_ALL_ACTIONS,
                                          "my", "", "Equals", "", "me", "toDie", "Equals",
                                          "active", "me", StateTags.IS_DEAD, "EqualsAssignment",
                                          "active", "me", "asdf", "Equals", "active", "Equals",
                                          "a", "123", "AdditionAssignment", "myValue", "my",
                                          StateTags.RESOURCES, "b", "zero"));
        enemy.attributes.setNumericalAttribute("active", 1);
        enemy.attributes.setNumericalAttribute("zero", 0);
        enemy.attributes.setNumericalAttribute("myValue", 200);

        return enemy;
    }

    private SelectableGameElementState createMovingUnit (double[] bounds,
                                                         double health,
                                                         double attack,
                                                         double reloadTime,
                                                         double x,
                                                         double y,
                                                         double movementSpeed,
                                                         int teamID,
                                                         String pictureURI) throws Exception {
        SelectableGameElementState archerState = new SelectableGameElementState(x, y);
        archerState.attributes.setNumericalAttribute(StateTags.X_POSITION, x);
        archerState.attributes.setNumericalAttribute(StateTags.Y_POSITION, y);
        archerState.attributes.setNumericalAttribute(StateTags.X_GOAL_POSITION, x);
        archerState.attributes.setNumericalAttribute(StateTags.Y_GOAL_POSITION, y);
        archerState.attributes.setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION, x);
        archerState.attributes.setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION, y);
        archerState.attributes.setNumericalAttribute(StateTags.HEALTH, health);
        archerState.attributes.setNumericalAttribute("maxHealth", health);
        archerState.attributes.setNumericalAttribute(StateTags.ATTACK, attack);
        archerState.attributes.setNumericalAttribute(StateTags.RELOAD_TIME, reloadTime);
        archerState.attributes.setTextualAttribute(StateTags.CURRENT_ACTION, "STANDING");
        archerState.attributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED, movementSpeed);
        archerState.addType("archer");
        // Choose a random temporary waypoint if we collide with anything
        archerState.addAction(new ActionWrapper(ActionType.COLLISION,
                                                ActionOptions.OBJECT_CONDITION_ACTION,
                                                "NotCollision", "RandomWaypoint"));
        // Move back if we collide with anything
        archerState.addAction(new ActionWrapper(ActionType.COLLISION,
                                                ActionOptions.OBJECT_CONDITION_ACTION,
                                                "Collision", "MoveBack"));
        // On collision, attack an enemy
        archerState.addAction(new ActionWrapper(ActionType.COLLISION,
                                                ActionOptions.OBJECT_CONDITION_ACTION,
                                                "Collision", "Attack"));

        // Check to see if our health is <0. If so, die.
        archerState
                .addAction(new ActionWrapper(ActionType.INTERNAL,
                                             ActionOptions.CHECK_ATTR_SET_ATTR_ACTION,
                                             "me",
                                             StateTags.HEALTH,
                                             "LessThanEqual",
                                             "0", "me",
                                             "toDie", "EqualsAssignment", "1"));
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

        archerState.setBounds(bounds);

        AnimatorState archerAnimations =
                SaveLoadUtility
                        .loadResource(AnimatorState.class,
                                      "resources/gameelementresources/animatorstate/archer.json");
        archerState.myAnimatorState = archerAnimations;

        return archerState;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
