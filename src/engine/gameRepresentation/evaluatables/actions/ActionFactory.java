package engine.gameRepresentation.evaluatables.actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.stateManaging.GameElementManager;


/**
 * A class that follows the factory pattern for creating actions from ActionWrappers.
 * 
 * @author Zach, Michael R., John L.
 *
 */
public class ActionFactory {
    private EvaluatorFactory myEvaluatorFactory;
    private GameElementManager myGameElementManager;
    private ParticipantManager myParticipantManager;

    /**
     * Initialize the action factory
     */
    public ActionFactory (EvaluatorFactory evaluatorFactory,
                          GameElementManager elementManager,
                          ParticipantManager participantManager) {
        myEvaluatorFactory = evaluatorFactory;
        myGameElementManager = elementManager;
        myParticipantManager = participantManager;
    }

    /**
     * Set the factory of the game element manager
     * 
     * @param manager
     */
    public void setGameElementManager (GameElementManager manager) {
        myGameElementManager = manager;
    }

    public void setParticipantManager (ParticipantManager manager) {
        myParticipantManager = manager;
    }

    /**
     * Create an action from an action wrapper
     * 
     * @param wrapper the action wrapper
     * @return the action
     * @throws ClassNotFoundException if the class isn't found
     * @throws ActionCreationException if the action can't be created
     */
    public Evaluatable<?> createAction (ActionWrapper wrapper) throws ClassNotFoundException,
                                                              ActionCreationException {
        String actionClassName = wrapper.getActionClassName();
        String[] actionParams = wrapper.getParameters();
        String currentPackage = this.getClass().getPackage().getName();
        Class<Evaluatable<?>> evaluatableClass =
                (Class<Evaluatable<?>>) Class.forName(currentPackage + "." + actionClassName);
        try {
            Constructor<Evaluatable<?>> constructor =
                    evaluatableClass.getConstructor(String.class, EvaluatorFactory.class,
                                                    GameElementManager.class, String[].class);
            return constructor.newInstance(wrapper.getActionClassName(), myEvaluatorFactory,
                                           myGameElementManager, wrapper.getParameters());
        }
        catch (NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ActionCreationException();
        }
    }
}
