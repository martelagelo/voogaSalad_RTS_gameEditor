package game_engine.gameRepresentation.stateRepresentation.gameElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.JSONable;

/**
 * The most basic flavor of GameElement - this type of element has no on-screen
 * representation. Examples include triggers and goals. States are essentially
 * data-wrapping objects and as such have no internal logic.
 * 
 * @author Steve, Jonathan, Rahul, Nishad, Zach
 *
 */

public class GameElementState implements JSONable {
    /**
     * The actions for a given game element state will be stored in a map of
     * Strings that represent the type of action e.g. collision, internal, etc.
     * to a list of strings vscript of the actions to be executed. Conditions
     * were removed from this representation as actions can easily check for
     * their own conditions using an && sign. i.e. condition&&action will only
     * evaluate the action if the condition is true.
     */
    protected Map<String, List<String>> myActions;
    /**
     * It was decided that all attributes for a game element would be saved in
     * sets of attributes instead of in local instance variables. Although this
     * may make it difficult at times to be sure of what attributes an object
     * actually has, this choice was made to allow for the greatest amount of
     * power for the VScript language as people creating the games will be able
     * to use any numerical or string attribute of an object in their logic,
     * allowing for much more power on the side of engine extensibility at the
     * cost of some "parameter uncertainty" in the engine.
     */
    public AttributeContainer attributes;

    /**
     * Initialize the game element state and its internal data structures.
     */
    public GameElementState () {
        attributes = new AttributeContainer();
        myActions = new HashMap<String, List<String>>();
    }

    /**
     * @return the name of the element, if it has been set
     */
    public String getName () {
        return attributes.getTextualAttribute(StateTags.NAME_ATTRIBUTE_STRING);
    }

    /**
     *
     * @return the type of the element, if it has been set
     */
    public String getType () {
        return attributes.getTextualAttribute(StateTags.TYPE_ATTRIBUTE_STRING);
    }
    
    /**
     * Add a string condition-action pair to the game element state
     * 
     * @param actionType
     *            the type of the action being added
     * @param actionString
     *            a Vscript string representation of the action
     */
    public void addAction (String actionType, String actionString) {
        if (!myActions.containsKey(actionType)) {
            myActions.put(actionType, new ArrayList<>());
        }
        myActions.get(actionType).add(actionString);
    }

    /**
     * Grabs all actions for use in evaluatable factory.
     * 
     * @return
     */
    public Map<String, List<String>> getActions () {
        return myActions;
    }
}
