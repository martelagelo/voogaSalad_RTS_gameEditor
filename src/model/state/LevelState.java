package model.state;
//THIS ENTIRE CLASS IS PART OF MY MASTERPIECE
import java.io.Serializable;
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
public class LevelState extends DescribableState implements Serializable {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = 846038357373984158L;
    public AttributeContainer myAttributes;
    private List<DrawableGameElementState> myTerrains;
    private List<SelectableGameElementState> myUnits;
    private List<GameElementState> myGoals;
    private LevelIdentifier myIdentifier;

    public LevelState() {
    }
    
    public LevelState (String name, String campaignName) {
        super(name);
        myAttributes = new AttributeContainer();
        myTerrains = new ArrayList<>();
        myUnits = new ArrayList<>();
        myGoals = new ArrayList<>();
        myIdentifier = new LevelIdentifier(name, campaignName);
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
    
    public void removeElement(GameElementState state) {
        if (myUnits.contains(state)) myUnits.remove(state);
        if (myTerrains.contains(state)) myTerrains.remove(state);
        if (myGoals.contains(state)) myGoals.remove(state);
    }

    /**
     * 
     * @param goal
     *        the goal to be added to the level's list of goals
     */
    public void addGoal (GameElementState goal) {
        myGoals.add(goal);
    }
    
    @Override
    public boolean equals(Object o){
		if (!(o instanceof LevelState)) {
			return false;
		} else {
			LevelState ls = (LevelState) o;
			return this.myIdentifier.equals(ls.myIdentifier);
		}
    }
    
    public LevelIdentifier getIdentifier(){
    	return myIdentifier;
    }

}
