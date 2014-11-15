    package application;

import game_engine.Engine;
import game_engine.gameRepresentation.renderedRepresentation.Game;
import game_engine.gameRepresentation.stateRepresentation.CampaignState;
import game_engine.gameRepresentation.stateRepresentation.GameState;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.inputManagers.InputEvent;
import game_engine.inputManagers.InputHandler;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Dimension;
import game_engine.visuals.Spritesheet;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import player.VisualManager;


public class ShittyMain extends Application {
    @Override
    public void start (Stage primaryStage) {
        
        Object saveLoadUtility = new Object(); // loololol
        Engine engine = new Engine(hardCodeAGame(),saveLoadUtility);
        primaryStage.setScene(engine.getScene());
        primaryStage.show();
        
        
    }

    private GameState hardCodeAGame () {
        SelectableGameElementState archerState = new SelectableGameElementState(10,10);
        archerState.setSpritesheet(new Spritesheet("resources/img/exploBig.png",new Dimension(40,40),7));
        archerState.addAnimation(new AnimationSequence("Walk",0,13,true));
        
        LevelState levelState = new LevelState();
        levelState.addUnit(archerState);
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
