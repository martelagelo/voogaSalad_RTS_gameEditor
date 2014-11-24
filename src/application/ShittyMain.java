package application;

import game_engine.Engine;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Dimension;
import game_engine.visuals.ScrollableScene;
import game_engine.visuals.Spritesheet;
import game_engine.visuals.TerrainGrid;
import gamemodel.MainModel;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;


public class ShittyMain extends Application {
    @Override
    public void start (Stage primaryStage) {
        try {
            Engine engine = new Engine(hardCodeAGame());
            primaryStage.setScene(engine.getScene());
            primaryStage.show();
            engine.play();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("what did you expect this is shit");
        }
    }

    private MainModel hardCodeAGame () throws Exception {
        
        SelectableGameElementState archeryRange = new SelectableGameElementState(400, 400);
        archeryRange.setSpritesheet(new Spritesheet("resources/img/graphics/buildings/archeryRange/1.png", new Dimension(312, 260), 1));
        
        SelectableGameElementState archerState = new SelectableGameElementState(300, 100);
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
        
        SelectableGameElementState archerState2 = new SelectableGameElementState(400, 100);
        archerState2
                .setSpritesheet(new Spritesheet(
                                                "resources/img/graphics/units/eagleWarrior.png",
                                                new Dimension(294, 98), 14));
        archerState2.addAnimation(new AnimationSequence("stand_fwd", 0, 9, true, 0.2));
        archerState2.setAnimation("stand_fwd");
        
        archerState2.setNumericalAttribute(DrawableGameElementState.CAN_MOVE_STRING, 1);
        
        archerState2.addAnimation(new AnimationSequence("stand_fwd", 0, 9, true, 0.2));
        archerState2.addAnimation(new AnimationSequence("stand_fwd_left", 15, 23, true, 0.2));
        archerState2.addAnimation(new AnimationSequence("stand_left", 29, 37, true, 0.2));
        archerState2.addAnimation(new AnimationSequence("stand_bk_left", 43, 51, true, 0.2));
        archerState2.addAnimation(new AnimationSequence("stand_bk", 57, 65, true, 0.2));
        archerState2.addAnimation(new AnimationSequence("stand_bk_right", 71, 79, true, 0.2));
        archerState2.addAnimation(new AnimationSequence("stand_right", 85, 93, true, 0.2));
        archerState2.addAnimation(new AnimationSequence("stand_fwd_right", 99, 107, true, 0.2));
        
        // setting walking animations
        archerState2.addAnimation(new AnimationSequence("walk_fwd", 112, 125, true, 0.4));
        archerState2.addAnimation(new AnimationSequence("walk_fwd_left", 126, 139, true, 0.4));
        archerState2.addAnimation(new AnimationSequence("walk_left", 140, 153, true, 0.4));
        archerState2.addAnimation(new AnimationSequence("walk_bk_left", 154, 167, true, 0.4));
        archerState2.addAnimation(new AnimationSequence("walk_bk", 168, 181, true, 0.4));
        archerState2.addAnimation(new AnimationSequence("walk_bk_right", 182, 195, true, 0.4));
        archerState2.addAnimation(new AnimationSequence("walk_right", 196, 209, true, 0.4));
        archerState2.addAnimation(new AnimationSequence("walk_fwd_right", 210, 223, true, 0.4));

        TerrainGrid grid =
                new TerrainGrid(ScrollableScene.FIELD_WIDTH, ScrollableScene.FIELD_HEIGHT);
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
        levelState.addUnit(archeryRange);
        return model;
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
