package game_engine.gameRepresentation;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


public class ObservableCampaign implements Observable, Describable {

    private Campaign myCampaign;
    public List<ObservableLevel> levels;

    public ObservableCampaign (Campaign campaign) {
        myCampaign = campaign;
        levels = new ArrayList<ObservableLevel>();
        myCampaign.getLevels().stream().forEach(l -> levels.add(new ObservableLevel(l)));
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
