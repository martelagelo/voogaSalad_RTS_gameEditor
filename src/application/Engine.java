package application;

import game_engine.UI.InputManager;
import game_engine.gameRepresentation.factories.GameElementFactory;
import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.stateManaging.GameElementManager;
import game_engine.stateManaging.GameLoop;
import game_engine.visuals.MiniMap;
import game_engine.visuals.ScrollablePane;
import game_engine.visuals.VisualManager;
import gamemodel.MainModel;
import gamemodel.exceptions.DescribableStateException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;


/**
 * The concrete boundary of the Engine - this class exposes the public API of the Engine to the rest
 * of the project.
 *
 * @author Steve, Jonathan, Nishad, Rahul, Michael D.
 *
 */
public class Engine extends Observable implements Observer {

    private MainModel myMainModel;
    private GameLoop myGameLoop;
    private GameElementManager myElementManager;
    private VisualManager myVisualManager;
    private InputManager myInputManager;
    private MiniMap myMiniMap;
    private GameElementFactory myElementFactory;

    public Engine (MainModel mainModel) {
        // TODO hard-coding the visual representation for now, should remove this dependency
        myMainModel = mainModel;
        myInputManager = new InputManager();
        myVisualManager =
                new VisualManager(new Group(), myInputManager, ShittyMain.screenSize.getWidth(), ShittyMain.screenSize.getHeight());
        myMiniMap = new MiniMap((ScrollablePane) myVisualManager.getScrollingScene());
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
//        myGameLoop = new GameLoop(campaignName, newLevel, myVisualManager);
        myMiniMap.setUnits(newLevel.getUnits());
        myGameLoop = new GameLoop(campaignName, newLevel, myVisualManager, myMiniMap);
//        myGameLoop = new GameLoop(newLevel, myVisualManager, myMiniMap);
        myElementManager = new GameElementManager(newLevel);
        myVisualManager.addObjects(newLevel.getGroup());
        myVisualManager.addBoxObserver(myElementManager);
        myInputManager.addClickObserver(myElementManager);
        myInputManager.addKeyboardObserver(myElementManager);
        myVisualManager.addMiniMap(myMiniMap.getDisplay());
    }

    public void play () {
        updateGameLoop(myMainModel.getCurrentLevel(), myMainModel.getCurrentCampaign().getName());
        myGameLoop.play();
    }

    public void pause () {
        myGameLoop.pause();
    }

    public MiniMap getMiniMap () {
        return myMiniMap;
    }

    @Override
    public void update (Observable observable, Object arg) {
        // TODO Auto-generated method stub
        updateGameLoop(myMainModel.getCurrentLevel(), myMainModel.getCurrentCampaign().getName());
//        updateGameLoop(myMainModel.getCurrentLevel());
    }

    public ScrollablePane getScene () {
        return myVisualManager.getScrollingScene();
    }

    private void updateGameLoop (LevelState levelState, String currentCampaign) {
        // TODO check equlity
        if (myGameLoop == null || !myGameLoop.isCurrentLevel(levelState, currentCampaign)) {
            Level nextLevel = new Level(levelState);
            myMiniMap.setUnits(nextLevel.getUnits());
            myGameLoop = new GameLoop(currentCampaign, nextLevel, myVisualManager, myMiniMap);
            myElementManager = new GameElementManager(nextLevel);
            myVisualManager.addObjects(nextLevel.getGroup());
            myVisualManager.addBoxObserver(myElementManager);
            myInputManager.addClickObserver(myElementManager);
            myInputManager.addKeyboardObserver(myElementManager);
            myVisualManager.addMiniMap(myMiniMap.getDisplay());
        }
    }

}