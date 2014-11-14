package game_engine.gameRepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


public class ObservableGame implements Observable, Describable {

    private Game myGame;
    public List<ObservableCampaign> campaigns;

    public ObservableGame (Game game) {
        myGame = game;
        campaigns = new ArrayList<ObservableCampaign>();
        myGame.getCampaigns().stream().forEach(c -> campaigns.add(new ObservableCampaign(c)));
    }

    public ObservableLevel getCurrentLevel () {
        List<Level> levels = new ArrayList<Level>();
        for (Campaign campaign : myGame.getCampaigns()) {
            for (Level level : campaign.getLevels()) {
                levels.add(level);
            }
        }

        Level activeLevel = levels.stream()
                .filter(o -> o.isActive())
                .collect(Collectors.toList()).get(0);

        return new ObservableLevel(activeLevel);
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
