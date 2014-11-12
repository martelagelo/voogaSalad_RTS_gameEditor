package vooga.engine.gameRepresentation;

import java.util.Map;
import vooga.engine.gameRepresentation.actions.Action;
import vooga.engine.gameRepresentation.conditions.Condition;


public class GameElement {

    private Map<Condition, Action> ifThisThenThat;
    private Map<String, Number> numericalAttributes;
    private Map<String, String> textualAttributes;
}
