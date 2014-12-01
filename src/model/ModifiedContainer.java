package model;

import java.util.ArrayList;
import java.util.List;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;


/**
 * This is a wrapper class around the lists which hold which states have been recently modified such
 * that the Engine can easily determine what the Editor has modified within the Model. This has been
 * done to ease the method call for the Engine to see what elements have been changed.
 * 
 * @author Nishad Agrawal
 *
 */
public class ModifiedContainer {
    private List<GameElementState> recentlyAddedGoals;
    private List<DrawableGameElementState> recentlyAddedTerrain;
    private List<SelectableGameElementState> recentlyAddedUnits;

    private List<GameElementState> recentlyDeletedGoals;
    private List<DrawableGameElementState> recentlyDeletedTerrain;
    private List<SelectableGameElementState> recentlyDeletedUnits;

    public ModifiedContainer () {
        recentlyAddedGoals = new ArrayList<>();
        recentlyAddedTerrain = new ArrayList<>();
        recentlyAddedUnits = new ArrayList<>();
        recentlyDeletedGoals = new ArrayList<>();
        recentlyDeletedTerrain = new ArrayList<>();
        recentlyDeletedUnits = new ArrayList<>();
    }

    public List<GameElementState> getRecentlyAddedGoals () {
        return recentlyAddedGoals;
    }

    public List<DrawableGameElementState> getRecentlyAddedTerrain () {
        return recentlyAddedTerrain;
    }

    public List<SelectableGameElementState> getRecentlyAddedUnits () {
        return recentlyAddedUnits;
    }

    public List<GameElementState> getRecentlyDeletedGoals () {
        return recentlyDeletedGoals;
    }

    public List<DrawableGameElementState> getRecentlyDeletedTerrain () {
        return recentlyDeletedTerrain;
    }

    public List<SelectableGameElementState> getRecentlyDeletedUnits () {
        return recentlyDeletedUnits;
    }

}
