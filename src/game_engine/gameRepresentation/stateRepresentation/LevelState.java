package game_engine.gameRepresentation.stateRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The Level class is the direct point of interaction for most gameplay and editing - this class
 * holds all of the GameElements being used for the current editing and/or game running session.
 * 
 * @author Steve
 *
 */
public class LevelState {

    public String name;
    public String description;
    private boolean isActiveLevel = false;
    private List<DrawableGameElementState> terrains;
    private List<SelectableGameElementState> units;
    private List<GameElementState> goals;

    public boolean isActive () {
        return isActiveLevel;
    }

    public List<DrawableGameElementState> getTerrain () {
        return terrains;
    }
    
    public void addTerrain(DrawableGameElementState terrain) {
        terrains.add(terrain);
    }

    public List<SelectableGameElementState> getUnits () {
        return units;
    }
    
    public void addUnit(SelectableGameElementState unit) {
        units.add(unit);
    }

    public List<GameElementState> getGoal () {
        return goals;
    }
    
    public void addGoal(GameElementState goal) {
        goals.add(goal);
    }

    public void setActive () {
        isActiveLevel = true;
    }

}
