package vooga.engine.gameRepresentation.conditions;

import java.util.List;
import vooga.engine.gameRepresentation.GameElement;


public abstract class Condition {

    public abstract Boolean evaluate (List<GameElement> parameters);

}
