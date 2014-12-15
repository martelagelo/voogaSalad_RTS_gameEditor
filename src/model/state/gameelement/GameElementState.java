package model.state.gameelement;
//THIS ENTIRE CLASS IS PART OF MY MASTERPIECE
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.JSONable;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;


/**
 * The most basic flavor of GameElement - this type of element has no on-screen
 * representation. Examples include triggers and goals. States are essentially
 * data-wrapping objects and as such have no internal logic.
 * 
 * @author Steve, Jonathan, Rahul, Nishad, Zach, Michael D., Stanley
 *
 */

public class GameElementState implements JSONable, Serializable {
    /**
     * 
     */

    private static final long serialVersionUID = 6832117170246376287L;

    /**
     * The actions for a given game element state will be stored in a map of
     * ActionTypes that represent the type of action e.g. collision, internal, etc.
     * to a list of Action wrappers of vscript commands of the actions to be executed. Conditions
     * were removed from this representation as actions internally check for
     * their own conditions.
     */
    protected Map<ActionType, List<ActionWrapper>> myActions;

    public AttributeContainer myAttributes;

    public Set<String> myTypes;

    /**
     * Initialize the game element state and its internal data structures.
     */
    public GameElementState () {
        myAttributes = new AttributeContainer();
        myActions = new HashMap<ActionType, List<ActionWrapper>>();
        myTypes = new HashSet<String>();
    }

    /**
     * @return the name of the element, if it has been set
     */
    public String getName () {
        return myAttributes.getTextualAttribute(StateTags.NAME.getValue());
    }

    /**
     * Adds a type to a set of types
     * 
     * @param type The new type
     */
    public void addType (String type) {
        myTypes.add(type);
    }

    /**
     * @return A list of the types
     */
    public List<String> getTypes () {
        return new ArrayList<String>(myTypes);
    }

    /**
     * 
     * @param typeString The type we want to check
     * @return True if the type set contains typeString
     */
    public boolean isType (String typeString) {
        return myTypes.contains(typeString);
    }

    public void deleteTypes (String typeString) {
        myTypes.remove(typeString);
    }

    /**
     * Add a string condition-action pair to the game element state
     * 
     * param actionType
     * the type of the action being added
     * param actionString
     * a Vscript string representation of the action
     */
    public void addAction (ActionWrapper action) {
        if (!myActions.containsKey(action.getActionType())) {
            myActions.put(action.getActionType(), new ArrayList<>());
        }
        myActions.get(action.getActionType()).add(action);
    }

    /**
     * Grabs all actions for use in evaluatable factory. Exposing the collection
     * is in this case necessary for use by the factory as the element states
     * are essentially data wrappers that are only used in the creation of
     * objects by the factory
     * 
     * @return
     */
    public Map<ActionType, List<ActionWrapper>> getActions () {
        return myActions;
    }

}
