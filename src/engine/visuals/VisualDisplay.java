// This entire file is part of my masterpiece.
// JOHN LORENZ

package engine.visuals;

import java.util.List;
import javafx.scene.Node;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;


/**
 * The interface for components that want to display on the Pane for the VisualManager. Must contain
 * at least methods to get the Node, and to update based on the list of SelectableGameElements
 * contained in the current Level
 * 
 * @author John Lorenz
 *
 */
public interface VisualDisplay {

    /**
     * Returns the node for this object's visual representation
     * 
     * @return a javaFX node
     */
    public abstract Node getNode ();

    /**
     * Updates internally to reflect the states of the current selectable game elements in the
     * level.
     * 
     * @param unitList
     */
    public abstract void update (List<SelectableGameElement> unitList);

}
