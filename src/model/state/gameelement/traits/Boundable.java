package model.state.gameelement.traits;


/**
 * An indicator that an object is bounded that requires the implementation of a
 * method that exposes the bounding box of the object so it can be used in
 * calculations by other bounded objects.
 *
 * @author Zach
 *
 */
public interface Boundable {
    /**
     * @return the bounds of the bounded object
     */
    public double[] getBounds ();
    public double[] findGlobalBounds ();
}

