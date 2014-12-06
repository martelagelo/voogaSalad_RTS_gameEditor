package engine;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import model.MainModel;
import model.exceptions.DescribableStateException;
import model.state.CampaignState;
import model.state.LevelState;
import org.json.JSONException;
import application.ShittyMain;
import engine.UI.ParticipantManager;
import engine.UI.RunnerInputManager;
import engine.elementFactories.AnimatorFactory;
import engine.elementFactories.GameElementFactory;
import engine.elementFactories.LevelFactory;
import engine.elementFactories.VisualizerFactory;
import engine.gameRepresentation.evaluatables.actions.ActionFactory;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.renderedRepresentation.Level;
import engine.stateManaging.GameElementManager;
import engine.stateManaging.GameLoop;
import engine.users.HumanParticipant;
import engine.visuals.ScrollablePane;
import engine.visuals.VisualManager;


/**
 * The concrete boundary of the Engine - this class exposes the public API of
 * the Engine to the rest of the project.
 *
 * @author Steve, Jonathan, Michael D., John L.
 *
 */
// TODO: probably doesn't need to be observable or observer
public class Engine extends Observable implements Observer {

    private MainModel myMainModel;
    private GameLoop myGameLoop;
    private LevelState myLevelState;
    private CampaignState myCampaignState;

    private GameElementManager myElementManager;
    private VisualManager myVisualManager;
    private RunnerInputManager myInputManager;
    private ParticipantManager myParticipantManager;

    private GameElementFactory myElementFactory;
    private LevelFactory myLevelFactory;
    private ActionFactory myEvaluatableFactory;
    private VisualizerFactory myVisualizerFactory;

    private HumanParticipant myUser;

    public Engine (MainModel mainModel, CampaignState campaignState, LevelState levelState)
        throws ClassNotFoundException, JSONException, IOException {
        myMainModel = mainModel;
        myCampaignState = campaignState;
        myLevelState = levelState;
        // TODO fix this so it isn't null
        myEvaluatableFactory = new ActionFactory(new EvaluatorFactory(), null, null);
        myVisualizerFactory = new VisualizerFactory(new AnimatorFactory(myMainModel));
        myElementFactory =
                new GameElementFactory(myMainModel.getGameUniverse(), myEvaluatableFactory,
                                       myVisualizerFactory);
        myLevelFactory = new LevelFactory(myElementFactory);
        myUser = new HumanParticipant(1, "Yo fuckin' user name!!");

        instantiateManagers();
    }

    // TODO Should just be a call for "get next level" from main model rather than
    // selecting a specific level
    public void selectNextLevel () throws DescribableStateException {
        // myLevelState = myMainModel.(campaignName, levelName);
        // myCampaignState = myMainModel.getCampaign(campaignName);
        // TODO: get next level and next campaign state in model (also write method in model)
        instantiateManagers();
    }

    private void instantiateManagers () {
        // TODO hard-coding the visual representation for now, should remove
        // this dependency
        myVisualManager =
                new VisualManager(new Group(), ShittyMain.shittyWidth,
                                  0.9 * ShittyMain.screenSize.getHeight());
        myElementManager = new GameElementManager(myElementFactory);
        // The game evaluatable factory must have its game element manager set after it is created
        myEvaluatableFactory.setGameElementManager(myElementManager);
        // The next level needs to then be created
        Level nextLevel = myLevelFactory.createLevel(myLevelState);
        // Finally, the GameElementManager needs to have its next level set
        myElementManager.setLevel(nextLevel);

        myParticipantManager = new ParticipantManager(myUser, myElementManager);
        myEvaluatableFactory.setParticipantManager(myParticipantManager);
        
        myGameLoop =
                new GameLoop(myCampaignState.getName(), nextLevel, myVisualManager,
                             myElementManager, myParticipantManager);
        nextLevel.getGroups().stream().forEach(g -> myVisualManager.addObject(g));
        myInputManager = new RunnerInputManager(myMainModel, myElementManager, myGameLoop, myUser);
        myVisualManager.attachInputManager(myInputManager);
        myVisualManager.attachParticipantManager(myParticipantManager);
    }

    public void play () {
        myGameLoop.play();
    }

    public void pause () {
        myGameLoop.pause();
    }

    @Override
    public void update (Observable observable, Object arg) {
        // TODO: decide if we need Engine to be observer still
        // TODO utilize or delete the inputs to this method
    }

    public ScrollablePane getScene () {
        return myVisualManager.getScrollingScene();
    }

}
