package game_engine.gameRepresentation.observableRepresentation;

import game_engine.gameRepresentation.Describable;
import game_engine.gameRepresentation.Game;
import game_engine.gameRepresentation.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


/**
 * This wrapper allows for the Engine to pass an Object to the GUI without exposing internal state
 * we want hidden.
 * 
 * @author Steve
 *
 */
public class ObservableGame implements Observable, Describable {

    private Game myGame;
    public List<ObservableCampaign> campaigns;

    public ObservableGame (Game game) {
        myGame = game;
        campaigns = new ArrayList<ObservableCampaign>();
        myGame.getCampaigns().stream().forEach(c -> campaigns.add(new ObservableCampaign(c)));
    }

    public ObservableLevel getCurrentLevel () {
        List<ObservableLevel> levels = new ArrayList<ObservableLevel>();
        for (ObservableCampaign campaign : campaigns) {
            for (ObservableLevel level : campaign.levels) {
                levels.add(level);
            }
        }

        ObservableLevel activeLevel = levels.stream()
                .filter(o -> o.isActive())
                .collect(Collectors.toList()).get(0);

        return activeLevel;
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
        return myGame.name;
    }

    @Override
    public String getDescription () {
        return myGame.description;
    }

}
