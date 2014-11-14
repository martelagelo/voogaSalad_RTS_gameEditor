package game_engine.stateManaging;

import game_engine.gameRepresentation.GameElement;
import game_engine.gameRepresentation.Level;
import java.util.List;
import java.util.stream.Collectors;


public class GameElementManager {

    private Level myLevel;

    public GameElementManager (Level level) {
        myLevel = level;
    }

    public List<GameElement> findAllElementsOfType (String typeName) {
        return myLevel.getUnits().stream()
                .filter(o -> o.getType().equals(typeName))
                .collect(Collectors.toList());
    }

}
