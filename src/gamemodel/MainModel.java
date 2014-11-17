package gamemodel;

import gamemodel.exceptions.CampaignExistsException;
import gamemodel.exceptions.CampaignNotFoundException;
import gamemodel.exceptions.LevelExistsException;
import gamemodel.exceptions.LevelNotFoundException;


/**
 * Main class for the model of the game
 * 
 * @author Jonathan Tseng
 *
 */
public class MainModel {

    private SavableGameState mySavableGameState;
    private SavableCampaignState myCurrentSavableCampaignState;
    private SavableLevelState myCurrentSavableLevelState;
    private String myEditorSelectedElement;

    public MainModel (String game) {
        // TODO: create/get mySavableGameState based on game name
    }

    /**
     * called by editor when user selects new element from accordian pane
     * 
     * @param element
     */
    public void setEditorSelected (String element) {
        myEditorSelectedElement = element;
    }

    /**
     * called by engine when registers click and needs what the editor has
     * selected to place on the map
     */
    public String getEditorSelected () {
        return myEditorSelectedElement;
    }

    // TODO: error handling is done by view, but need to create exception classes
    // for when campaign exists already
    /**
     * called by editor when creating a new campaign
     * 
     * @param campaignName
     * @throws CampaignExistsException
     */
    public void createCampaign (String campaignName) throws CampaignExistsException,
                                                    CampaignNotFoundException {
        mySavableGameState.addCampaign(new SavableCampaignState(campaignName));
        myCurrentSavableCampaignState = mySavableGameState.getCampaign(campaignName);
    }

    // TODO: LevelExistsException class
    /**
     * called by editor when creating a new level under a certain campaign
     * 
     * @param campaignName
     * @param levelName
     * @throws LevelExistsException
     */
    public void createLevel (String campaignName, String levelName) throws LevelExistsException,
                                                                   CampaignNotFoundException,
                                                                   LevelNotFoundException {
        myCurrentSavableCampaignState = mySavableGameState.getCampaign(campaignName);
        myCurrentSavableCampaignState.addLevel(new SavableLevelState(levelName));
        myCurrentSavableLevelState = myCurrentSavableCampaignState.getLevel(levelName);
    }

    /**
     * called by editor when creating a new game element
     * 
     * @param bundle
     */
    public void createGameElement (GameElementInfoBundle bundle) {
        // TODO: use factory to create game element
        SavableGameElementState gameElement = SavableGameElementFactory.createElement(bundle);
        mySavableGameState.addElement(gameElement);
    }

    /**
     * returns the current campaign
     * 
     * @return
     */
    public SavableCampaignState getCurrentCampaign () {
        return myCurrentSavableCampaignState;
    }

    /**
     * returns the current level
     * 
     * @return
     */
    public SavableLevelState getCurrentLevel () {
        return myCurrentSavableLevelState;
    }

    /**
     * gets the game universe (list of all elements that could be used in the
     * currently selected game
     * 
     * @return
     */
    public SavableGameUniverse getGameUniverse () {
        return mySavableGameState.getGameUniverse();
    }

}
