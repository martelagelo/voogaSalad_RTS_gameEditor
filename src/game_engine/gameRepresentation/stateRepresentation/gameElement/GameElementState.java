package game_engine.gameRepresentation.stateRepresentation.gameElement;

import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Condition;
//import game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes.ConditionOnImmediateElements;
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

    protected Map<Condition, Action> ifThisThenThat = new HashMap<>();
    protected List<Attribute<Number>> numericalAttributes = new ArrayList<>();
    protected List<Attribute<String>> textualAttributes = new ArrayList<>();

    public void addConditionActionPair (Condition condition, Action action) {
    	ifThisThenThat.put(condition, action);
    }
    
    public void updateConditionActionPair (Condition condition, Action action) {
    	ifThisThenThat.remove(condition);
    	ifThisThenThat.put(condition, action);
    }
    
    public void removeConditionActionPair (Condition condition) {
    	ifThisThenThat.remove(condition);
    }
    
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
    
    public void addTextualAttribute (String name, String value) {
    	Attribute textTrait = new Attribute(name, value);
    	this.textualAttributes.add(textTrait);
    }
    
    public void addNumericalAttribute (String name, Number value) {
    	Attribute numTrait = new Attribute(name, value);
    	this.numericalAttributes.add(numTrait);
    }
    
    public void updateTextualAttribute (String name, String value) {
    	for(Attribute<String> attr: textualAttributes) {
    		if (attr.getName().equals(name)) {
    			attr.setData(value);
    		}
    	}
    }
    
    public void updateNumericalAttribute (String name, Number value) {
    	for(Attribute<Number> attr: numericalAttributes) {
    		if (attr.getName().equals(name)) {
    			attr.setData(value);
    		}
    	}
    }
    
    public void deleteTextualAttribute (String name) {
    	for(Attribute<String> attr: textualAttributes) {
    		if (attr.getName().equals(name)) {
    			textualAttributes.remove(attr);
    			break;
    		}
    	}
    }
    
    public void deleteNumericalAttribute (String name) {
    	for(Attribute<Number> attr: numericalAttributes) {
    		if (attr.getName().equals(name)) {
    			numericalAttributes.remove(attr);
    			break;
    		}
    	}
    }

    public void update () {
        updateSelfDueToInternalFactors();
    }

    private void updateSelfDueToInternalFactors () {
        // TODO Auto-generated method stub

    }
}
