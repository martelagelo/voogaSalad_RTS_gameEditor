package game_engine.gameRepresentation.stateRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.ArrayList;
import java.util.List;


/**
 * The Level class is the direct point of interaction for most gameplay and editing - this class
 * holds all of the GameElements being used for the current editing and/or game running session.
 *
 * @author Steve, Jonathan, Nishad, Rahul
 *
 */
public class LevelState extends DescribableState {

    private List<DrawableGameElementState> myTerrains;
    private List<SelectableGameElementState> myUnits;
    private List<GameElementState> myGoals;
    private CampaignState myCampaignState;

    public LevelState (String name, CampaignState campaign) {
        super(name);
        myCampaignState = campaign;
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

    public CampaignState getCampaignState () {
        return myCampaignState;
    }

    public boolean sameLevel (LevelState otherLevel) {
        return getName().equals(otherLevel.getName()) &&
               myCampaignState.getName().equals(otherLevel.getCampaignState().getName());
    }

}
