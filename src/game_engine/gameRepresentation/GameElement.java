package game_engine.gameRepresentation;

import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Condition;
import java.util.HashMap;
import java.util.Map;


public class GameElement {

    protected Map<Condition, Action> ifThisThenThat = new HashMap<Condition, Action>();
    protected Map<String, Number> numericalAttributes = new HashMap<>();
    protected Map<String, String> textualAttributes = new HashMap<>();

    public String getName () {
        return textualAttributes.get("Name");
    }

    public String getType () {
        return textualAttributes.get("Type");
    }
}
