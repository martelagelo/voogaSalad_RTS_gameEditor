package game_engine;

import game_engine.UI.InputManager;
import game_engine.elementFactories.AnimatorFactory;
import game_engine.elementFactories.GameElementFactory;
import game_engine.elementFactories.LevelFactory;
import game_engine.elementFactories.VisualizerFactory;
import game_engine.gameRepresentation.evaluatables.EvaluatableFactory;
import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.stateManaging.GameElementManager;
import game_engine.stateManaging.GameLoop;
import game_engine.visuals.MiniMap;
import game_engine.visuals.ScrollablePane;
import game_engine.visuals.VisualManager;
import gamemodel.MainModel;
import gamemodel.exceptions.DescribableStateException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import org.json.JSONException;
import application.ShittyMain;


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

    // TODO : move into visual manager
    private MiniMap myMiniMap;

    private GameElementFactory myElementFactory;
    private LevelFactory myLevelFactory;
    private EvaluatableFactory myEvaluatableFactory;
    private VisualizerFactory myVisualizerFactory;

    public Engine (MainModel mainModel) throws ClassNotFoundException, JSONException, IOException {
        myMainModel = mainModel;
        myInputManager = new InputManager();
        myEvaluatableFactory = new EvaluatableFactory();
        myVisualizerFactory = new VisualizerFactory(new AnimatorFactory(myMainModel));
        myElementFactory =
                new GameElementFactory(myMainModel.getGameUniverse(), myEvaluatableFactory,
                                       myVisualizerFactory);
        myLevelFactory = new LevelFactory(myElementFactory);

        // TODO hard-coding the visual representation for now, should remove this dependency
        myVisualManager =
                new VisualManager(new Group(), myInputManager, ShittyMain.shittyWidth,
                                  0.9 * ShittyMain.screenSize.getHeight());
        // TODO: minimap should be inside of visual manager
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
        Level newLevel = myLevelFactory.createLevel(myMainModel.getCurrentLevel());
        myMiniMap.setUnits(newLevel.getUnits());
        myGameLoop = new GameLoop(campaignName, newLevel, myVisualManager, myMiniMap);
        myElementManager = new GameElementManager(newLevel, myElementFactory);
        myEvaluatableFactory.setManager(myElementManager);
        newLevel.getGroups().stream().forEach(g -> myVisualManager.addObjects(g));
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
        // updateGameLoop(myMainModel.getCurrentLevel());
    }

    public ScrollablePane getScene () {
        return myVisualManager.getScrollingScene();
    }

    private void updateGameLoop (LevelState levelState, String currentCampaign) {
        // TODO check equlity
        // TOOD: this code is copy-pasted from the selectLevel method ... unify and reduce code
        // waste
        if (myGameLoop == null || !myGameLoop.isCurrentLevel(levelState, currentCampaign)) {
            Level nextLevel = myLevelFactory.createLevel(myMainModel.getCurrentLevel());
            myMiniMap.setUnits(nextLevel.getUnits());
            myGameLoop = new GameLoop(currentCampaign, nextLevel, myVisualManager, myMiniMap);
            myElementManager = new GameElementManager(nextLevel, myElementFactory);
            myEvaluatableFactory.setManager(myElementManager);
            nextLevel.getGroups().stream().forEach(g -> myVisualManager.addObjects(g));
            myVisualManager.addBoxObserver(myElementManager);
            myInputManager.addClickObserver(myElementManager);
            myInputManager.addKeyboardObserver(myElementManager);
            myVisualManager.addMiniMap(myMiniMap.getDisplay());
        }
    }

}
