package gamemodel;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 
 * @author Jonathan Tseng
 *
 */
public class SavableGameElementState {

    private static final String NAME_ATTRIBUTE_STRING = "Name";
    private static final String TYPE_ATTRIBUTE_STRING = "Type";

    private Map<String, String> myConditionActionPairs;
    // NEED IMPORT FROM ENGINE BRANCH
    private List<Attribute<String>> myTextualAttributes;
    private List<Attribute<Number>> myNumericalAttributes;

    public String getName () {
        new ArrayList<>().stream().anyMatch((attribute)->attribute.getName().equals(name));
        return getTextualAttribute(NAME_ATTRIBUTE_STRING);
    }

    public String getType () {
        return getTextualAttribute(TYPE_ATTRIBUTE_STRING);
    }

    public String getTextualAttribute (String name) {
        return (myTextualAttributes.stream().anyMatch( (attribute) -> attribute.getName()
                .equals(name))) ?
                               myTextualAttributes.stream()
                                       .filter(o -> o.getName().equals(name))
                                       .collect(Collectors.toList()).get(0).getData() : "";
    }

    public Number getNumericalAttribute (String name) {
        return (myNumericalAttributes.stream().anyMatch( (attribute) -> attribute.getName()
                .equals(name))) ?
                               myNumericalAttributes.stream()
                                       .filter(o -> o.getName().equals(name))
                                       .collect(Collectors.toList()).get(0).getData() : Double.NaN;
    }

    public void update () {
        updateSelfDueToInternalFactors();
    }

    private void updateSelfDueToInternalFactors () {
        // TODO Finish this stuff--ask engine peeps

    }

}
