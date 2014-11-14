package game_engine.gameRepresentation;

import game_engine.gameRepresentation.gameElement.DrawableGameElement;
import game_engine.gameRepresentation.gameElement.GameElement;
import game_engine.gameRepresentation.gameElement.SelectableGameElement;
import java.util.List;
import java.util.stream.Collectors;


public class Level {
    
    public String name;
    public String description;
    private boolean isActiveLevel = false;
    private List<DrawableGameElement> terrain;
    private List<SelectableGameElement> units;
    private List<GameElement> goal;
    
    public boolean isActive(){
        return isActiveLevel;
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
