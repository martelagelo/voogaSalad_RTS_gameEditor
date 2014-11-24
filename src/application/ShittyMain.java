package application;

import game_engine.Engine;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Dimension;
import game_engine.visuals.ScrollablePane;
import game_engine.visuals.Spritesheet;
import game_engine.visuals.TerrainGrid;
import gamemodel.MainModel;
import java.awt.Toolkit;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class ShittyMain extends Application {
    
    public static final java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int shittyWidth = 1279;
    
    @Override
    public void start (Stage primaryStage) {
        try {
            Engine engine = new Engine(hardCodeAGame());
            Group g = new Group();
            ScrollablePane pane = engine.getScene();
            g.getChildren().add(pane);
            Scene s = new Scene(g, screenSize.getWidth(), screenSize.getHeight());
            s.getStylesheets().add(this.getClass().getClassLoader().getResource("game_engine/visuals/stylesheets/engine.style.css").toExternalForm());
            System.out.println(s.getStylesheets());
            primaryStage.setScene(s);
            primaryStage.show();
            engine.play();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("what did you expect this is shit");
        }
    }

    private MainModel hardCodeAGame () throws Exception {
        
        SelectableGameElementState archeryRange = new SelectableGameElementState(600, 600);
        double[] archeryBounds = {-100,0,-100,175,150,175,150,0};
        archeryRange.setSpritesheet(new Spritesheet("resources/img/graphics/buildings/archeryRange.png", new Dimension(312, 260), 1));
        archeryRange.setBounds(archeryBounds);
        archeryRange.setNumericalAttribute(DrawableGameElementState.TEAM_ID, 1);

        double[] bounds = {0,0,40,0,40,40,0,40};
        
        SelectableGameElementState archerState = createArcher(bounds, 100, 100, 1, 0);
        SelectableGameElementState archerState2 = createArcher(bounds, 400, 100, 1, 0);
        SelectableGameElementState archerState3 = createArcher(bounds, 100, 200, 1, 0);
        SelectableGameElementState archerState4 = createArcher(bounds, 400, 200, 2, 0);
        SelectableGameElementState archerState5 = createArcher(bounds, 600, 200, 2, 1);


        
        TerrainGrid grid =
                new TerrainGrid(ScrollablePane.FIELD_WIDTH, ScrollablePane.FIELD_HEIGHT);
        List<DrawableGameElementState> grassTerrain = grid.renderTerrain();

        MainModel model = new MainModel();
        model.newGame("TestGame");
        model.createCampaign("TestCampaign");
        model.createLevel("TestLevel", "TestCampaign");
        LevelState levelState = model.getCurrentLevel();

        for (DrawableGameElementState s : grassTerrain) {
            levelState.addTerrain(s);
        }
        levelState.addUnit(archerState);
        levelState.addUnit(archerState2);
        levelState.addUnit(archerState3);
        levelState.addUnit(archerState4);
        levelState.addUnit(archerState5);
        levelState.addUnit(archeryRange);
        return model;
    }

    private SelectableGameElementState createArcher (double[] bounds, double x, double y, int teamID, int randomMovement) {
        SelectableGameElementState archerState = new SelectableGameElementState(x, y);
        archerState
                .setSpritesheet(new Spritesheet(
                                                "resources/img/graphics/units/eagleWarrior.png",
                                                new Dimension(294, 98), 14));
        archerState.setNumericalAttribute(DrawableGameElementState.CAN_MOVE_STRING, 1);

        /**
         * TODO: we need to initialize these programmatically. Also, the animation should somehow
         * be applied to a type of unit, rather than to each unit individually.
         */
        // setting standing animations
        archerState.addAnimation(new AnimationSequence("stand_fwd", 0, 9, true, 0.2));
        archerState.addAnimation(new AnimationSequence("stand_fwd_left", 15, 23, true, 0.2));
        archerState.addAnimation(new AnimationSequence("stand_left", 29, 37, true, 0.2));
        archerState.addAnimation(new AnimationSequence("stand_bk_left", 43, 51, true, 0.2));
        archerState.addAnimation(new AnimationSequence("stand_bk", 57, 65, true, 0.2));
        archerState.addAnimation(new AnimationSequence("stand_bk_right", 71, 79, true, 0.2));
        archerState.addAnimation(new AnimationSequence("stand_right", 85, 93, true, 0.2));
        archerState.addAnimation(new AnimationSequence("stand_fwd_right", 99, 107, true, 0.2));
        archerState.setBounds(bounds);
        // setting walking animations
        archerState.addAnimation(new AnimationSequence("walk_fwd", 112, 125, true, 0.4));
        archerState.addAnimation(new AnimationSequence("walk_fwd_left", 126, 139, true, 0.4));
        archerState.addAnimation(new AnimationSequence("walk_left", 140, 153, true, 0.4));
        archerState.addAnimation(new AnimationSequence("walk_bk_left", 154, 167, true, 0.4));
        archerState.addAnimation(new AnimationSequence("walk_bk", 168, 181, true, 0.4));
        archerState.addAnimation(new AnimationSequence("walk_bk_right", 182, 195, true, 0.4));
        archerState.addAnimation(new AnimationSequence("walk_right", 196, 209, true, 0.4));
        archerState.addAnimation(new AnimationSequence("walk_fwd_right", 210, 223, true, 0.4));
        archerState.setAnimation("walk_left");

        TerrainGrid grid =
                new TerrainGrid(ScrollablePane.FIELD_WIDTH, ScrollablePane.FIELD_HEIGHT);
        List<DrawableGameElementState> grassTerrain = grid.renderTerrain();
        archerState.setNumericalAttribute(DrawableGameElementState.RANDOM_MOVEMENT_STRING, randomMovement);
        archerState.setNumericalAttribute(DrawableGameElementState.TEAM_ID, teamID);
        return archerState;
    }

    public static void main (String[] args) {
        launch(args);
    }

//    public void shittyRun (Group g) {
//        System.out.println("Shitty running");
//        Image poop = new Image("resources/img/poop.png");
//        g.getChildren().add(new ImageView(poop));
//
//        new InputHandler(g);
//        new InputEvent<MouseEvent, Group>(MouseEvent.MOUSE_CLICKED, g, group -> {
//            group.getChildren().get(0)
//                    .setRotate(group.getChildren().get(0).getRotate() + 20);
//            System.out.println("Clicked");
//        });
//    }

}
