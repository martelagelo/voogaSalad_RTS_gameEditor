package game_engine;

import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.stateManaging.GameElementManager;
import game_engine.stateManaging.GameLoop;
import game_engine.visuals.VisualManager;
import gamemodel.MainModel;
import gamemodel.exceptions.DescribableStateException;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import javafx.scene.Scene;


/**
 * The concrete boundary of the Engine - this class exposes the public API of the Engine to the rest
 * of the project.
 *
 * @author Steve, Jonathan, Nishad, Rahul
 *
 */
public class Engine extends Observable implements Observer {

    public static final Integer SCREEN_WIDTH = 600;
    public static final Integer SCREEN_HEIGHT = 600;

    private MainModel myMainModel;
    private GameLoop myGameLoop;
    private GameElementManager myElementManager;
    private VisualManager myVisualManager;

    public Engine (MainModel mainModel) {
        // TODO hard-coding the visual representation for now, should remove this dependency
        myMainModel = mainModel;
        myVisualManager = new VisualManager(new Group(), SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public Group getVisualRepresentation () {
        // TODO: figure out what stores our shit
        // TODO: currently unused, possibly remove this
        return myVisualManager.getVisualRepresentation();
    }

    public void selectLevel (String campaignName, String levelName) throws DescribableStateException {
        myMainModel.setCurrentLevel(campaignName, levelName);
        Level newLevel = new Level(myMainModel.getCurrentLevel());
        myGameLoop = new GameLoop(newLevel, myVisualManager);
        myElementManager = new GameElementManager(newLevel);
        myVisualManager.addObjects(newLevel.getGroup());
        myVisualManager.addBoxObserver(myElementManager);
        myVisualManager.addClickObserver(myElementManager);
        myVisualManager.addKeyboardObserver(myElementManager);
    }

    public void play () {
        updateGameLoop(myMainModel.getCurrentLevel());
        myGameLoop.play();
    }

    public void pause () {
        myGameLoop.pause();
    }

    @Override
    public void update (Observable observable, Object arg) {
        // TODO Auto-generated method stub
        updateGameLoop(myMainModel.getCurrentLevel());
    }

    public Scene getScene () {
        return myVisualManager.getScene();
    }

    private void updateGameLoop (LevelState levelState) {
        // TODO check equlity
        if (myGameLoop == null || !myGameLoop.isCurrentLevel(levelState)) {
            Level nextLevel = new Level(levelState);
            myGameLoop = new GameLoop(nextLevel, myVisualManager);
            myElementManager = new GameElementManager(nextLevel);
            myVisualManager.addObjects(nextLevel.getGroup());
            myVisualManager.addBoxObserver(myElementManager);
            myVisualManager.addClickObserver(myElementManager);
            myVisualManager.addKeyboardObserver(myElementManager);
        }
    }

}
