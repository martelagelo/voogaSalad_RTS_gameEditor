package application;

import java.util.List;
import game_engine.Engine;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.inputManagers.InputEvent;
import game_engine.inputManagers.InputHandler;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Dimension;
import game_engine.visuals.ScrollableScene;
import game_engine.visuals.Spritesheet;
import game_engine.visuals.TerrainGrid;
import gamemodel.MainModel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

        SelectableGameElementState archerState = new SelectableGameElementState(96, 0);
        archerState
                .setSpritesheet(new Spritesheet(
                                                "resources/img/graphics/units/eagleWarrior.png",
                                                new Dimension(294, 98), 14));
        archerState.addAnimation(new AnimationSequence("stand_fwd", 0, 9, true, 0.2));
        archerState.setAnimation("stand_fwd");

        TerrainGrid grid = new TerrainGrid(ScrollableScene.FIELD_WIDTH, ScrollableScene.FIELD_HEIGHT);
        List<DrawableGameElementState> grassTerrain = grid.renderTerrain();
        
        MainModel model = new MainModel();
        model.loadGame("TestGame");
        model.createCampaign("TestCampaign");
        model.createLevel("TestLevel", "TestCampaign");
        LevelState levelState = model.getCurrentLevel();

        for(DrawableGameElementState s : grassTerrain){
            levelState.addTerrain(s);
        }
        levelState.addUnit(archerState);
        return model;
    }

    public static void main (String[] args) {
        launch(args);
    }

    public void shittyRun (Group g) {
        System.out.println("Shitty running");
        Image poop = new Image("resources/img/poop.png");
        g.getChildren().add(new ImageView(poop));

        new InputHandler(g);
        new InputEvent<MouseEvent, Group>(MouseEvent.MOUSE_CLICKED, g, group -> {
            group.getChildren().get(0)
                    .setRotate(group.getChildren().get(0).getRotate() + 20);
            System.out.println("Clicked");
        });
    }

}