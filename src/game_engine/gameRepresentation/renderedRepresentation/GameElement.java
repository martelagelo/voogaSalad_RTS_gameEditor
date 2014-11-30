package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.evaluatables.NullElementPair;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.StateTags;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * A basic game element. Contains a state and a map strings to conditions and actions. Also contains
 * an
 * update method to update the element due to its internal state.
 *
 * @author Jonathan, Nishad, Rahul, Steve, Zach
 *
 */
public class GameElement {
    /**
     * The action lists map is a map of action strings to lists of possible actions. For example,
     * The String "Collision" might map to a list of actions that should be run whenever an element
     * is dealing with a collision with another element whereas "Action1" might map to a list of
     * actions that should be executed when an element's action button is clicked.
     */
    private Map<String, List<Evaluatable<?>>> myActionLists;
    private GameElementState myState;
    protected ResourceBundle actionTypes;

    /**
     * Create a game element with the given state
     *
     * @param gameElementState the state of the game element
     */
    public GameElement (GameElementState gameElementState,
                        Map<String, List<Evaluatable<?>>> conditionActionPairs,
                        ResourceBundle actionTypesBundle) {
        myState = gameElementState;
        myActionLists = conditionActionPairs;
        actionTypes = actionTypesBundle;
        createActionLists();
    }

    /**
     * Ensure that lists are instantiated for all the possible action types of an element to protect
     * against uninstantiated action entities at runtime.
     */
    private void createActionLists () {
        if (myActionLists == null) {
            myActionLists = new HashMap<>();
        }
        for (String key : actionTypes.keySet()) {
            String type = actionTypes.getString(key);
            if (!myActionLists.containsKey(type)) {
                myActionLists.put(type, new CopyOnWriteArrayList<>());
            }
        }

    }

    /**
     * Get an iterator of the actions with a given tag
     * 
     * @param actionType the tag for the type of action
     * @return an iterator containing all the actions of the desired type
     */
    public Iterator<Evaluatable<?>> getActionsOfType (String actionType) {
        List<Evaluatable<?>> actionsOfInterest;
        if (!myActionLists.containsKey(actionType)) {
            actionsOfInterest = new ArrayList<Evaluatable<?>>();
        }
        else {
            actionsOfInterest = myActionLists.get(actionType);
        }
        return actionsOfInterest.iterator();

    }

    /**
     * Add an action of the given type to the array of actions
     * 
     * @param actionType the type of the action to be added
     * @param action the action to be added
     */
    public void addAction (String actionType, Evaluatable<?> action) {
        if (!myActionLists.containsKey(actionType)) {
            myActionLists.put(actionType, new CopyOnWriteArrayList<>());
        }
        myActionLists.get(actionType).add(action);
    }

    /**
     * Remove the given action the array of actions
     * 
     * @param actionID the identifier string for the action tree @see Evaluatable
     */
    public void removeAction (String actionID) {
        for (String actionType : myActionLists.keySet()) {
            getActionsOfType(actionType).forEachRemaining(action -> {
                if (action.getID().equals(actionID)) {
                    System.out.println("Action should be removed");
                    myActionLists.get(actionType).remove(action);
                }
            });
        }
    }
    
    /**
     * Get a numeric attribute contained by the game element
     *
     * @param attributTag the tag of the attribute of interest
     * @return the atribute's value or 0 if the attribute was not declared
     */
    public Number getNumericalAttribute (String attributeTag) {
        return myState.attributes.getNumericalAttribute(attributeTag);
    }

    /**
     * Set a numeric attribute contained by the game element
     *
     * @param attributeTag the tag of the attribute
     * @param attributeValue the value of the attribute
     */
    public void setNumericalAttribute (String attributeTag, Number attributeValue) {
        myState.attributes.setNumericalAttribute(attributeTag, attributeValue);
    }

    /**
     * Get a textual attribute contained by the game element
     *
     * @param attributeTag the tag of the attribute
     * @return the value of the attribute or "" if it does not exist
     */
    public String getTextualAttribute (String attributeTag) {
        return myState.attributes.getTextualAttribute(attributeTag);
    }

    /**
     * Set the value of a textual attribute contained by the game element
     *
     * @param attributeTag the tag of the attribute
     * @param attributeValue the attribute's value
     */
    public void setTextualAttribute (String attributeTag, String attributeValue) {
        myState.attributes.setTextualAttribute(attributeTag, attributeValue);
    }


    /**
     * Update the element based on its internal state
     */
    public void update () {
        updateSelfDueToInternalFactors();
    }

    /**
     * Update the game element due to actions that execute on its internal factors e.g. move the
     * element based on its internal velocity parameters.
     */
    private void updateSelfDueToInternalFactors () {
        executeAllActions(actionTypes.getString("internal"));
    }

    /**
     * Execute all the actions in a given key's list. Given an element pair
     *
     * @param actionKey the key of the action set for which to execute all of the actions
     * @param ElementPair an element pair of the current object and any objects it might be
     *        interested in e.g. the current unit and a unit nearby it
     */
    protected void executeAllActions (String actionKey, ElementPair elementPair) {
        getActionsOfType(actionKey).forEachRemaining(action -> action.evaluate(elementPair));
    }

    /**
     * Execute all the actions in a given key's list given that there are no applicable elements for
     * the action set.
     *
     * @param actionKey the key of the action set for which to execute all of the actions
     */
    protected void executeAllActions (String actionKey) {
        executeAllActions(actionKey, new NullElementPair());
    }
    
    public void setPosition (double x, double y) {
        myState.attributes.setNumericalAttribute(StateTags.X_POSITION, x);
        myState.attributes.setNumericalAttribute(StateTags.Y_POSITION, y);
    }
}
