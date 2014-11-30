package gamemodel;

import editor.wizards.WizardData;
import editor.wizards.WizardDataType;
import game_engine.gameRepresentation.stateRepresentation.CampaignState;
import game_engine.gameRepresentation.stateRepresentation.DescribableState;
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
import util.SaveLoadManager;


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
<<<<<<< HEAD

    public MainModel () {
=======
    private SaveLoadManager mySaveLoadManager;

    public MainModel () {
        mySaveLoadManager = new SaveLoadManager();
>>>>>>> model
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
<<<<<<< HEAD
            myGameState = SaveLoadUtility.loadResource(GameState.class, getGameSaveLocation(game));
=======
            myGameState = mySaveLoadManager.loadGame(game);
>>>>>>> model
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
<<<<<<< HEAD
            String location = SaveLoadUtility
                    .save(myGameState, getGameSaveLocation(myGameState.getName()));
=======
            String location = mySaveLoadManager.saveGame(myGameState, myGameState.getName());
>>>>>>> model

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
    public void createGameElement (WizardData data) {
        GameElementState gameElement = GameElementStateFactory.createGameElementState(data);
        System.out.println(gameElement);
        myGameState.getGameUniverse().addGameElementState(gameElement);
        updateObservers();
    }

    /**
     * called by editor when creating a new game element
     * 
     * @param data
     */
    public void createDrawableGameElement (WizardData data) {
        // TODO: figure out the actual save location for this
        try {
<<<<<<< HEAD
            System.out.println(data.getValueByKey(GameElementStateFactory.IMAGE));

            String actualSaveLocation =
                    SaveLoadUtility.saveImage(
                                       data.getValueByKey(GameElementStateFactory.IMAGE),
                                       saveLocation + System.getProperty("file.separator")
                                               + data.getValueByKey(GameElementStateFactory.NAME) +
                                               ".png");
=======
            String actualSaveLocation = mySaveLoadManager.saveImage(data);

>>>>>>> model
            DrawableGameElementState gameElement = GameElementStateFactory
                    .createDrawableGameElementState(data, actualSaveLocation);

            System.out.println(gameElement);
            myGameState.getGameUniverse().addDrawableGameElementState(gameElement);
            setChanged();
            notifyObservers();
            clearChanged();
        } catch (IOException e) {
            // TODO remove
            e.printStackTrace();
        }
    }

    /**
     * called by editor when creating a new game element
     * 
     * @param data
     */
    public void createSelectableGameElement (WizardData data) {
        SelectableGameElementState gameElement = GameElementStateFactory
                .createSelectableGameElementState(data);
        System.out.println(gameElement);
        myGameState.getGameUniverse().addSelectableGameElementState(gameElement);
        updateObservers();
    }

    public void addGoal (WizardData data) {
        GameElementState goal = GameElementStateFactory.createGoal(data);
        myCurrentLevelState.addGoal(goal);
        updateObservers();
    }
    
    public void removeGoal (int index) {
        myCurrentLevelState.getGoals().remove(index);
        updateObservers();
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

}
