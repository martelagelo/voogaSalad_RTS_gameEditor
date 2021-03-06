package engine.gameRepresentation.renderedRepresentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import model.state.gameelement.GameElementState;
import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;

/**
 * A basic game element. Contains a state and a map strings to conditions and
 * actions. Also contains an update method to update the element due to its
 * internal state.
 *
 * @author Steve, Zach, Jonathan, Nishad, Rahul, Stanley
 *
 */
public class GameElement {
    /**
     * The action lists map is a map of action strings to lists of possible
     * actions. For example, The String "Collision" might map to a list of
     * actions that should be run whenever an element is dealing with a
     * collision with another element whereas "Action1" might map to a list of
     * actions that should be executed when an element's action button is
     * clicked.
     */
    private Map<ActionType, List<Evaluatable<?>>> myActionLists;
    private GameElementState myState;
    private Map<String, Long> myTimers;

    /**
     * Create a game element with the given state
     *
     * @param gameElementState
     *            the state of the game element
     */
    public GameElement (GameElementState gameElementState) {
        myState = gameElementState;
        myTimers = new HashMap<>();
        createActionLists();
    }

    public GameElementState getState () {
        return myState;
    }

    /**
     * Ensure that lists are instantiated for all the possible action types of
     * an element to protect against uninstantiated action entities at runtime.
     */
    private void createActionLists () {
        if (myActionLists == null) {
            myActionLists = new HashMap<>();
        }
        for (ActionType type : ActionType.values()) {
            if (!myActionLists.containsKey(type)) {
                myActionLists.put(type, new CopyOnWriteArrayList<>());
            }
        }

    }

    /**
     * Get an iterator of the actions with a given tag
     *
     * @param actionType
     *            the tag for the type of action
     * @return an iterator containing all the actions of the desired type
     */
    public Iterator<Evaluatable<?>> getActionsOfType (ActionType actionType) {
        List<Evaluatable<?>> actionsOfInterest;
        if (!myActionLists.containsKey(actionType)) {
            actionsOfInterest = new ArrayList<Evaluatable<?>>();
        } else {
            actionsOfInterest = myActionLists.get(actionType);
        }
        return actionsOfInterest.iterator();

    }

    /**
     * Add an action of the given type to the array of actions
     *
     * @param actionType
     *            the type of the action to be added
     * @param action
     *            the action to be added
     */
    public void addAction (ActionType actionType, Evaluatable<?> action) {
        if (!myActionLists.containsKey(actionType)) {
            myActionLists.put(actionType, new CopyOnWriteArrayList<>());
        }
        myActionLists.get(actionType).add(action);
    }

    /**
     * Get a numeric attribute contained by the game element
     *
     * @param attributTag
     *            the tag of the attribute of interest
     * @return the atribute's value or 0 if the attribute was not declared
     */
    public Number getNumericalAttribute (String attributeTag) {
        return myState.myAttributes.getNumericalAttribute(attributeTag);
    }

    /**
     * Set a numeric attribute contained by the game element
     *
     * @param attributeTag
     *            the tag of the attribute
     * @param attributeValue
     *            the value of the attribute
     */
    public void setNumericalAttribute (String attributeTag, Number attributeValue) {
        myState.myAttributes.setNumericalAttribute(attributeTag, attributeValue);
    }

    /**
     * Get a textual attribute contained by the game element
     *
     * @param attributeTag
     *            the tag of the attribute
     * @return the value of the attribute or "" if it does not exist
     */
    public String getTextualAttribute (String attributeTag) {
        return myState.myAttributes.getTextualAttribute(attributeTag);
    }

    /**
     * Set the value of a textual attribute contained by the game element
     *
     * @param attributeTag
     *            the tag of the attribute
     * @param attributeValue
     *            the attribute's value
     */
    public void setTextualAttribute (String attributeTag, String attributeValue) {
        myState.myAttributes.setTextualAttribute(attributeTag, attributeValue);
    }

    /**
     * Update the element based on its internal state
     */
    public void update () {
        updateTimers();
        updateSelfDueToInternalFactors();
    }

    /**
     * Update the game element due to actions that execute on its internal
     * factors e.g. move the element based on its internal velocity parameters.
     */
    private void updateSelfDueToInternalFactors () {
        executeAllActions(ActionType.INTERNAL);
    }

    /**
     * Execute all the actions in a given key's list. Given an element pair
     *
     * @param actionKey
     *            the key of the action set for which to execute all of the
     *            actions
     * @param ElementPair
     *            an element pair of the current object and any objects it might
     *            be interested in e.g. the current unit and a unit nearby it
     */
    protected void executeAllActions (ActionType actionKey, ElementPair elementPair) {
        getActionsOfType(actionKey).forEachRemaining(action -> action.evaluate(elementPair));
    }

    /**
     * Execute all the actions in a given key's list given that there are no
     * applicable elements for the action set.
     *
     * @param actionKey
     *            the key of the action set for which to execute all of the
     *            actions
     */
    protected void executeAllActions (ActionType actionKey) {
        executeAllActions(actionKey, new ElementPair(this, this));
    }

    /**
     * Get the value of a timer with a given name
     *
     * @param timerName
     *            the name of the timer
     * @return the timer's value or 0 if the timer has not been initialized
     */
    public long getTimer (String timerName) {
        long timerValue = 0;
        if (myTimers.containsKey(timerName)) {
            timerValue = myTimers.get(timerName).longValue();
        }
        return timerValue;
    }

    /**
     * Set the value of a timer with a given name to a given number of updates
     *
     * @param timerName
     *            the name of the timer
     * @param timerValue
     *            its value
     */
    public void setTimer (String timerName, long timerValue) {
        setNumericalAttribute(timerName, 0);
        myTimers.put(timerName, timerValue);
    }

    /**
     * Update all the timers. If a timer's value is 0, remove the timer from the
     * map in order to save space and processing time.
     */
    public void updateTimers () {
        for (Iterator<Map.Entry<String, Long>> it = myTimers.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Long> entry = it.next();
            Long timerValue = entry.getValue();
            timerValue--;
            if (timerValue > 0) {
                entry.setValue(timerValue);
            } else {
                // Set a flag indicating that the timer has completed
                setNumericalAttribute(entry.getKey(), 1);
                it.remove();
            }
        }
    }

    public Map<String, Long> getTimersCopy () {
        return new HashMap<>(myTimers);
    }

    public void registerAsChild (Consumer<GameElementState> function) {
        function.accept(myState);
    }

    public boolean isType (String typeString) {
        return myState.isType(typeString);
    }

    public List<String> getTypes () {
        return myState.getTypes();
    }

    public void setPosition (double x, double y) {
        setNumericalAttribute(StateTags.X_GOAL_POSITION.getValue(), x);
        setNumericalAttribute(StateTags.Y_GOAL_POSITION.getValue(), y);
        setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION.getValue(), x);
        setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION.getValue(), y);
        setNumericalAttribute(StateTags.X_POSITION.getValue(), x);
        setNumericalAttribute(StateTags.Y_POSITION.getValue(), y);
    }
}
