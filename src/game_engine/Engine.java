package game_engine;

import game_engine.UI.InputManager;
import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.stateManaging.GameElementManager;
import game_engine.stateManaging.GameLoop;
import game_engine.visuals.MiniMap;
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
 * @author Steve, Jonathan, Nishad, Rahul, Michael D.
 *
 */
public class Engine extends Observable implements Observer {

    public static final Integer SCREEN_WIDTH = 600;
    public static final Integer SCREEN_HEIGHT = 600;

    private MainModel myMainModel;
    private GameLoop myGameLoop;
    private GameElementManager myElementManager;
    private VisualManager myVisualManager;
    private InputManager myInputManager;
    private MiniMap myMiniMap;

    public Engine (MainModel mainModel) {
        // TODO hard-coding the visual representation for now, should remove this dependency
        myMainModel = mainModel;
        myInputManager = new InputManager();
        myVisualManager = new VisualManager(new Group(), myInputManager, SCREEN_WIDTH, SCREEN_HEIGHT);
        myMiniMap = new MiniMap(myVisualManager.getScene());
    }

    public Group getVisualRepresentation () {
        // TODO: figure out what stores our shit
        // TODO: currently unused, possibly remove this
        return myVisualManager.getVisualRepresentation();
    }

    public void selectLevel (String campaignName, String levelName)
                                                                   throws DescribableStateException {
        myMainModel.setCurrentLevel(campaignName, levelName);
        Level newLevel = new Level(myMainModel.getCurrentLevel());
        myMiniMap.setUnits(newLevel.getUnits());
        myGameLoop = new GameLoop(newLevel, myVisualManager, myMiniMap);
        myElementManager = new GameElementManager(newLevel);
        myVisualManager.addObjects(newLevel.getGroup());
        myVisualManager.addBoxObserver(myElementManager);
        myInputManager.addClickObserver(myElementManager);
        myInputManager.addKeyboardObserver(myElementManager);
        myVisualManager.addMiniMap(myMiniMap.getDisplay());
    }

    public void play () {
        updateGameLoop(myMainModel.getCurrentLevel());
        myGameLoop.play();
    }

    public void pause () {
        myGameLoop.pause();
    }
    
    public MiniMap getMiniMap(){
    	return myMiniMap;
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
            myMiniMap.setUnits(nextLevel.getUnits());
            myGameLoop = new GameLoop(nextLevel, myVisualManager, myMiniMap);
            myElementManager = new GameElementManager(nextLevel);
            myVisualManager.addObjects(nextLevel.getGroup());
            myVisualManager.addBoxObserver(myElementManager);
            myInputManager.addClickObserver(myElementManager);
            myInputManager.addKeyboardObserver(myElementManager);
            myVisualManager.addMiniMap(myMiniMap.getDisplay());
        }
    }

}
