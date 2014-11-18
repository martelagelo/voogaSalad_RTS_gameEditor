package game_engine.computers.boundsComputers;




/**
 * An interface required for object that have sight.
 *
 * @author Zachary Bears
 *
 */
public interface Sighted {
    /**
     * Gets the vision polygon of the sighted object
     */
    public double[] getVisionBounds ();

}
