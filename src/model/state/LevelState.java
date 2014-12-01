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

    public List<DrawableGameElementState> getTerrain () {
        return myTerrains;
    }

    public void addTerrain (DrawableGameElementState terrain) {
        myTerrains.add(terrain);
    }

    public List<SelectableGameElementState> getUnits () {
        return myUnits;
    }

    public void addUnit (SelectableGameElementState unit) {
        myUnits.add(unit);
    }

    public List<GameElementState> getGoals () {
        return myGoals;
    }

    public void addGoal (GameElementState goal) {
        myGoals.add(goal);
    }

}
