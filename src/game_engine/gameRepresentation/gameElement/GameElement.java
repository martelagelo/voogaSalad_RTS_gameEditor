package game_engine.gameRepresentation.gameElement;

import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Condition;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class GameElement {

    protected Map<Condition, Action> ifThisThenThat;
    protected List<Attribute<Number>> numericalAttributes;
    protected List<Attribute<String>> textualAttributes;

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
}
