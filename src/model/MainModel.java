package model;

import java.io.IOException;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import model.exceptions.CampaignExistsException;
import model.exceptions.CampaignNotFoundException;
import model.exceptions.DescribableStateException;
import model.exceptions.LevelExistsException;
import model.exceptions.LevelNotFoundException;
import model.state.CampaignState;
import model.state.DescribableState;
import model.state.GameState;
import model.state.LevelState;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import util.SaveLoadMediator;
import view.editor.wizards.WizardData;


/**
 * Main class for the model of the game
 * 
 * @author Jonathan Tseng, Rahul Harikrishnan, Nishad Agrawal
 *
 */
public class MainModel extends Observable {

    private GameState myGameState;
    private CampaignState myCurrentCampaignState;
    private LevelState myCurrentLevelState;
    private GameElementState myEditorSelectedElement;
    private SaveLoadMediator mySaveLoadManager;
    private Map<String, SpriteImageContainer> imageConatinerMap;

    public MainModel () {
        mySaveLoadManager = new SaveLoadMediator();
    }

    public void newGame (String gameName) {
        // TODO CLEAN THIS UP
        myGameState = new GameState(gameName);
    }

    /**
     * Sets the game of the Model.
     * 
     * @param game
     * @throws Exception
     */
    public void loadGame (String game) throws Exception {

        try {
            // TODO: insert Save Load code here and instantiate myGameState
            myGameState = mySaveLoadManager.loadGame(game);
            // TODO remove print lines
            System.out.println(myGameState.getCampaigns().get(0).getLevels().get(0));
        }
        catch (Exception e) {
            // TODO Get rid of stack trace printing
            e.printStackTrace();
        }
        updateObservers();
    }

    public void updateDescribableState (String[] selection, String name, String description)
                                                                                            throws CampaignNotFoundException,
                                                                                            LevelNotFoundException {
        DescribableState state = getDescribableState(selection);
        state.updateName(name);
        state.updateDescription(description);
        updateObservers();
    }

    public DescribableState getDescribableState (String[] selection)
                                                                    throws CampaignNotFoundException,
                                                                    LevelNotFoundException {
        if (selection[2].isEmpty()) {
            if (selection[1].isEmpty()) {
                return myGameState;
            }
            else {
                return myGameState.getCampaign(selection[1]);
            }
        }
        else {
            return myGameState.getCampaign(selection[1]).getLevel(selection[2]);
        }
    }

