package game_engine.gameRepresentation;

import game_engine.gameRepresentation.observableRepresentation.ObservableLevel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class is analogous to a genre of game or a game tile such as Warcraft (tm).
 * 
 * @author Steve
 *
 */
public class Game {

    public String name;
    public String description;
    private List<Campaign> campaigns;

    public List<Campaign> getCampaigns () {
        return campaigns;
    }
    
    public void setCurrentLevel (String name) {
        List<Level> levels = new ArrayList<Level>();
        for (Campaign campaign : campaigns) {
            for (Level level : campaign.getLevels()) {
                levels.add(level);
            }
        }

        Level activeLevel = levels.stream()
                .filter(o -> o.name.equals(name))
                .collect(Collectors.toList()).get(0);

        activeLevel.setActive();
    }
}
