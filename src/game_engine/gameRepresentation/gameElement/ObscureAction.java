package game_engine.gameRepresentation.gameElement;

/**
 * This functional interface is used to represent the actions of buttons specific to
 * SelectableGameElements when they are selected. This type of function does not take in any
 * arguments and does not return any either. The action of this function is to update the internal
 * state of the GameElement.
 * 
 * @author Steve
 *
 */
@FunctionalInterface
public interface ObscureAction {

    public void performAction ();
}
