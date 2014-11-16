package game_engine.gameRepresentation.immutableRepresentation;

import game_engine.gameRepresentation.Describable;
import game_engine.gameRepresentation.stateRepresentation.CampaignState;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


/**
 * This wrapper allows for the Engine to pass an Object to the GUI without exposing internal state
 * we want hidden.
 * 
 * @author Steve
 *
 */
public class ImmutableCampaign implements Observable, Describable {

    private CampaignState myCampaign;
    public List<ImmutableLevel> levels;

    public ImmutableCampaign (CampaignState campaign) {
        myCampaign = campaign;
        levels = new ArrayList<ImmutableLevel>();
        myCampaign.getLevels().stream().forEach(l -> levels.add(new ImmutableLevel(l)));
    }

    @Override
    public void addListener (InvalidationListener arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeListener (InvalidationListener arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getName () {
        return myCampaign.name;
    }

    @Override
    public String getDescription () {
        return myCampaign.description;
    }

}
