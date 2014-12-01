package engine.visual;

import javafx.scene.Node;


/**
 * An object that can be displayed
 * 
 * @author Zach
 *
 */
public interface Displayable {
    /**
     * @return the object's display node
     */
    public Node getNode ();
}
