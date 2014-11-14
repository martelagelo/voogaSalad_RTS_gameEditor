package game_engine.gameRepresentation.gameElement;

import java.util.Map;
import java.util.function.Consumer;

public class ObservableGameElement {
    
    private SelectableGameElement currentlySelectedElement;
    
    public ObservableGameElement(SelectableGameElement selectedElement){
        currentlySelectedElement = selectedElement;
    }
    
    public void selectNewElement(SelectableGameElement elementToSelect){
        currentlySelectedElement = elementToSelect;
        currentlySelectedElement.updateSelfDueToSelection();
    }
    
    public String getTextualAttribute (String attributeName ) {
        return currentlySelectedElement.getTextualAttribute(attributeName);
    }
    
    public Number getNumericalAttribute (String attributeName ) {
        return currentlySelectedElement.getNumericalAttribute(attributeName);
    }
    
    public Map<String,PureConsumer> getButtonInformation(){
        return currentlySelectedElement.getCurrentButtonInformation();
    }
}
