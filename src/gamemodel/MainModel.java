package gamemodel;

import game_engine.gameRepresentation.stateRepresentation.CampaignState;
import game_engine.gameRepresentation.stateRepresentation.GameState;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import gamemodel.exceptions.CampaignExistsException;
import gamemodel.exceptions.CampaignNotFoundException;
import gamemodel.exceptions.DescribableStateException;
import gamemodel.exceptions.LevelExistsException;
import gamemodel.exceptions.LevelNotFoundException;
import java.util.Observable;


/**
 * Main class for the model of the game
 * 
 * @author Jonathan Tseng
 *
 */
public class MainModel extends Observable {

    private GameState myGameState;
    private CampaignState myCurrentCampaignState;
    private LevelState myCurrentLevelState;
    private GameElementState myEditorSelectedElement;
    
    /**
     * Sets the game of the Model.
     * 
     * @param game 
     */
    public void loadGame(String game) {
        // TODO get game info and load it (save/load utility)
        myGameState = new GameState(game);
    }
    
    public GameState getCurrentGame() {
        return myGameState;
    }

    public void setCurrentLevel(String campaignName, String levelName) throws DescribableStateException {
        myCurrentCampaignState = myGameState.getCampaign(campaignName);
        myCurrentLevelState = myCurrentCampaignState.getLevel(levelName);
    }
    
    /**
     * called by editor when user selects new element from accordian pane
     * 
     * @param element
     */
    public void setEditorSelected (String elementName) {
        myEditorSelectedElement = myGameState.getGameUniverse().getElement(elementName);
    }

    /**
     * called by engine when registers click and needs what the editor has
     * selected to place on the map
     */
    public GameElementState getEditorSelected () {
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
        myGameState.addCampaign(new CampaignState(campaignName));
        myCurrentCampaignState = myGameState.getCampaign(campaignName);
    }

    /**
     * called by editor when creating a new level under a certain campaign
     * 
     * @param campaignName
     * @param levelName
     * @throws LevelExistsException
     */
    public void createLevel (String levelName, String campaignName) throws LevelExistsException,
                                                                   CampaignNotFoundException,
                                                                   LevelNotFoundException {
        myCurrentCampaignState = myGameState.getCampaign(campaignName);
        myCurrentCampaignState.addLevel(new LevelState(levelName, myCurrentCampaignState));
        myCurrentLevelState = myCurrentCampaignState.getLevel(levelName);
        setChanged();        
        notifyObservers();
        clearChanged();
    }

    /**
     * called by editor when creating a new game element
     * 
     * @param bundle
     */
    public void createGameElement (GameElementInfoBundle bundle) {
        // TODO: use factory to create game element
        GameElementState gameElement = GameElementStateFactory.createElement(bundle);
        myGameState.getGameUniverse().addElement(gameElement);
    }

    /**
     * returns the current campaign
     * 
     * @return
     */
    public CampaignState getCurrentCampaign () {
        return myCurrentCampaignState;
    }

    /**
     * returns the current level
     * 
     * @return
     */
    public LevelState getCurrentLevel () {
        return myCurrentLevelState;
    }

    /**
     * gets the game universe (list of all elements that could be used in the
     * currently selected game
     * 
     * @return
     */
    public GameUniverse getGameUniverse () {
        return myGameState.getGameUniverse();
    }

}
