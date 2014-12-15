package engine.visuals;

import java.util.List;
import javafx.scene.Node;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;

public interface VisualDisplay {

    public abstract Node getNode();
    
    public abstract void update(List<SelectableGameElement> unitList);
    
}
