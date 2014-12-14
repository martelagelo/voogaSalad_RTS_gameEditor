package engine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import model.MainModel;
import model.state.LevelState;
import model.state.gameelement.StateTags;

import org.json.JSONException;

import engine.UI.InputManager;
import engine.UI.ParticipantManager;
import engine.UI.RunnerInputManager;
import engine.elementFactories.AnimatorFactory;
import engine.elementFactories.GameElementFactory;
import engine.elementFactories.LevelFactory;
import engine.elementFactories.VisualizerFactory;
import engine.gameRepresentation.evaluatables.actions.EvaluatableFactory;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.renderedRepresentation.Level;
import engine.stateManaging.GameElementManager;
import engine.stateManaging.GameLoop;
import engine.users.HumanParticipant;
import engine.users.Participant;
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

    public static final String DEFAULT_PLAYER_COLOR_STRING = "0000ff";
    public static final long DEFAULT_PLAYER_COLOR = Long.parseLong(DEFAULT_PLAYER_COLOR_STRING, 16);
    private MainModel myMainModel;
    private GameLoop myGameLoop;
    private LevelState myLevelState;

    private GameElementManager myElementManager;
    private VisualManager myVisualManager;
    private InputManager myInputManager;
    private ParticipantManager myParticipantManager;

    private GameElementFactory myElementFactory;
    private LevelFactory myLevelFactory;
    private EvaluatableFactory myEvaluatableFactory;
    private VisualizerFactory myVisualizerFactory;

    private HumanParticipant myUser;

    public Engine (MainModel mainModel, LevelState levelState) throws ClassNotFoundException,
            JSONException, IOException {
        myMainModel = mainModel;
        myLevelState = levelState;
        myUser = new HumanParticipant(DEFAULT_PLAYER_COLOR, "Username");

        instantiateFactories();
        instantiateManagers();
    }

    private void instantiateFactories () {
        myEvaluatableFactory = new EvaluatableFactory(new EvaluatorFactory());
        myVisualizerFactory = new VisualizerFactory(new AnimatorFactory(myMainModel));
        myElementFactory = new GameElementFactory(myMainModel.getGameUniverse(),
                myEvaluatableFactory, myVisualizerFactory);
        myLevelFactory = new LevelFactory(myElementFactory);
    }

    public void setInputManager (Class<?> inputManagerClass) throws InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        InputManager inputManager = (InputManager) inputManagerClass.getDeclaredConstructor(
                MainModel.class, GameElementManager.class, GameLoop.class, Participant.class)
                .newInstance(myMainModel, myElementManager, myGameLoop, myUser);
        myInputManager = inputManager;
        myVisualManager.attachInputManager(myInputManager);
    }

    private void instantiateManagers () {
        myElementManager = new GameElementManager(myElementFactory);
        myParticipantManager = new ParticipantManager(myUser);

        initializeEvaluatableFactory();
        createLevel();

        myInputManager = new RunnerInputManager(myMainModel, myElementManager, myGameLoop, myUser);

        initializeVisualManager();
    }

    private void createLevel () {
        Level nextLevel = myLevelFactory.createLevel(myLevelState);
        instantiateVisualManager(nextLevel);
        instantiateGameLoop(nextLevel);
        myElementManager.setLevel(nextLevel);
    }

    private void initializeVisualManager () {
        myVisualManager.attachInputManager(myInputManager);
        myVisualManager.attachParticipantManager(myParticipantManager);
    }

    private void initializeEvaluatableFactory () {
        myEvaluatableFactory.setGameElementManager(myElementManager);
        myEvaluatableFactory.setParticipantManager(myParticipantManager);
    }

    private void instantiateVisualManager (Level nextLevel) {
        String backgroundPath = nextLevel.getTextualAttribute(StateTags.BACKGROUND_PATH.getValue());
        myVisualManager = new VisualManager(nextLevel.getMapWidth(), nextLevel.getMapHeight(),
                backgroundPath);
        nextLevel.getGroups().stream().forEach(g -> myVisualManager.addObject(g));
    }

    private void instantiateGameLoop (Level nextLevel) {
        myGameLoop = new GameLoop(nextLevel, myVisualManager, myParticipantManager);
        myGameLoop.addObserver(this);
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
            myGameLoop.stop();
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

    /**
     * Gets the view of the background
     *
     * @return The scene
     */
    public ScrollablePane getScene () {
        return myVisualManager.getScrollingScene();
    }

}
