package game_engine.gameRepresentation.stateRepresentation.gameElement.traits;

/**
 * An interface required for object that have sight.
 *
 * @author Zachary Bears
 *
 */
public interface Sighted {
    /**
     * Gets the vision bounds of the sighted object
     */
    public double[] getVisionBounds ();

}