    public void saveGame () throws RuntimeException {
        try {
            // TODO: Save location
            String location = mySaveLoadManager.saveGame(myGameState, myGameState.getName());
        }
        catch (Exception e) {
            // TODO: eliminate stack trace printing
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
    }

    public GameState getCurrentGame () {
        return myGameState;
    }

    public LevelState getLevel (String campaignName, String levelName)
                                                                      throws LevelNotFoundException,
                                                                      CampaignNotFoundException {
        return getCampaign(campaignName).getLevel(levelName);
    }

    public CampaignState getCampaign (String campaignName) throws CampaignNotFoundException {
        return myGameState.getCampaign(campaignName);
    }

    public void setCurrentLevel (String campaignName, String levelName)
                                                                       throws DescribableStateException {
        myCurrentCampaignState = myGameState.getCampaign(campaignName);
        myCurrentLevelState = myCurrentCampaignState.getLevel(levelName);
        updateObservers();
    }

    /**
     * called by editor when user selects new element from accordion pane
     * 
     * @param element
     */
    public void setEditorSelected (String elementName) {
        updateObservers();
    }

    /**
     * called by engine when registers click and needs what the editor has
     * selected to place on the map
     */
    public GameElementState getEditorSelected () {
        return myEditorSelectedElement;
    }

    // TODO: error handling is done by view, but need to create exception
    // classes
    // for when campaign exists already
    /**
     * called by editor when creating a new campaign
     * 
     * @param campaignName
     * @throws CampaignExistsException
     */
    public void createCampaign (String campaignName) throws CampaignExistsException {
        myCurrentCampaignState = new CampaignState(campaignName.trim());
        myGameState.addCampaign(myCurrentCampaignState);
        updateObservers();
    }

    /**
     * called by editor when creating a new level under a certain campaign
     * 
     * @param campaignName
     * @param levelName
     * @throws LevelExistsException
     */
    public void createLevel (String levelName, String campaignName) throws LevelExistsException,
                                                                   CampaignNotFoundException {
        myCurrentCampaignState = myGameState.getCampaign(campaignName.trim());
        myCurrentLevelState = new LevelState(levelName.trim());
        myCurrentCampaignState.addLevel(myCurrentLevelState);
        updateObservers();
    }

    /**
     * called by editor when creating a new game element
     * 
     * @param data
     */
    public void createGameElementState (WizardData data) {
        GameElementState gameElement = GameElementStateFactory.createGameElementState(data);
        myGameState.getGameUniverse().addGameElementState(gameElement);
        updateObservers();
    }

    /**
     * called by editor when creating a new game element
     * 
     * @param data
     */
    public void createDrawableGameElementState (WizardData data) {
        try {
            String actualSaveLocation = mySaveLoadManager.saveImage(data);
            DrawableGameElementState gameElement = GameElementStateFactory
                    .createDrawableGameElementState(data, actualSaveLocation);
            myGameState.getGameUniverse().addDrawableGameElementState(gameElement);
            updateObservers();
        }
        catch (IOException e) {
            // TODO remove
            e.printStackTrace();
        }
    }

    /**
     * called by editor when creating a new game element
     * 
     * @param data
     */
    public void createSelectableGameElementState (WizardData data) {
        // TODO: figure out the actual save location for this
        try {
            String actualSaveLocation = mySaveLoadManager.saveImage(data);
            SelectableGameElementState gameElement = GameElementStateFactory
                    .createSelectableGameElementState(data, actualSaveLocation);
            myGameState.getGameUniverse().addSelectableGameElementState(gameElement);
            updateObservers();
        }
        catch (IOException e) {
            // TODO remove
            e.printStackTrace();
        }
    }

    public void removeDrawableGameElement (String elementName) {
        Optional<DrawableGameElementState> option =
                getGameUniverse().getDrawableGameElementStates().stream().filter( (state) -> {
                    return state.getName().equals(elementName);
                }).findFirst();
        if (option.isPresent()) {
            getGameUniverse().removeDrawableGameElementState(option.get());
        }
    }

    public void removeSelectableGameElement (String elementName) {
        Optional<SelectableGameElementState> option =
                getGameUniverse().getSelectableGameElementStates().stream().filter( (state) -> {
                    return state.getName().equals(elementName);
                }).findFirst();
        if (option.isPresent()) {
            getGameUniverse().removeSelectableGameElementState(option.get());
        }
    }

    public void createGoal (WizardData data) {
        GameElementState goal = GameElementStateFactory.createGoal(data);
        myCurrentLevelState.addGoal(goal);
        updateObservers();
    }

    public void removeGoal (int index) {
        myCurrentLevelState.getGoals().remove(index);
        updateObservers();
    }
    
    public void addSelectableToLevel (String elementName) {
        // TODO: do i need to deep copy this into the level state?
        getCurrentLevel().addUnit(getGameUniverse().getSelectableGameElementState(elementName));
    }

    public void setTerrain (String terrainName) {
        // TODO: do i need to deep copy this into the level state?
        
    }
    
    public void addDrawableToLevel (String elementName) {
        // TODO: do i need to deep copy this into the level state?
        getCurrentLevel().addTerrain(getGameUniverse().getDrawableGameElementState(elementName));
    }

    private void updateObservers () {
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

    public SpriteImageContainer fetchImageContainer (String imageTag) {
        return imageConatinerMap.get(imageTag);
    }

}
