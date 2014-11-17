package gamemodel;

import gamemodel.exceptions.CampaignExistsException;
import gamemodel.exceptions.CampaignNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 
 * @author Jonathan Tseng
 *
 */
public class SavableGameState extends SavableDescribableState {

    private List<SavableCampaignState> myCampaigns;
    private SavableGameUniverse myGameUniverse;

    public SavableGameState (String name) {
        super(name);
    }

    /**
     * gets the campaign with associated campaignName
     * 
     * @param campaignName
     * @return
     * @throws CampaignNotFoundException
     */
    public SavableCampaignState getCampaign (String campaignName) throws CampaignNotFoundException {
        List<SavableCampaignState> campaigns = myCampaigns.stream().filter( (campaign) -> {
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
     * adds a campaign to the game
     * 
     * @param campaignToAdd
     * @throws CampaignExistsExeption
     */
    public void addCampaign (SavableCampaignState campaignToAdd) throws CampaignExistsException {
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
    public SavableGameUniverse getGameUniverse () {
        return myGameUniverse;
    }

    public void addElement (SavableGameElementState element) {
        myGameUniverse.addElement(element);
    }

}
