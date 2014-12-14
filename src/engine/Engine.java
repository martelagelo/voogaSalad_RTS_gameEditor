package engine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
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

    public static final String DEFAULT_PLAYER_COLOR_STRING = "0x0000ffff";
    public static final int DEFAULT_PLAYER_COLOR = colorIntFromString(DEFAULT_PLAYER_COLOR_STRING);
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
        // TODO fix this so it isn't null
        myEvaluatableFactory = new ActionFactory(new EvaluatorFactory(), null, null);
        myVisualizerFactory = new VisualizerFactory(new AnimatorFactory(myMainModel));
        myElementFactory =
                new GameElementFactory(myMainModel.getGameUniverse(), myEvaluatableFactory,
                                       myVisualizerFactory);
        myLevelFactory = new LevelFactory(myElementFactory);

        myUser = new HumanParticipant(DEFAULT_PLAYER_COLOR, "Username");

        instantiateManagers(levelState.attributes.getTextualAttribute(StateTags.BACKGROUND_PATH
                .getValue()));
    }

    public static Color colorFromInt (int colorValue) {
        return Color.web(String.format("0x%s", colorStringFromInt(colorValue)), 1.0);
    }

    public static int colorIntFromString (String color) {
        return Integer.parseInt(color.substring(2), 16);
    }
    
    public static String colorStringFromInt(int color) {
        String colorString = String.format("000000%s", Integer.toHexString(color));
        return colorString.substring(colorString.length() - 6);
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

    public void setAnimationEnabled (boolean b) {
        myVisualizerFactory.setAnimationEnabled(b);
    }

    private void instantiateManagers (String backgroundPath) {
        myElementManager = new GameElementManager(myElementFactory);
        // The game evaluatable factory must have its game element manager set after it is created
        myEvaluatableFactory.setGameElementManager(myElementManager);
        myParticipantManager = new ParticipantManager(myUser, myElementManager);
        myEvaluatableFactory.setParticipantManager(myParticipantManager);
        // The next level needs to then be created
        Level nextLevel = myLevelFactory.createLevel(myLevelState);
        // Finally, the GameElementManager needs to have its next level set
        myElementManager.setLevel(nextLevel);
        myVisualManager =
                new VisualManager(new Group(), nextLevel.getMapWidth(), nextLevel.getMapHeight(),
                                  backgroundPath);

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
