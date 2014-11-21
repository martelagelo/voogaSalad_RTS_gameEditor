package gamemodel;

import editor.wizards.WizardData;
import game_engine.gameRepresentation.stateRepresentation.CampaignState;
import game_engine.gameRepresentation.stateRepresentation.GameState;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import gamemodel.exceptions.CampaignExistsException;
import gamemodel.exceptions.CampaignNotFoundException;
import gamemodel.exceptions.DescribableStateException;
import gamemodel.exceptions.LevelExistsException;
import gamemodel.exceptions.LevelNotFoundException;
import java.io.IOException;
import java.util.Observable;
import util.SaveLoadUtility;


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
    private SaveLoadUtility mySLUtil;
    
    public MainModel() {
        mySLUtil = new SaveLoadUtility();
    }

    /**
     * Sets the game of the Model.
     * 
     * @param game
     */
    public void loadGame (String game) {
        if (game.equals("New Game")) {
            myGameState = new GameState(game);
            System.out.println("yay");
        }
        else {
            //TODO: insert Save Load code here and instantiate myGameState
            myGameState = mySLUtil.loadResource(GameState.class, game);            
        }        
        setChanged();
        notifyObservers();
        clearChanged();
    }
    
    public void saveGame() throws RuntimeException {
        try {
            mySLUtil.save(myGameState, "myGames" + System.getProperty("file.separator") + myGameState.getName());
        }
        catch (IOException e) { 
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
    }

    public GameState getCurrentGame () {
        return myGameState;
    }

    public void setCurrentLevel (String campaignName, String levelName)
                                                                       throws DescribableStateException {
        myCurrentCampaignState = myGameState.getCampaign(campaignName);
        myCurrentLevelState = myCurrentCampaignState.getLevel(levelName);
    }

    /**
     * called by editor when user selects new element from accordion pane
     * 
     * @param element
     */
    public void setEditorSelected (String elementName) {
//        myEditorSelectedElement = myGameState.getGameUniverse().getElement(elementName);
        setChanged();
        notifyObservers();
        clearChanged();
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
        setChanged();
        notifyObservers();
        clearChanged();
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
     * @param data
     */
    public void createGameElement (WizardData data) {
        // TODO: use factory to create game element
        GameElementState gameElement = GameElementStateFactory.createGameElementState(data);
        System.out.println(gameElement);
        myGameState.getGameUniverse().addGameElementState(gameElement);
        setChanged();
        notifyObservers();
        clearChanged();
    }
    
    /**
     * called by editor when creating a new game element
     * 
     * @param data
     */
    public void createDrawableGameElement (WizardData data) {
        DrawableGameElementState gameElement = GameElementStateFactory.createDrawableGameElementState(data);
        System.out.println(gameElement);
        myGameState.getGameUniverse().addDrawableGameElementState(gameElement);
        setChanged();
        notifyObservers();
        clearChanged();
    }
    
    /**
     * called by editor when creating a new game element
     * 
     * @param data
     */
    public void createSelectableGameElement (WizardData data) {
        SelectableGameElementState gameElement = GameElementStateFactory.createSelectableGameElementState(data);
        System.out.println(gameElement);
        myGameState.getGameUniverse().addSelectableGameElementState(gameElement);
        setChanged();
        notifyObservers();
        clearChanged();
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
