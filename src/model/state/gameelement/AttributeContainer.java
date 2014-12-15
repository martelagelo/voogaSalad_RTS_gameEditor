package model.state.gameelement;
// THIS ENTIRE CLASS IS PART OF MY MASTERPIECE

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * It was decided that all attributes for a game element would be saved in
 * sets of attributes instead of in local instance variables. Although this
 * may make it difficult at times to be sure of what attributes an object
 * actually has, this choice was made to allow for the greatest amount of
 * power for the VScript language as people creating the games will be able
 * to use any numerical or string attribute of an object in their logic,
 * allowing for much more power on the side of engine extensibility at the
 * cost of some "parameter uncertainty" in the engine.
 * 
 * Creating an attribute container allows us to give attributes using composition to many objects.
 * 
 * @author Steve
 *
 */
public class AttributeContainer implements Serializable {

    private static final long serialVersionUID = -3942254987685526285L;

    private Set<Attribute<Number>> myNumericalAttributes;
    private Set<Attribute<String>> myTextualAttributes;

    public AttributeContainer () {
        myNumericalAttributes = new HashSet<>();
        myTextualAttributes = new HashSet<>();
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
        return getAttribute(myNumericalAttributes, name, new Double(0));
    }

    public Set<Attribute<Number>> getNumericalAttributes () {
        return myNumericalAttributes;
    }

    public Set<Attribute<String>> getTextualAttributes () {
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
                                   String attributeName,
                                   T attributeValue) {
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
}
