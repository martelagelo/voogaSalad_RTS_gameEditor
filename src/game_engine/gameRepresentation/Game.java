package game_engine.gameRepresentation;

import java.util.List;


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
}
