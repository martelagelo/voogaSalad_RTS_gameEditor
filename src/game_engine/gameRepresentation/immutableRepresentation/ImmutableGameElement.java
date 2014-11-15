package game_engine.gameRepresentation.immutableRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.ObscureAction;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.Map;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


/**
 * This wrapper allows for the Engine to pass an Object to the GUI without exposing internal state
 * we want hidden.
 * 
 * @author Steve
 *
 */
public class ImmutableGameElement implements Observable {

    private SelectableGameElementState currentlySelectedElement;

    public ImmutableGameElement (SelectableGameElementState selectedElement) {
        currentlySelectedElement = selectedElement;
    }

    public String getTextualAttribute (String attributeName) {
        return currentlySelectedElement.getTextualAttribute(attributeName);
    }

    public Number getNumericalAttribute (String attributeName) {
        return currentlySelectedElement.getNumericalAttribute(attributeName);
    }

    public Map<String, ObscureAction> getButtonInformation () {
        return currentlySelectedElement.getCurrentInteractionInformation();
    }

    @Override
    public void addListener (InvalidationListener arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeListener (InvalidationListener arg0) {
        // TODO Auto-generated method stub

    }
}
