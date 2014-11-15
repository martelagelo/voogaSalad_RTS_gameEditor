package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.GameState;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    
    private GameState myGameState;
    public List<Campaign> campaigns;

    public Game (GameState game) {
        myGameState = game;
        campaigns = new ArrayList<Campaign>();
        myGameState.getCampaigns().stream().forEach(c -> campaigns.add(new Campaign(c)));
    }
    
    public Level getCurrentLevel () {
        List<Level> levels = new ArrayList<Level>();
        for (Campaign campaign : campaigns) {
            for (Level level : campaign.levels) {
                levels.add(level);
            }
        }

        Level activeLevel = levels.stream()
                .filter(o -> o.isActive())
                .collect(Collectors.toList()).get(0);

        return activeLevel;
    }

    public void setCurrentLevel (String name) {
        List<Level> levels = new ArrayList<Level>();
        for (Campaign campaign : campaigns) {
            for (Level level : campaign.levels) {
                levels.add(level);
            }
        }

        Level activeLevel = levels.stream()
                .filter(o -> o.name.equals(name))
                .collect(Collectors.toList()).get(0);

        activeLevel.setActive();
    }

}
