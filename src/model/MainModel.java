package model;

import java.io.IOException;
import java.util.Observable;
import java.util.Optional;
import model.exceptions.CampaignExistsException;
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelExistsException;
import model.exceptions.LevelNotFoundException;
import model.sprite.SpriteImageContainer;
import model.sprite.SpriteImageGenerator;
import model.state.CampaignState;
import model.state.DescribableState;
import model.state.GameState;
import model.state.LevelState;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import util.GameSaveLoadMediator;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;


/**
 * Main class for the model of the game
 * 
 * @author Jonathan Tseng, Rahul Harikrishnan, Nishad Agrawal
 *
 */
public class MainModel extends Observable {

    private GameState myGameState;
    private GameElementState myEditorSelectedElement;
    private GameSaveLoadMediator mySaveLoadMediator;
    private SpriteImageGenerator mySpriteImageGenerator;
    private ModifiedContainer myModifiedContainer;


    public MainModel () {
        try {
            mySaveLoadMediator = new GameSaveLoadMediator();
            myModifiedContainer = new ModifiedContainer();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
            myGameState = mySaveLoadMediator.loadGame(game);
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
            String location = mySaveLoadMediator.saveGame(myGameState, myGameState.getName());
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
        CampaignState newCampaignState = new CampaignState(campaignName.trim());
        myGameState.addCampaign(newCampaignState);
        updateObservers();
    }

    /**
     * called by editor when creating a new level under a certain campaign
     * 
     * @param campaignName
     * @param levelName
     * @throws LevelExistsException
     */
    public void createLevel (String levelName, String campaignName, Number width, Number height) throws LevelExistsException,
                                                                   CampaignNotFoundException {
        CampaignState campaignState = myGameState.getCampaign(campaignName.trim());
        LevelState newLevelState = new LevelState(levelName.trim());
        newLevelState.attributes.setNumericalAttribute(StateTags.LEVEL_WIDTH, width);
        newLevelState.attributes.setNumericalAttribute(StateTags.LEVEL_HEIGHT, height);
        campaignState.addLevel(newLevelState);
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
            // TODO: actualSaveLocation gets ignored now that we use the json file. That itself
            // specifies the save location so we dont need to make this method call below
            String actualSaveLocation = mySaveLoadMediator.saveImage(data);
            data.addDataPair(WizardDataType.IMAGE, actualSaveLocation);
            DrawableGameElementState gameElement = GameElementStateFactory
                    .createDrawableGameElementState(data);
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
        try {
            String actualSaveLocation = mySaveLoadMediator.saveImage(data);
            data.addDataPair(WizardDataType.IMAGE, actualSaveLocation);
            
            SelectableGameElementState gameElement = GameElementStateFactory
                    .createSelectableGameElementState(data);
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

    public void createGoal (LevelState levelState, WizardData data) {
        GameElementState goal = GameElementStateFactory.createGoal(data);
        levelState.addGoal(goal);
        myModifiedContainer.getRecentlyAddedGoals().add(goal);
        updateObservers();
    }

    public void removeGoal (LevelState levelState, int index) {
        GameElementState goal = levelState.getGoals().get(index);
        levelState.getGoals().remove(goal);
        myModifiedContainer.getRecentlyDeletedGoals().add(goal);
        updateObservers();
    }

    public void addUnitToLevel (LevelState levelState, String elementName, Double xValue, Double yValue) {
        // TODO: check that the position is actually inside the grid
        SelectableGameElementState unit =
                getGameUniverse().getSelectableGameElementState(elementName);
        unit.attributes.setNumericalAttribute(StateTags.X_POSITION, xValue);
        unit.attributes.setNumericalAttribute(StateTags.Y_POSITION, yValue);
        myModifiedContainer.getRecentlyAddedUnits().add(unit);
        levelState.addUnit(unit);
    }

    public void setTerrain (LevelState levelState, String terrainName) {
        int width =
                levelState.attributes.getNumericalAttribute(StateTags.LEVEL_WIDTH)
                        .intValue();
        int height =
                levelState.attributes.getNumericalAttribute(StateTags.LEVEL_HEIGHT)
                        .intValue();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                DrawableGameElementState terrain =
                        getGameUniverse().getDrawableGameElementState(terrainName);
                terrain.attributes.setNumericalAttribute(StateTags.X_POSITION, i);
                terrain.attributes.setNumericalAttribute(StateTags.Y_POSITION, j);
                myModifiedContainer.getRecentlyAddedTerrain().add(terrain);
                levelState.addTerrain(terrain);
            }
        }
    }

    public void addTerrainToLevel (LevelState levelState, String elementName, Double xValue, Double yValue) {
        // TODO: check that the position is actually inside the grid
        DrawableGameElementState terrain =
                getGameUniverse().getDrawableGameElementState(elementName);
        terrain.attributes.setNumericalAttribute(StateTags.X_POSITION, xValue);
        terrain.attributes.setNumericalAttribute(StateTags.Y_POSITION, yValue);
        myModifiedContainer.getRecentlyAddedTerrain().add(terrain);
        levelState.addTerrain(terrain);
    }

    private void updateObservers () {
        setChanged();
        notifyObservers();
        clearChanged();
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

    public ModifiedContainer getModifiedContainer () {
        return myModifiedContainer;
    }

    /**
     * 
     * @param imageTag
     * @return
     * @throws Exception
     */
    public SpriteImageContainer fetchImageContainer (String imageTag) {
        return mySpriteImageGenerator.fetchImageContainer(imageTag);
    }

}
