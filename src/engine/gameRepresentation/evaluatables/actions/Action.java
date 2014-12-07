package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.FalseEvaluator;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.stateManaging.GameElementManager;
import engine.users.Participant;


/**
 * The wrapper for an action class. Actions are a layer of abstraction that was added on top of
 * evaluators in order to make more strict type casting of parameters with evaluators. This allows
 * for specifying complex actions using only string pieces as opposed to necessitating the user to
 * type in long VScript strings for actions.
 * 
 * @author Zach
 *
 */
public abstract class Action extends Evaluatable<Boolean> {
    private Evaluatable<?> myAction;

    /**
     * Create an action
     * 
     * @param id the type id of the action tree. Allows leaves to reference the tree as a whole
     * @param factory the factory for evaluators
     * @param args any arguments that dictate the makeup of the Action. These are specified through
     *        the ActionTypes.java file
     */
    public Action (String id,
                   EvaluatorFactory factory,
                   GameElementManager elementManager, ParticipantManager participantManager,
                   String[] args) {
        super(Boolean.class, id);
        try {
            myAction = initializeAction(args, factory, elementManager, participantManager);
        }
        catch (ClassNotFoundException | EvaluatorCreationException e) {
            e.printStackTrace();
            myAction = new FalseEvaluator<>();
        }

    }

    /**
     * Initialize the action's action evaluatable for execution given the factory and the arguments
     * of the action.
     * 
     * @return the evaluatable for the action
     */
    protected abstract Evaluatable<?> initializeAction (String[] args,
                                                        EvaluatorFactory factory,
                                                        GameElementManager elementManager,
                                                        ParticipantManager participantManager)
                                                                                              throws ClassNotFoundException,
                                                                                              EvaluatorCreationException;

    @Override
    public Boolean evaluate (ElementPair elements) {
        return evaluate(myAction, elements);
    }

    /**
     * Check any necessary conditions and evaluate the action's internal action
     * 
     * @param action the internal fundamental action of the action
     * @param elements any elements that may be acted upon
     * @return a boolean indicating whether action execution was successful
     */
    protected abstract Boolean evaluate (Evaluatable<?> action, ElementPair elements);
}
