package game_engine.gameRepresentation.stateRepresentation;

import java.util.List;


/**
 * This class is a collection of Levels used to string together gameplay in order to make a cohesive
 * user experience.
 * 
 * @author Steve
 *
 */
public class CampaignState {

    public String name;
    public String description;
    private List<LevelState> levels;

    public List<LevelState> getLevels () {
        return levels;
    }

    public void addLevel (LevelState level) {
        levels.add(level);
    }

}
