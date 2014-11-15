package game_engine.gameRepresentation.stateRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class is analogous to a genre of game or a game tile such as Warcraft (tm).
 * 
 * @author Steve
 *
 */
public class GameState {

    public String name;
    public String description;
    private List<CampaignState> campaigns;

    public List<CampaignState> getCampaigns () {
        return campaigns;
    }

    public void setCurrentLevel (String name) {
        List<LevelState> levels = new ArrayList<LevelState>();
        for (CampaignState campaign : campaigns) {
            for (LevelState level : campaign.getLevels()) {
                levels.add(level);
            }
        }

        LevelState activeLevel = levels.stream()
                .filter(o -> o.name.equals(name))
                .collect(Collectors.toList()).get(0);

        activeLevel.setActive();
    }

    public LevelState getCurrentLevel () {
        List<LevelState> levels = new ArrayList<LevelState>();
        for (CampaignState campaign : campaigns) {
            for (LevelState level : campaign.getLevels()) {
                levels.add(level);
            }
        }

        LevelState activeLevel = levels.stream()
                .filter(o -> o.isActive())
                .collect(Collectors.toList()).get(0);

        return activeLevel;
    }
}
