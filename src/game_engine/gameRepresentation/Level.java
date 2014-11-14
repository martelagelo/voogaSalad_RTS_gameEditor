package game_engine.gameRepresentation;

import java.util.List;
import java.util.stream.Collectors;


public class Level implements Describable {
    private String name;
    private String description;
    private List<DrawableGameElement> terrain;
    private List<SelectableGameElement> units;
    private List<GameElement> goal;

    @Override
    public String getName () {
        return name;
    }

    @Override
    public String getDescription () {
        return description;
    }

    public List<DrawableGameElement> getTerrain () {
        return terrain;
    }

    public List<SelectableGameElement> getUnits () {
        return units;
    }

    public List<GameElement> getGoal () {
        return goal;
    }

}
