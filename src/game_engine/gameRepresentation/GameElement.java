package game_engine.gameRepresentation;

import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Condition;
import java.util.Map;


public class GameElement {

    private Map<Condition, Action> ifThisThenThat;
    private Map<String, Number> numericalAttributes;
    private Map<String, String> textualAttributes;
}
