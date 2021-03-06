package model.state;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.GameUniverse;
import model.exceptions.CampaignExistsException;
import model.exceptions.CampaignNotFoundException;
import model.state.gameelement.AttributeContainer;


/**
 * This class is analogous to a genre of game or a game tile such as Warcraft (tm).
 * 
 * @author Steve, Jonathan, Nishad, Rahul
 *
 */
public class GameState extends DescribableState {

    /**
     * 
     */
    private static final long serialVersionUID = -7286615449971559602L;
    private List<CampaignState> myCampaigns;
    private List<HighScore> myHighScores;
    private GameUniverse myGameUniverse;

    public GameState (String name) {
        super(name);
        myHighScores = new ArrayList<>();
        myCampaigns = new ArrayList<>();
        myGameUniverse = new GameUniverse();
    }

    /**
     * gets the campaign with associated campaignName
     * 
     * @param campaignName
     *        the name of the campaign to be returned
     * @return
     *        the Campaign with the associated campaignName
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
     * @return List<CampaignState>
     *         the list of the gamestate's campaignstates
     */
    public List<CampaignState> getCampaigns () {
        return myCampaigns;
    }

    /**
     * adds a campaign to the game
     * 
     * @param campaignToAdd
     *        the campaignstate to be added to the gamestate's campaignstate's
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
     *        the name of the campaign to be removed
     */
    public void removeCampaign (String campaignName) {
        myCampaigns = myCampaigns.stream().filter( (campaign) -> {
            return !campaign.getName().equals(campaignName);
        }).collect(Collectors.toList());
    }
    
    /**
     * Adds a high score to a game given a player's
     * attributes at the end of a game
     * 
     * @param player
     * 		  an attributecontainer which holds the player's
     * 		  attributes
     */
    public void addHighScore (AttributeContainer player) {
    	HighScore highScore = new HighScore(player);
    	myHighScores.add(highScore);
    }
    
    /**
     * Returns the list of highscores of the game
     * 
     * @return myHighScores
     * 		   the list of highscores of the game
     */
    public List<HighScore> getHighScores () {
    	return myHighScores;
    }

    /**
     * returns the game universe for this game
     * 
     * @return GameUniverse
     *         the GameUniverse of the GameState
     */
    public GameUniverse getGameUniverse () {
        return myGameUniverse;
    }

}
