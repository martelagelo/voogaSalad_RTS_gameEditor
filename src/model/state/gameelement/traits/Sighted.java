package model.state.gameelement.traits;

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

    /**
     * Sets the vision bounds of the sighted object
     * 
     * @param bounds a double array of a polygon outline
     */
    public void setVisionBounds (double[] bounds);
}
