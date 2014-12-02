package model.state;

import java.util.ArrayList;
import java.util.List;
import model.state.gameelement.AttributeContainer;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;


/**
 * The Level class is the direct point of interaction for most gameplay and editing - this class
 * holds all of the GameElements being used for the current editing and/or game running session.
 * Essentially a data wrapper class for all objects and states contained in a level.
 *
 * @author Steve, Jonathan, Nishad, Rahul
 *
 */
public class LevelState extends DescribableState {

    public AttributeContainer attributes;
    private List<DrawableGameElementState> myTerrains;
    private List<SelectableGameElementState> myUnits;
    private List<GameElementState> myGoals;

    public LevelState (String name) {
        super(name);
        attributes = new AttributeContainer();
        myTerrains = new ArrayList<>();
        myUnits = new ArrayList<>();
        myGoals = new ArrayList<>();
    }

    /**
     * 
     * @return List<DrawableGameElementState>
     *         the list of the level's terrains
     */
    public List<DrawableGameElementState> getTerrain () {
        return myTerrains;
    }

    /**
     * 
     * @param terrain
     *        the terrain to be added to the level's terrains
     */
    public void addTerrain (DrawableGameElementState terrain) {
        myTerrains.add(terrain);
    }
    
    /**
     * 
     * @return myUnits
     *         the list of the level's units
     */
    public List<SelectableGameElementState> getUnits () {
        return myUnits;
    }

    /**
     * 
     * @param unit
     *        the unit to be added to the level's list of units
     */
    public void addUnit (SelectableGameElementState unit) {
        myUnits.add(unit);
    }

    /**
     * 
     * @return List<GameElementState>
     *         the list of the level's goals
     */
    public List<GameElementState> getGoals () {
        return myGoals;
    }

    /**
     * 
     * @param goal
     *        the goal to be added to the level's list of goals
     */
    public void addGoal (GameElementState goal) {
        myGoals.add(goal);
    }

}
