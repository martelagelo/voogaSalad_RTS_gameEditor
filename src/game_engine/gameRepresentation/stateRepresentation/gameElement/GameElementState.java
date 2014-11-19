package game_engine.gameRepresentation.stateRepresentation.gameElement;

import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Evaluatable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * The most basic flavor of GameElement - this type of element has no on-screen representation.
 * Examples include triggers and goals.
 *
 * @author Steve
 * @author Zach
 *
 */
public class GameElementState {

    protected Map<Evaluatable, Action> ifThisThenThat = new HashMap<>();
    protected Set<Attribute<Number>> numericalAttributes;
    protected Set<Attribute<String>> textualAttributes;

    public GameElementState () {
        numericalAttributes = new HashSet<>();
        textualAttributes = new HashSet<>();
    }

    public String getName () {
        return getTextualAttribute("Name");
    }

    public String getType () {
        return getTextualAttribute("Type");
    }

    public String getTextualAttribute (String name) {
        List<Attribute<String>> attributes = textualAttributes.stream()
                .filter(o -> o.getName().equals(name))
                .collect(Collectors.toList());
        return (attributes.size() != 0) ? attributes.get(0).getData() : "";
    }

    public Number getNumericalAttribute (String name) {
        List<Attribute<Number>> attributes = numericalAttributes.stream()
                .filter(o -> o.getName().equals(name))
                .collect(Collectors.toList());
        return (attributes.size() != 0) ? attributes.get(0).getData() : new Double(0);
    }

    public void setTextualAttribute (String name, String value) {
        Attribute<String> attribute = new Attribute<>(name, value);
        textualAttributes.remove(attribute); // Remove any old attribute that conflicts
        textualAttributes.add(attribute); // Add the new attribute to the set
    }

    public void setNumericalAttribute (String name, Number value) {
        Attribute<Number> attribute = new Attribute<>(name,value);
        numericalAttributes.remove(attribute); // Remove any old attribute that conflicts
        numericalAttributes.add(attribute); // Add the new attribute to the set
    }

    public void update () {
        updateSelfDueToInternalFactors();
    }

    private void updateSelfDueToInternalFactors () {
        // TODO Auto-generated method stub

    }
}
