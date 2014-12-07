package engine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import model.MainModel;
import model.exceptions.DescribableStateException;
import model.state.LevelState;
import org.json.JSONException;
import engine.UI.InputManager;
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

    private static final String DEFAULT_BACKGROUND_TILE = "resources/img/graphics/terrain/grass/GrassTile.jpg";
    private MainModel myMainModel;
    private GameLoop myGameLoop;
    private LevelState myLevelState;

    private GameElementManager myElementManager;
    private VisualManager myVisualManager;
    private InputManager myInputManager;
    private ParticipantManager myParticipantManager;

    private GameElementFactory myElementFactory;
    private LevelFactory myLevelFactory;
    private ActionFactory myEvaluatableFactory;
    private VisualizerFactory myVisualizerFactory;

    private HumanParticipant myUser;
    
    public Engine (MainModel mainModel, LevelState levelState)
        throws ClassNotFoundException, JSONException, IOException {
        myMainModel = mainModel;
        myLevelState = levelState;
        System.out.println("Engine version of level state: " + myLevelState.toJSON());
        // TODO fix this so it isn't null
        myEvaluatableFactory = new ActionFactory(new EvaluatorFactory(), null, null);
        myVisualizerFactory = new VisualizerFactory(new AnimatorFactory(myMainModel));
        myElementFactory =
                new GameElementFactory(myMainModel.getGameUniverse(), myEvaluatableFactory,
                                       myVisualizerFactory);
        myLevelFactory = new LevelFactory(myElementFactory);
 
        myUser = new HumanParticipant("BLUE", "Username");

        instantiateManagers();
    }

    public void setInputManager (Class<?> inputManagerClass) throws InstantiationException,
                                                            IllegalAccessException,
                                                            IllegalArgumentException,
                                                            InvocationTargetException,
                                                            NoSuchMethodException,
                                                            SecurityException {
        InputManager inputManager =
                (InputManager) inputManagerClass.getDeclaredConstructor(MainModel.class,
                                                                        GameElementManager.class,
                                                                        GameLoop.class,
                                                                        Participant.class)
                        .newInstance(myMainModel, myElementManager, myGameLoop, myUser);
        myInputManager = inputManager;
        myVisualManager.attachInputManager(myInputManager);
    }
    
    public void setAnimationEnabled(boolean b){
        myVisualizerFactory.setAnimationEnabled(b);
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
        String backgroundURI = DEFAULT_BACKGROUND_TILE;
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
