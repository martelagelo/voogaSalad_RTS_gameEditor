package gamemodel;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Jonathan Tseng
 *
 */
public class SavableLevelState extends SavableDescribableState {

    private List<SavableDrawableGameElementState> myTerrains;
    private List<SavableSelectableGameElementState> myUnits;
    private List<SavableGameElementState> myGoals;

    public SavableLevelState (String name) {
        super(name);
        myTerrains = new ArrayList<>();
        myUnits = new ArrayList<>();
        myGoals = new ArrayList<>();
    }

    private void addTerrain (SavableDrawableGameElementState terrain) {
        myTerrains.add(terrain);
    }

    private void addUnit (SavableSelectableGameElementState unit) {
        myUnits.add(unit);
    }

    private void addGoal (SavableGameElementState goal) {
        myGoals.add(goal);
    }

}
