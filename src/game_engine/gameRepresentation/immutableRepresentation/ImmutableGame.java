package game_engine.gameRepresentation.immutableRepresentation;

import game_engine.gameRepresentation.Describable;
import game_engine.gameRepresentation.stateRepresentation.GameState;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
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
public class ImmutableGame implements Observable, Describable {

    private GameState myGame;
    public List<ImmutableCampaign> campaigns;

    public ImmutableGame (GameState game) {
        myGame = game;
        campaigns = new ArrayList<ImmutableCampaign>();
        myGame.getCampaigns().stream().forEach(c -> campaigns.add(new ImmutableCampaign(c)));
    }

    public ImmutableLevel getCurrentLevel () {
        List<ImmutableLevel> levels = new ArrayList<ImmutableLevel>();
        for (ImmutableCampaign campaign : campaigns) {
            for (ImmutableLevel level : campaign.levels) {
                levels.add(level);
            }
        }

        ImmutableLevel activeLevel = levels.stream()
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
