package game_engine.gameRepresentation.stateRepresentation.gameElement;

import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Evaluatable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The most basic flavor of GameElement - this type of element has no on-screen representation.
 * Examples include triggers and goals.
 *
 * @author Steve
 *
 */
public class GameElementState {

    protected Map<Evaluatable, Action> ifThisThenThat = new HashMap<>();
    protected List<Attribute<Number>> numericalAttributes = new ArrayList<>();
    protected List<Attribute<String>> textualAttributes = new ArrayList<>();

    public String getName () {
        return getTextualAttribute("Name");
    }

    public String getType () {
        return getTextualAttribute("Type");
    }

    public String getTextualAttribute (String name) {
        return textualAttributes.stream()
                .filter(o -> o.getName().equals(name))
                .collect(Collectors.toList()).get(0).getData();
    }

    public Number getNumericalAttribute (String name) {
        return numericalAttributes.stream()
                .filter(o -> o.getName().equals(name))
                .collect(Collectors.toList()).get(0).getData();
    }

    public void setTextualAttribute (String name, String value) {
       //TODO implement
    }

    public void setNumericalAttribute (String name, Number value) {
      //TODO implement
    }

    public void update () {
        updateSelfDueToInternalFactors();
    }

    private void updateSelfDueToInternalFactors () {
        // TODO Auto-generated method stub

    }
}
