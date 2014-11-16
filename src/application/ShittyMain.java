package application;

import game_engine.Engine;
import game_engine.gameRepresentation.renderedRepresentation.Game;
import game_engine.gameRepresentation.stateRepresentation.CampaignState;
import game_engine.gameRepresentation.stateRepresentation.GameState;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.inputManagers.InputEvent;
import game_engine.inputManagers.InputHandler;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Dimension;
import game_engine.visuals.Spritesheet;
import game_engine.visuals.VisualManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ShittyMain extends Application {
    @Override
    public void start (Stage primaryStage) {
        Object saveLoadUtility = new Object(); // loololol
        Engine engine = new Engine(hardCodeAGame(), saveLoadUtility);
        engine.selectLevel("TestLevel");
        primaryStage.setScene(engine.getScene());
        primaryStage.show();
    }

    private GameState hardCodeAGame () {
        SelectableGameElementState archerState = new SelectableGameElementState(96, 0);
        archerState
                .setSpritesheet(new Spritesheet(
                                                "resources/img/graphics/units/archer/stand/forward/stand_fwd.png",
                                                new Dimension(49, 51), 1));
        archerState.addAnimation(new AnimationSequence("stand_fwd", 0, 10, true, 0.3));
        archerState.setAnimation("stand_fwd");
        DrawableGameElementState grassState = new DrawableGameElementState(0, 0);
        grassState.setSpritesheet(new Spritesheet("resources/img/graphics/terrain/grass/1.png",
                                                  new Dimension(96, 48), 1));
        DrawableGameElementState grassState2 = new DrawableGameElementState(96, 0);
        grassState2.setSpritesheet(new Spritesheet("resources/img/graphics/terrain/grass/20.png",
                                                  new Dimension(96, 48), 1));
        DrawableGameElementState grassState3 = new DrawableGameElementState(48, 24);
        grassState3.setSpritesheet(new Spritesheet("resources/img/graphics/terrain/grass/2.png",
                                                  new Dimension(96, 48), 1));
        DrawableGameElementState grassState4 = new DrawableGameElementState(48, -24);
        grassState4.setSpritesheet(new Spritesheet("resources/img/graphics/terrain/grass/2.png",
                                                  new Dimension(96, 48), 1));
        LevelState levelState = new LevelState();
        levelState.addTerrain(grassState);
        levelState.addTerrain(grassState2);
        levelState.addTerrain(grassState3);
        levelState.addTerrain(grassState4);
        levelState.addUnit(archerState);

        levelState.name = "TestLevel";
        CampaignState campaignState = new CampaignState();
        campaignState.addLevel(levelState);
        GameState gameState = new GameState();
        gameState.addCampaign(campaignState);
        return gameState;
    }

    public static void main (String[] args) {
        launch(args);
    }

    public void shittyRun (Group g) {
        System.out.println("Shitty running");
        Image poop = new Image("resources/img/poop.png");
        g.getChildren().add(new ImageView(poop));

        // Add the input handler to the group
        InputHandler handler = new InputHandler(g);
        InputEvent<MouseEvent, Group> keyPressedInput =
                new InputEvent<MouseEvent, Group>(MouseEvent.MOUSE_CLICKED, g, group -> {
                    group.getChildren().get(0)
                            .setRotate(group.getChildren().get(0).getRotate() + 20);
                    System.out.println("Clicked");
                });
    }

}
