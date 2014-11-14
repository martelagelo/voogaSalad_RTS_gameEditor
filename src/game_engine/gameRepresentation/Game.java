package game_engine.gameRepresentation;

import java.util.List;


public class Game {

    public String name;
    public String description;
    private List<Campaign> campaigns;
    
    public List<Campaign> getCampaigns(){
        return campaigns;
    }
}
