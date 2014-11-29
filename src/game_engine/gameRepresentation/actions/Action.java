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
// TODO add more functionality to this class. This is currently a basic skeleton
public abstract class Action {
    /**
     * Perform the action on a given list of elements
     * 
     * @param elementList the list of elements on which to perform the action
     */
    public abstract void doAction (List<GameElementState> elementList);

}
