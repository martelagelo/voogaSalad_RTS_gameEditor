package game_engine.visuals.elementVisuals.widgets;

import java.util.function.Consumer;
import javafx.scene.Node;

public interface Widget {
    
    public void registerAsComponent(Consumer<Node> registeringFunction);
}
