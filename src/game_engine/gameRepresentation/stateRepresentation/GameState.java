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

}
