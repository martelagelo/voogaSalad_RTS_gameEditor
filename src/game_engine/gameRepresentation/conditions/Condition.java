package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.GameElement;
import java.util.List;


public abstract class Condition {

    public abstract Boolean evaluate (List<GameElement> parameters);

}
