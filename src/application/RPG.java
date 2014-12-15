package application;

import java.awt.Toolkit;

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
import model.state.gameelement.traits.WidgetState;
import model.state.gameelement.traits.AttributeDisplayerTags;
import util.SaveLoadUtility;
import engine.Engine;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Engine hardCodeAGame () throws Exception {
        String uri = "resources/img/graphics/terrain/grass/1.png";
        double[] normalBounds = new double[] { 0, 0, 100, 0, 100, 100, 0, 100 };
        SelectableGameElementState hero = createMovingUnit(normalBounds, 600, 50, 50, 20, 20, 10,
                1, uri);
        hero.myAttributes.setTextualAttribute(StateTags.TEAM_COLOR.getValue(), "BLUE");
        hero.addWidgetState(new WidgetState(
                AttributeDisplayerTags.ATTRIBUTE_BAR_DISPLAYER, StateTags.HEALTH.getValue(), 0,
                1000));
        hero.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.CHECK_ATTR_SET_ATTR_ACTION, "me", "toDie", "Equals", "1", "me",
                StateTags.IS_DEAD.getValue(), "EqualsAssignment", "1"));

        // set up a healing ability
        hero.addAction(new ActionWrapper(ActionType.BUTTON, ActionOptions.MOTHER_OF_ALL_ACTIONS,
                "my", StateTags.RESOURCES.getValue(), "GreaterThanEqual", "healCost", "me",
                StateTags.LAST_BUTTON_CLICKED_ID.getValue(), "Equals", "active", "me",
                StateTags.HEALTH.getValue(), "AdditionAssignment", "healAmount", "me", "blah",
                "Equals", "active", "Equals", "a", "zero", "SubtractionAssignment", "healCost",
                "my", StateTags.RESOURCES.getValue(), "1", "HealingCooldown"));
        hero.myAttributes.setNumericalAttribute("healCost", 50);
        hero.myAttributes.setNumericalAttribute("healAmount", 5000);
        hero.myAttributes.setNumericalAttribute("active", 1);
        hero.myAttributes.setNumericalAttribute("HealingCooldown", 1000);
        String defendURIString = "resources/img/graphics/buttons/commands/defend.png";
        hero.myAttributes.setTextualAttribute(StateTags.ATTRIBUTE_DESCRIPTION.getValue() + 1,
                defendURIString);

        // set the health to have a limiter
        hero.addAction(new ActionWrapper(ActionType.INTERNAL, ActionOptions.MOTHER_OF_ALL_ACTIONS,
                "my", "", "Equals", "", "me", StateTags.HEALTH.getValue(), "GreaterThanEqual",
                "maxHealth", "me", StateTags.HEALTH.getValue(), "EqualsAssignment", "maxHealth",
                "me", "", "Equals", "", "Equals", "a", "zero", "Equals", "df", "my", "w", "f", "w"));

        // set a level up (!) button
        hero.addAction(new ActionWrapper(ActionType.BUTTON, ActionOptions.MOTHER_OF_ALL_ACTIONS,
                "my", StateTags.RESOURCES.getValue(), "GreaterThanEqual", "levelUpCost", "me",
                StateTags.LAST_BUTTON_CLICKED_ID.getValue(), "Equals", "levelUpButtonID", "me",
                "maxHealth", "AdditionAssignment", "levelUpHealthIncrease", "me", StateTags.ATTACK
                        .getValue(), "AdditionAssignment", "levelUpAttackIncrease", "Equals", "a",
                "", "SubtractionAssignment", "levelUpCost", "my", StateTags.RESOURCES.getValue(),
                "b", ""));
        String levelUpURIString = "resources/gameelementresources/commands/buildOffensive.png";
        hero.myAttributes.setTextualAttribute(StateTags.ATTRIBUTE_DESCRIPTION.getValue() + 16,
                levelUpURIString);
        hero.myAttributes.setNumericalAttribute("levelUpCost", 100);
        hero.myAttributes.setNumericalAttribute("levelUpButtonID", 16);
        hero.myAttributes.setNumericalAttribute("levelUpHealthIncrease", 50);
        hero.myAttributes.setNumericalAttribute("levelUpAttackIncrease", 35);

        String enemyUri = "resources/img/graphics/terrain/grass/1.png";
        SelectableGameElementState enemy = createEnemyUnit(normalBounds, 300, 35, 75, 600, 600, 2,
                2, enemyUri);
        enemy.addWidgetState(new WidgetState(
                AttributeDisplayerTags.ATTRIBUTE_BAR_DISPLAYER, StateTags.HEALTH.getValue(), 0, 300));
        enemy.myAttributes.setTextualAttribute(StateTags.NAME.getValue(), "Enemy");
        enemy.myAttributes.setTextualAttribute(StateTags.TEAM_COLOR.getValue(), "GREEN");

        LevelState levelState = new LevelState("testLevel", "testCampaign");

        SelectableGameElementState spawner1 = createSpawnPoint(1500, 1500, "GREEN");
        levelState.addUnit(spawner1);

        levelState.addUnit(hero);
        levelState.addUnit(enemy);

        levelState.myAttributes.setNumericalAttribute(StateTags.LEVEL_WIDTH.getValue(), 2000);
        levelState.myAttributes.setNumericalAttribute(StateTags.LEVEL_HEIGHT.getValue(), 2000);
        levelState.myAttributes.setTextualAttribute(StateTags.BACKGROUND_PATH.getValue(),
                "resources/img/graphics/terrain/grass/GrassTile.jpg");
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
        LevelIdentifier l = new LevelIdentifier("testLevel", "testCampaign");
        Engine engine = new Engine(model2, model2.getLevel(l));

        return engine;
    }

    private SelectableGameElementState createSpawnPoint (double x, double y, String Color)
            throws Exception {
        SelectableGameElementState archerState = new SelectableGameElementState(x, y, null);
        archerState.myAttributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), x);
        archerState.myAttributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), y);
        archerState.myAttributes.setNumericalAttribute(StateTags.RELOAD_TIME.getValue(), 50);
        archerState.myAttributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED.getValue(), 0);
        archerState.addType("spawner");
        archerState.myAttributes.setNumericalAttribute("isSpawn", 1);
        archerState.myAttributes.setNumericalAttribute("unitCost", 0);

        // set empty bounds to do nothing
        archerState.setBounds(new double[8]);
        archerState.myAttributes.setNumericalAttribute("specialThing", 10);
        // make spawn point action
        archerState.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.CHECK_CONDITION_CREATE_OBJECT_ACTION, "isSpawn", "Equals", "1",
                "Enemy", "unitCost", "Resources", "1", "500", StateTags.HEALTH.getValue() + ","
                        + StateTags.MOVEMENT_SPEED.getValue(), "specialThing,specialThing"));

        archerState.myAttributes.setNumericalAttribute(StateTags.X_SPAWN_OFFSET.getValue(), -100);
        archerState.myAttributes.setNumericalAttribute(StateTags.Y_SPAWN_OFFSET.getValue(), -100);

        AnimatorState archerAnimations = SaveLoadUtility.loadResource(AnimatorState.class,
                "resources/gameelementresources/animatorstate/archer.json");
        archerState.myAnimatorState = archerAnimations;

        return archerState;
    }

    private GameElementState createGoal () {
        GameElementState ges = new GameElementState();
        ges.myAttributes.setNumericalAttribute("GoalSatisfied", 0);
        ges.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.PLAYER_ATTRIBUTE_CONDITION, "my", "Resources", "GreaterThanEqual",
                "1000", "Won", "EqualsAssignment", "1"));
        // ges.addAction(new ActionWrapper(ActionType.INTERNAL,
        // ActionOptions.PLAYER_ATTRIBUTE_CONDITION, "my",
        // "my", "archer", "50", "50", "50", "Won",
        // "EqualsAssignment", "1"));
        return ges;
    }

    private SelectableGameElementState createEnemyUnit (double[] bounds, double health,
            double attack, double reloadTime, double x, double y, double movementSpeed, int teamID,
            String pictureURI) throws Exception {
        SelectableGameElementState enemy = createMovingUnit(bounds, health, attack, reloadTime, x,
                y, movementSpeed, teamID, pictureURI);
        enemy.addType("Enemy");

        enemy.addAction(new ActionWrapper(ActionType.INTERNAL, ActionOptions.MOTHER_OF_ALL_ACTIONS,
                "my", "", "Equals", "", "me", "toDie", "Equals", "active", "me", StateTags.IS_DEAD
                        .getValue(), "EqualsAssignment", "active", "me", "asdf", "Equals",
                "active", "Equals", "a", "123", "AdditionAssignment", "myValue", "my",
                StateTags.RESOURCES.getValue(), "b", "zero"));
        enemy.myAttributes.setNumericalAttribute("active", 1);
        enemy.myAttributes.setNumericalAttribute("zero", 0);
        enemy.myAttributes.setNumericalAttribute("myValue", 30);

        return enemy;
    }

    private SelectableGameElementState createMovingUnit (double[] bounds, double health,
            double attack, double reloadTime, double x, double y, double movementSpeed, int teamID,
            String pictureURI) throws Exception {
        SelectableGameElementState archerState = new SelectableGameElementState(x, y, null);
        archerState.myAttributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), x);
        archerState.myAttributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), y);
        archerState.myAttributes.setNumericalAttribute(StateTags.X_GOAL_POSITION.getValue(), x);
        archerState.myAttributes.setNumericalAttribute(StateTags.Y_GOAL_POSITION.getValue(), y);
        archerState.myAttributes
                .setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION.getValue(), x);
        archerState.myAttributes
                .setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION.getValue(), y);
        archerState.myAttributes.setNumericalAttribute(StateTags.HEALTH.getValue(), health);
        archerState.myAttributes.setNumericalAttribute("maxHealth", health);
        archerState.myAttributes.setNumericalAttribute(StateTags.ATTACK.getValue(), attack);
        archerState.myAttributes
                .setNumericalAttribute(StateTags.RELOAD_TIME.getValue(), reloadTime);
        archerState.myAttributes.setTextualAttribute(StateTags.CURRENT_ACTION.getValue(),
                "STANDING");
        archerState.myAttributes.setNumericalAttribute(StateTags.MOVEMENT_SPEED.getValue(),
                movementSpeed);
        archerState.addType("archer");
        // Choose a random temporary waypoint if we collide with anything
        archerState.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_CONDITION_ACTION, "NotCollision", "RandomWaypoint"));
        // Move back if we collide with anything
        archerState.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_CONDITION_ACTION, "Collision", "MoveBack"));
        // On collision, attack an enemy
        archerState.addAction(new ActionWrapper(ActionType.COLLISION,
                ActionOptions.OBJECT_CONDITION_ACTION, "Collision", "Attack"));

        // Check to see if our health is <0. If so, die.
        archerState.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.CHECK_ATTR_SET_ATTR_ACTION, "me", StateTags.HEALTH.getValue(),
                "LessThanEqual", "0", "me", "toDie", "EqualsAssignment", "1"));
        // Update player direction
        archerState.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.ACT_ON_OBJECTS_ACTION, "UpdateMovementDirection"));
        // This one moves the player
        archerState.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.ACT_ON_OBJECTS_ACTION, "MovePlayer"));
        // This one can be used for pathing
        archerState.addAction(new ActionWrapper(ActionType.INTERNAL,
                ActionOptions.ACT_ON_OBJECTS_ACTION, "HeadingUpdate"));
        // Make the element so it follows another player when right-clicked on
        archerState.addAction(new ActionWrapper(ActionType.FOCUSED,
                ActionOptions.ACT_ON_OBJECTS_ACTION, "Follow"));

        archerState.setBounds(bounds);

        AnimatorState archerAnimations = SaveLoadUtility.loadResource(AnimatorState.class,
                "resources/gameelementresources/animatorstate/archer.json");
        archerState.myAnimatorState = archerAnimations;

        return archerState;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
