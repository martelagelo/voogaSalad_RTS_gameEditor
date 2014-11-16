package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.CampaignState;
import java.util.ArrayList;
import java.util.List;


public class Campaign {

    private CampaignState myCampaignState;
    public List<Level> levels;

    public Campaign (CampaignState state) {
        myCampaignState = state;
        levels = new ArrayList<Level>();
        myCampaignState.getLevels().stream().forEach(l -> levels.add(new Level(l)));
    }
}
