package game_engine.computers.boundsComputer;

import javafx.geometry.Bounds;
import javafx.scene.shape.Polygon;


/**
 * An indicator that an object is bounded that requires the implementation of a method that exposes
 * the bounding box of the object so it can be used in calculations by other bounded objects.
 *
 * @author Zachary Bears
 *
 */
public interface Bounded {
    /**
     * @return the bounds of the bounded object
     */
    public Bounds getBounds ();

}
