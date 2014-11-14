package vooga.engine.computers.boundsComputers;

import javafx.scene.shape.Polygon;


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
    public Polygon getVisionPolygon ();

}
