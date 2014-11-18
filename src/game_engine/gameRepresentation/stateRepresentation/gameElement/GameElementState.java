package game_engine.gameRepresentation.stateRepresentation.gameElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The most basic flavor of GameElement - this type of element has no on-screen
 * representation. Examples include triggers and goals.
 * 
 * @author Steve, Jonathan, Rahul, Nishad
 *
 */
public class GameElementState {

    private static final String NAME_ATTRIBUTE_STRING = "Name";
    private static final String TYPE_ATTRIBUTE_STRING = "Type";

    protected Map<String, String> myConditionActionPairs;
    protected List<Attribute<Number>> numericalAttributes;
    protected List<Attribute<String>> textualAttributes;

    public GameElementState () {
        myConditionActionPairs = new HashMap<>();
        numericalAttributes = new ArrayList<>();
        textualAttributes = new ArrayList<>();
    }

    // TODO: handle if name attribute doesn't exist
    public String getName () {
        return getTextualAttribute(NAME_ATTRIBUTE_STRING);
    }

    // TODO: handle if type attribute doesn't exist
    public String getType () {
        return getTextualAttribute(TYPE_ATTRIBUTE_STRING);
    }

    // TODO: handle when attribute doesn't exist
    public String getTextualAttribute (String name) {
        return textualAttributes.stream().filter(o -> o.getName().equals(name))
                .collect(Collectors.toList()).get(0).getData();
    }

    // TODO: handle when attribute doesn't exist
    public Number getNumericalAttribute (String name) {
        return numericalAttributes.stream().filter(o -> o.getName().equals(name))
                .collect(Collectors.toList()).get(0).getData();
    }

}