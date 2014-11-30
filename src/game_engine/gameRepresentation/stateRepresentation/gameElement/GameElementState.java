package game_engine.gameRepresentation.stateRepresentation.gameElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import util.JSONable;


/**
 * The most basic flavor of GameElement - this type of element has no on-screen
 * representation. Examples include triggers and goals. States are essentially data-wrapping objects
 * and as such have no internal logic.
 * 
 * @author Steve, Jonathan, Rahul, Nishad, Zach
 *
 */

public class GameElementState implements JSONable {
    /**
     * The actions for a given game element state will be stored in a map of Strings that represent
     * the type of action e.g. collision, internal, etc. to a list of strings vscript of the actions
     * to be
     * executed. Conditions were removed from this representation as actions can easily check for
     * their own conditions using an && sign. i.e. condition&&action will only evaluate the action
     * if the condition is true.
     */
    protected Map<String, List<String>> myActions;
    /**
     * It was decided that all attributes for a game element would be saved in sets of attributes
     * instead of in local instance variables. Although this may make it difficult at times to be
     * sure of what attributes an object actually has, this choice was made to allow for the
     * greatest amount of power for the VScript language as people creating the games will be able
     * to use any numerical or string attribute of an object in their logic, allowing for much more
     * power on the side of engine extensibility at the cost of some "parameter uncertainty" in the
     * engine.
     */
    protected Set<Attribute<Number>> myNumericalAttributes;
    protected Set<Attribute<String>> myTextualAttributes;

    /**
     * Initialize the game element state and its internal data structures.
     */
    public GameElementState () {
        myNumericalAttributes = new HashSet<>();
        myTextualAttributes = new HashSet<>();
        myActions = new HashMap<>();
    }

    /**
     * @return the name of the element, if it has been set
     */
    public String getName () {
        return getTextualAttribute(StateTags.NAME_ATTRIBUTE_STRING);
    }

    /**
     *
     * @return the type of the element, if it has been set
     */
    public String getType () {
        return getTextualAttribute(StateTags.TYPE_ATTRIBUTE_STRING);
    }

    /**
     * Get an attribute from an internal collection
     * 
     * @param collection
     *        the collection to extract the element from
     * @param attributeName
     *        the name of the attribute to extract
     * @param defaultReturnObject
     *        the value that should be returned if no attribute of the
     *        specified type was found
     * @return the attribute's value or the default return object if the
     *         attribute was not found
     */
    private <T> T getAttribute (Collection<Attribute<T>> collection,
                                String attributeName, T defaultReturnObject) {
        List<Attribute<T>> attributes = collection.stream()
                .filter(o -> o.getName().equals(attributeName))
                .collect(Collectors.toList());
        return (attributes.size() != 0) ? attributes.get(0).getData()
                                       : defaultReturnObject;

    }

    /**
     * Get a textual attribute with the given name
     */
    public String getTextualAttribute (String name) {
        return getAttribute(myTextualAttributes, name, "");
    }

    /**
     * Get a numerical attribute with the given name
     */
    public Number getNumericalAttribute (String name) {
        return getAttribute(myNumericalAttributes, name, new Double(0));
    }
    
    /**
     * This method will only be called by the MainModel in order display the actions
     * associated with each GameElementState. This method is necessary in order to be
     * able to display the goals in a level or the actions for a GameElement.
     * 
     * @return
     */
    public Map<String, List<String>> getActions() {
        return myActions;
    }
    
    /**
     * This method will only be called by the MainModel in order display the possible 
     * attributes associated with each GameElementState Wizard.
     * 
     * @return
     */
    public Set<Attribute<Number>> getNumericalAttributes() {
        return myNumericalAttributes;
    }
    
    /**
     * This method will only be called by the MainModel in order display the possible 
     * attributes associated with each GameElementState Wizard.
     * 
     * @return
     */
    public Set<Attribute<String>> getTextualAttributes() {
        return myTextualAttributes;
    }

    /**
     * Add an attribute with the given values to a given collection. Used to
     * make switching between internal collection implementations easy
     * 
     * @param collection
     * @param attributeName
     * @param attributeValue
     */
    private <T> void addAttribute (Collection<Attribute<T>> collection,
                                   String attributeName, T attributeValue) {
        Attribute<T> attribute = new Attribute<>(attributeName, attributeValue);
        collection.remove(attribute); // Remove any old attribute that might
                                      // conflict with this new
                                      // one
        collection.add(attribute); // Add the new attribute to the set
    }

    /**
     * Add an attribute
     */

    /**
     * Set a textual attribute with the given name to the given value
     */
    public void setTextualAttribute (String name, String value) {
        addAttribute(myTextualAttributes, name, value);
    }

    /**
     * Set a numerical attribute with the given name to the given value
     */
    public void setNumericalAttribute (String name, Number value) {
        addAttribute(myNumericalAttributes, name, value);
    }

    /**
     * Add a string condition-action pair to the game element state
     * 
     * @param actionType the type of the action being added
     * @param actionString a Vscript string representation of the action
     */
    public void addAction (String actionType, String actionString) {
        if (!myActions.containsKey(actionType)) {
            myActions.put(actionType, new ArrayList<>());
        }
        myActions.get(actionType).add(actionString);
    }

}
