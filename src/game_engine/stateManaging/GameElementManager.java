package game_engine.stateManaging;

import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import java.util.List;
import java.util.stream.Collectors;


public class GameElementManager {

    private Level myLevel;

    public GameElementManager (Level level) {
        myLevel = level;
    }

    public List<GameElementState> findAllElementsOfType (String typeName) {
        return myLevel.getUnits().stream()
                .filter(o -> o.getType().equals(typeName))
                .map(o -> o.getState())
                .collect(Collectors.toList());
    }

    public void addElementToLevel (String typeName) {
        // TODO: add factories
    }

}
