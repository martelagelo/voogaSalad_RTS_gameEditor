package game_engine.gameRepresentation;

import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Condition;
import java.util.Map;

public class GameElement {

	protected Map<Condition, Action> ifThisThenThat;
	protected Map<String, Number> numericalAttributes;
	protected Map<String, String> textualAttributes;

	public String getName() {
		return textualAttributes.get("Name");
	}

	public String getType() {
		return textualAttributes.get("Type");
	}
}
