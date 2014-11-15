package game_engine.stateManaging;

import game_engine.gameRepresentation.gameElement.GameElement;
import game_engine.gameRepresentation.renderedRepresentation.RenderedLevel;
import java.util.List;
import java.util.stream.Collectors;


public class GameElementManager {

    private RenderedLevel myLevel;

    public GameElementManager (RenderedLevel level) {
        myLevel = level;
    }

    public List<GameElement> findAllElementsOfType (String typeName) {
        return myLevel.getUnits().stream()
                .filter(o -> o.getType().equals(typeName))
                .map(o -> o.getState())
                .collect(Collectors.toList());
    }

    public void addElementToLevel (String typeName) {
        // TODO: add factories
    }

}
