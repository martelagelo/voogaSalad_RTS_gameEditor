package engine.gameRepresentation.evaluatables.actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.stateManaging.GameElementManager;

/**
 * A class that follows the factory pattern for creating actions from
 * ActionWrappers.
 *
 * @author Zach, Michael R., John L.
 *
 */
public class EvaluatableFactory {
    private EvaluatorFactory myEvaluatorFactory;
    private GameElementManager myGameElementManager;
    private ParticipantManager myParticipantManager;

    /**
     * Initialize the action factory
     */
    public EvaluatableFactory (EvaluatorFactory evaluatorFactory) {
        myEvaluatorFactory = evaluatorFactory;
    }

    /**
     * Set the factory of the game element manager
     *
     * @param manager
     */
    public void setGameElementManager (GameElementManager manager) {
        myGameElementManager = manager;
    }

    /**
     * Set the participant manager to be a given manager
     *
     * @param manager
     */
    public void setParticipantManager (ParticipantManager manager) {
        myParticipantManager = manager;
    }

    /**
     * Create an action from an action wrapper
     *
     * @param wrapper
     *            the action wrapper
     * @return the action
     * @throws ClassNotFoundException
     *             if the class isn't found
     * @throws ActionCreationException
     *             if the action can't be created
     */
    public Evaluatable<?> createAction (ActionWrapper wrapper) throws ClassNotFoundException,
            ActionCreationException {
        String actionClassName = wrapper.getActionClassName();
        String[] actionParams = wrapper.getParameters();
        String currentPackage = this.getClass().getPackage().getName();
        // Added because java generics don't play nicely with reflection
        @SuppressWarnings("unchecked")
        Class<Evaluatable<?>> evaluatableClass = (Class<Evaluatable<?>>) Class
                .forName(currentPackage + "." + actionClassName);
        try {
            Constructor<Evaluatable<?>> constructor = evaluatableClass.getConstructor(
                    EvaluatorFactory.class, GameElementManager.class, ParticipantManager.class,
                    String[].class);
            return constructor.newInstance(myEvaluatorFactory, myGameElementManager,
                    myParticipantManager, actionParams);
        } catch (NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ActionCreationException();
        }
    }
}
