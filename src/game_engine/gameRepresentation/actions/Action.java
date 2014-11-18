package game_engine.gameRepresentation.actions;

import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.List;


/**
 * The Action class is the latter part of the Condition-Action pair, by use of which each
 * GameElement will define how it interacts with the world around it.
 * 
 * @author Steve
 *
 */

public abstract class Action {

    public abstract void doAction (List<GameElementState> elementList);

}
