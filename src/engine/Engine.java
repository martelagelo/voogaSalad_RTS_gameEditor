package engine;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import model.MainModel;
import model.exceptions.DescribableStateException;
import model.state.LevelState;
import org.json.JSONException;
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

    private GameElementManager myElementManager;
    private VisualManager myVisualManager;
    private RunnerInputManager myInputManager;
    private ParticipantManager myParticipantManager;

    private GameElementFactory myElementFactory;
    private LevelFactory myLevelFactory;
    private ActionFactory myEvaluatableFactory;
    private VisualizerFactory myVisualizerFactory;

    private HumanParticipant myUser;

    private boolean gameWon = false;

    public Engine (MainModel mainModel, LevelState levelState)
        throws ClassNotFoundException, JSONException, IOException {
        myMainModel = mainModel;
        myLevelState = levelState;
        // TODO fix this so it isn't null
        myEvaluatableFactory = new ActionFactory(new EvaluatorFactory(), null, null);
        myVisualizerFactory = new VisualizerFactory(new AnimatorFactory(myMainModel));
        myElementFactory =
                new GameElementFactory(myMainModel.getGameUniverse(), myEvaluatableFactory,
                                       myVisualizerFactory);
        myLevelFactory = new LevelFactory(myElementFactory);
        // TODO:wtf
        myUser = new HumanParticipant("BLUE", "Yo fuckin' user name!!");

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
        myElementManager = new GameElementManager(myElementFactory);
        // The game evaluatable factory must have its game element manager set after it is created
        myEvaluatableFactory.setGameElementManager(myElementManager);
        myParticipantManager = new ParticipantManager(myUser, myElementManager);
        myEvaluatableFactory.setParticipantManager(myParticipantManager);
        // The next level needs to then be created
        Level nextLevel = myLevelFactory.createLevel(myLevelState);
        // Finally, the GameElementManager needs to have its next level set
        myElementManager.setLevel(nextLevel);
        String backgroundURI = "resources/img/graphics/terrain/grass/GrassTile.jpg";
        myVisualManager =
                new VisualManager(new Group(), nextLevel.getMapWidth(), nextLevel.getMapHeight(), backgroundURI);

        myParticipantManager = new ParticipantManager(myUser, myElementManager);

        myGameLoop =
                new GameLoop(nextLevel, myVisualManager,
                             myElementManager, myParticipantManager);
        // to notify engine when level is finished
        myGameLoop.addObserver(this);

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
        if (observable instanceof GameLoop) {
            gameWon = ((int) arg) > 0;
            this.pause();
            System.out.println("Game is over! \n   Game won? " + gameWon);
            // TODO:something with this... display it?
            myUser.getAttributes();
            updateObservers();
        }
    }

    /**
     * called only when game is won
     */
    private void updateObservers () {
        setChanged();
        notifyObservers(myUser.getAttributes());
        clearChanged();
    }
    
    public ScrollablePane getScene () {
        return myVisualManager.getScrollingScene();
    }

}
