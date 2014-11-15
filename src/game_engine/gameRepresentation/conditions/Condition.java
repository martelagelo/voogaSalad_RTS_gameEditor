package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.List;


/**
 * The Condition class is the former part of the Condition-Action pair, by use of which each
 * GameElement will define how it interacts with the world around it.
 * 
 * @author Steve
 *
 */

public abstract class Condition {

    protected String conditionType;

    public String getType () {
        return conditionType;
    }

    public abstract Boolean evaluate (List<GameElementState> parameters);

}
