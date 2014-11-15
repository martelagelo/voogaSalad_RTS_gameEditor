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
    private List<DrawableGameElementState> terrain;
    private List<SelectableGameElementState> units;
    private List<GameElementState> goal;

    public boolean isActive () {
        return isActiveLevel;
    }

    public List<DrawableGameElementState> getTerrain () {
        return terrain;
    }

    public List<SelectableGameElementState> getUnits () {
        return units;
    }

    public List<GameElementState> getGoal () {
        return goal;
    }

    public void setActive () {
        isActiveLevel = true;
    }

}
