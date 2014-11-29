package game_engine.gameRepresentation.stateRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.Attribute;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * The Level class is the direct point of interaction for most gameplay and editing - this class
 * holds all of the GameElements being used for the current editing and/or game running session.
 * Essentially a data wrapper class for all objects and states contained in a level.
 *
 * @author Steve, Jonathan, Nishad, Rahul
 *
 */
public class LevelState extends DescribableState {

    private Set<Attribute<Number>> myNumericalAttributes;
    private Set<Attribute<String>> myTextualAttributes;
    private List<DrawableGameElementState> myTerrains;
    private List<SelectableGameElementState> myUnits;
    private List<GameElementState> myGoals;

    public LevelState (String name) {
        super(name);
        myTerrains = new ArrayList<>();
        myUnits = new ArrayList<>();
        myGoals = new ArrayList<>();
    }

    public List<DrawableGameElementState> getTerrain () {
        return myTerrains;
    }

    public void addTerrain (DrawableGameElementState terrain) {
        myTerrains.add(terrain);
    }

    public List<SelectableGameElementState> getUnits () {
        return myUnits;
    }

    public void addUnit (SelectableGameElementState unit) {
        myUnits.add(unit);
    }

    public List<GameElementState> getGoals () {
        return myGoals;
    }

    public void addGoal (GameElementState goal) {
        myGoals.add(goal);
    }
    
    // TOOD: this is copied from GameElementState - make both implement an interface??
    
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

}
