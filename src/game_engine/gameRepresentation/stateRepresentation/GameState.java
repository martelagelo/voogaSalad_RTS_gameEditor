package game_engine.gameRepresentation.stateRepresentation;

import gamemodel.GameUniverse;
import gamemodel.exceptions.CampaignExistsException;
import gamemodel.exceptions.CampaignNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class is analogous to a genre of game or a game tile such as Warcraft (tm).
 * 
 * @author Steve, Jonathan, Nishad, Rahul
 *
 */
public class GameState extends DescribableState {

    private List<CampaignState> myCampaigns;
    private GameUniverse myGameUniverse;

    public GameState (String name) {
        super(name);
        myCampaigns = new ArrayList<>();
    }

    /**
     * gets the campaign with associated campaignName
     * 
     * @param campaignName
     * @return
     * @throws CampaignNotFoundException
     */
    public CampaignState getCampaign (String campaignName) throws CampaignNotFoundException {
        List<CampaignState> campaigns = myCampaigns.stream().filter( (campaign) -> {
            return campaign.getName().equals(campaignName);
        }).collect(Collectors.toList());
        if (campaigns.isEmpty()) {
            throw new CampaignNotFoundException(campaignName);
        }
        else {
            return campaigns.get(0);
        }
    }

    /**
     * returns unmodifiable list of campaigns
     * 
     * @return
     */
    public List<CampaignState> getCampaigns () {
        return Collections.unmodifiableList(myCampaigns);
    }

    /**
     * adds a campaign to the game
     * 
     * @param campaignToAdd
     * @throws CampaignExistsExeption
     */
    public void addCampaign (CampaignState campaignToAdd) throws CampaignExistsException {
        if (myCampaigns.stream().anyMatch( (campaign) -> {
            return campaignToAdd.getName().equals(campaign.getName());
        })) {
            throw new CampaignExistsException(campaignToAdd.getName());
        }
        else {
            myCampaigns.add(campaignToAdd);
        }
    }

    /**
     * removes a campaign with a given name
     * 
     * @param campaignName
     */
    public void removeCampaign (String campaignName) {
        myCampaigns = myCampaigns.stream().filter( (campaign) -> {
            return !campaign.getName().equals(campaignName);
        }).collect(Collectors.toList());
    }

    /**
     * returns the game universe for this game
     * 
     * @return
     */
    public GameUniverse getGameUniverse () {
        return myGameUniverse;
    }

}
