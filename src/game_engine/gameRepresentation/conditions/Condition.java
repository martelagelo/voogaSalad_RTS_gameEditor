package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.GameElement;
import java.util.List;


public abstract class Condition {

    protected String conditionType;

    public String getType () {
        return conditionType;
    }

    public abstract Boolean evaluate (List<GameElement> parameters);

}
