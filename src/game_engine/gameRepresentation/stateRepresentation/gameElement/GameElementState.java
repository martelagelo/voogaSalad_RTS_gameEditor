package game_engine.gameRepresentation.stateRepresentation.gameElement;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The most basic flavor of GameElement - this type of element has no on-screen
 * representation. Examples include triggers and goals.
 * 
 * @author Steve, Jonathan, Rahul, Nishad, Zach
 *
 */
public class GameElementState {

    private static final String NAME_ATTRIBUTE_STRING = "Name";
    private static final String TYPE_ATTRIBUTE_STRING = "Type";

    protected Map<String, String> myConditionActionPairings;
    protected Set<Attribute<Number>> myNumericalAttributes;
    protected Set<Attribute<String>> myTextualAttributes;

    public GameElementState () {
        myNumericalAttributes = new HashSet<>();
        myTextualAttributes = new HashSet<>();
        myConditionActionPairings = new HashMap<>();
    }

    /**
     * Get the name of the element, if it has been set
     * 
     * @return
     */
    public String getName () {
        return getTextualAttribute(NAME_ATTRIBUTE_STRING);
    }

    // TODO: handle if type attribute doesn't exist
    /**
     * Get the type of the element, if it has been set
     * 
     * @return
     */
    public String getType () {
        return getTextualAttribute(TYPE_ATTRIBUTE_STRING);
    }
    
    /**
     * Get an attribute from an internal collection
     * 
     * @param collection the collection to extract the element from
     * @param attributeName the name of the attribute to extract
     * @param defaultReturnObject the value that should be returned if no attribute of the specified
     *        type was found
     * @return the attribute's value or the default return object if the attribute was not found
     */
    private <T> T getAttribute (Collection<Attribute<T>> collection,
                                String attributeName,
                                T defaultReturnObject) {
        List<Attribute<T>> attributes =
                collection.stream().filter(o -> o.getName().equals(attributeName))
                        .collect(Collectors.toList());
        return (attributes.size() != 0) ? attributes.get(0).getData() : defaultReturnObject;

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
        return getAttribute(myNumericalAttributes, name, new Double(-1));
    }

    /**
     * Add an attribute with the given values to a given collection. Used to make switching between
     * internal collection implementations easy
     * 
     * @param collection
     * @param attributeName
     * @param attributeValue
     */
    private <T> void setAttribute (Collection<Attribute<T>> collection,
                                   String attributeName,
                                   T attributeValue) {
        Attribute<T> attribute = new Attribute<>(attributeName, attributeValue);
        collection.remove(attribute); // Remove any old attribute that might conflict with this new
                                      // one
        collection.add(attribute); // Add the new attribute to the set
    }

    /**
     * Set a textual attribute with the given name to the given value
     */
    public void setTextualAttribute (String name, String value) {
        setAttribute(myTextualAttributes, name, value);
    }

    /**
     * Set a numerical attribute with the given name to the given value
     */
    public void setNumericalAttribute (String name, Number value) {
        setAttribute(myNumericalAttributes, name, value);
    }

    public void addConditionActionPair (String condition, String action) {
        myConditionActionPairings.put(condition, action);
    }
    
    public void update () {
        // TODO comment this
        updateSelfDueToInternalFactors();
    }

    private void updateSelfDueToInternalFactors () {
        // TODO Auto-generated method stub

    }
}
