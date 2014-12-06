package model.state.gameelement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.JSONable;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;


/**
 * The most basic flavor of GameElement - this type of element has no on-screen
 * representation. Examples include triggers and goals. States are essentially
 * data-wrapping objects and as such have no internal logic.
 * 
 * @author Steve, Jonathan, Rahul, Nishad, Zach
 *
 */

public  class  GameElementState  implements  JSONable , Serializable {
    /**
     * 
     */
    private  static  final  long serialVersionUID =  6832117170246376287L ;

    /**
     * The actions for a given game element state will be stored in a map of
     * Strings that represent the type of action e.g. collision, internal, etc.
     * to a list of Action wrappers of vscript commands of the actions to be executed. Conditions
     * were removed from this representation as actions internally check for
     * their own conditions.
     */
    protected Map<String, List<ActionWrapper>> myActions;
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
    public  GameElementState () {
        attributes = new AttributeContainer();
        myActions = new HashMap<String, List<ActionWrapper>>();
    }

    /**
     * @return the name of the element, if it has been set
     */
    public  String  getName () {
        return attributes.getTextualAttribute(StateTags.NAME);
    }

    /**
     *
     * @return the type of the element, if it has been set
     */
    public String getType () {
        return attributes.getTextualAttribute(StateTags.TYPE);
    }

    /**
     * Add a string condition-action pair to the game element state
     * 
     * param actionType
     *        the type of the action being added
     * param actionString
     *        a Vscript string representation of the action
     */
    public  void  addAction ( ActionWrapper  action ) {
        if (!myActions.containsKey(action.getActionType())) {
            myActions.put(action.getActionType(), new ArrayList<>());
        }
        myActions.get(action.getActionType()).add(action);
    }

    /**
     * Grabs all actions for use in evaluatable factory. Exposing the collection is in this case
     * necessary for use by the factory as the element states are essentially data wrappers that are
     * only used in the creation of objects by the factory
     * 
     * @return
     */
    public Map<String, List<ActionWrapper>> getActions () {
        return myActions;
    }

}