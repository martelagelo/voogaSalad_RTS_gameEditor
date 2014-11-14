package game_engine.gameRepresentation.observableRepresentation;

import game_engine.gameRepresentation.gameElement.ObscureAction;
import game_engine.gameRepresentation.gameElement.SelectableGameElement;
import java.util.Map;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


public class ObservableGameElement implements Observable {

    private SelectableGameElement currentlySelectedElement;

    public ObservableGameElement (SelectableGameElement selectedElement) {
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
