package engine.gameRepresentation.evaluatables.actions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.FalseEvaluator;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;
import engine.users.Participant;


/**
 * The wrapper for an action class. Actions are a layer of abstraction that was added on top of
 * evaluators in order to make more strict type casting of parameters with evaluators. The action
 * hierarchy arose from a time constraint on evaluatable parsing from strings. Essentially, actions
 * represent commands with inputable commands for functionality. @see ActionOptions This allows
 * for specifying complex actions using only string pieces as opposed to necessitating the user to
 * type in long VScript strings for actions.
 * 
 * @author Zach
 *
 */
public abstract class Action extends Evaluatable<Boolean> {
    private Evaluatable<?> myAction;
    public final static String MY_ELEMENT = "me";
    public final static String MY_PARTICIPANT = "my";

    /**
     * Create an action
     * 
     * @param id the type id of the action tree. Allows leaves to reference the tree as a whole
     * @param factory the factory for evaluators
     * @param args any arguments that dictate the makeup of the Action. These are specified through
     *        the ActionTypes.java file
     */
    public Action (EvaluatorFactory factory,
                   GameElementManager elementManager, ParticipantManager participantManager,
                   String[] args) {
        super(Boolean.class);
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

    /**
     * Below are some helper methods to keep actions DRY
     */

    /**
     * Get an object of interest identifier based on a string
     * 
     * @param objectIdentifierString the string for the object of interest.
     * @return the object of interest identifier
     */
    protected ObjectOfInterestIdentifier makeObjectIdentifier (String objectIdentifierString) {
        if (objectIdentifierString.equals(MY_ELEMENT)) { return new ActorObjectIdentifier(); }
        return new ActeeObjectIdentifier();
    }

    /**
     * Identify participants given an identifier string
     * 
     * @param participantIdentifier the string to identify the participants
     * @param manager the participant manager
     * @return the participants of interest
     */
    protected List<Participant> identifyParticipantsOfInterest (String participantIdentifier,
                                                                ParticipantManager manager) {
        return (participantIdentifier.equals(MY_PARTICIPANT) ? Arrays.asList(manager.getUser())
                                                            : manager.getAI());
    }

    // ----------------------------------------------------------------------------------------
    // Below are methods and variables used to perform common evaluatable creations in actions
    // Used to keep action code as DRY as possible. Although this is a lot of methods for one
    // class to have, the decision to put all these methods in one class allowed for a much
    // more succinct creation of actions in all other action children as the evaluatable
    // hierarchy was originally designed to be read from string input and translated into
    // a class tree using a parser.
    // ----------------------------------------------------------------------------------------
    protected final static GameElementParameter ACTOR =
            new GameElementParameter(new ActorObjectIdentifier());
    protected final static GameElementParameter ACTEE =
            new GameElementParameter(new ActeeObjectIdentifier());

    /**
     * Execute a given action if a timer has expired
     * 
     * @param element the element containing the timer
     * @param timerName the name of the timer
     * @param timerReloadTime the amount of time until the timer can execute again
     * @param elements a grouping of important elements for the action to execute on
     * @param action the action to execute
     * @return a boolean indicating if he action was executed
     */
    protected boolean executeActionIfTimerExpired (GameElement element,
                                                   String timerName,
                                                   long timerReloadTime,
                                                   ElementPair elements,
                                                   Consumer<ElementPair> action) {
        if (element.getTimer(timerName) <= 0) {
            element.setTimer(timerName, timerReloadTime);
            action.accept(elements);
            return true;
        }
        return false;

    }

}
