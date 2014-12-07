package model;

import java.util.HashSet;
import java.util.Observable;
import java.util.Optional;
import java.util.Set;

import javafx.scene.image.ImageView;
import model.exceptions.CampaignExistsException;
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelExistsException;
import model.exceptions.LevelNotFoundException;
import model.exceptions.SaveLoadException;
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
import util.JSONableSet;
import util.SaveLoadUtility;
import util.multilanguage.LanguagePropertyNotFoundException;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;
import view.splash.SplashScreen;
import engine.visuals.elementVisuals.animations.AnimatorState;

/**
 * Main class for the model of the game
 * 
 * @author Jonathan Tseng, Rahul Harikrishnan, Nishad Agrawal
 *
 */
public class MainModel extends Observable {

    private static final String LOAD_GAME_ERROR_KEY = "LoadGameError";

    private GameState myGameState;
    private GameElementState myEditorSelectedElement;
    private GameSaveLoadMediator mySaveLoadMediator;
    private SpriteImageGenerator mySpriteImageGenerator;
    private ModifiedContainer myModifiedContainer;

    public MainModel () {
        try {
            mySaveLoadMediator = new GameSaveLoadMediator();
            mySpriteImageGenerator = new SpriteImageGenerator();
            myModifiedContainer = new ModifiedContainer();
        } catch (SaveLoadException e) {
            System.out.println(mySpriteImageGenerator == null);
            // TODO: Display error in View
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
     * @throws LanguagePropertyNotFoundException
     */
    public void loadGame (String game) throws LanguagePropertyNotFoundException {
        // TODO: Move the dialog box utility message dialog to a View class as
        // opposed to in model.
        try {
            // TODO: insert Save Load code here and instantiate myGameState
            myGameState = mySaveLoadMediator.loadGame(game);
        } catch (SaveLoadException e) {
            e.printStackTrace();
            // DialogBoxUtility.createMessageDialog(MultiLanguageUtility.getInstance()
            // .getStringProperty(LOAD_GAME_ERROR_KEY).getValue());
        }

        loadSpritesAndMasks();

        updateObservers();
    }

    private void loadSpritesAndMasks () {
        Set<DrawableGameElementState> drawableStates = myGameState.getGameUniverse()
                .getDrawableGameElementStates();
        Set<SelectableGameElementState> selectableStates = myGameState.getGameUniverse()
                .getSelectableGameElementStates();

        Set<AnimatorState> animatorStates = new HashSet<>();
        for (DrawableGameElementState dges : selectableStates) {
            animatorStates.add(dges.myAnimatorState);
        }
        try {
            mySaveLoadMediator.loadSpritesAndMasks(animatorStates);
        } catch (SaveLoadException e) {
            // TODO: Display error to view
            // Remove exception
            e.printStackTrace();
        }
    }

    public void saveGame () throws RuntimeException {
        saveGame(myGameState);
    }

    public void saveGame (GameState game) {
        try {
            JSONableSet<String> existingGames = SaveLoadUtility.loadResource(JSONableSet.class,
                    SplashScreen.EXISTING_GAMES);
            existingGames.add(game.getName());
            SaveLoadUtility.save(existingGames, SplashScreen.EXISTING_GAMES);
            mySaveLoadMediator.saveGame(game, game.getName());
        } catch (SaveLoadException e) {
            e.printStackTrace();
        }

    }

    public void updateDescribableState (String[] selection, String name, String description)
            throws CampaignNotFoundException, LevelNotFoundException {
        DescribableState state = getDescribableState(selection);
        state.updateName(name);
        state.updateDescription(description);
        updateObservers();
    }

    public DescribableState getDescribableState (String[] selection)
            throws CampaignNotFoundException, LevelNotFoundException {
        if (selection[2].isEmpty()) {
            if (selection[1].isEmpty()) {
                return myGameState;
            } else {
                return myGameState.getCampaign(selection[1]);
            }
        } else {
            return myGameState.getCampaign(selection[1]).getLevel(selection[2]);
        }
    }

    public GameState getCurrentGame () {
        return myGameState;
    }

    public LevelState getLevel (String campaignName, String levelName)
            throws LevelNotFoundException, CampaignNotFoundException {
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
    public void createLevel (String levelName, String campaignName, Number width, Number height)
            throws LevelExistsException, CampaignNotFoundException, Exception {
        CampaignState campaignState = myGameState.getCampaign(campaignName.trim());
        LevelState newLevelState = new LevelState(levelName.trim());
        if (width.doubleValue() > 0 && height.doubleValue() > 0) {
            newLevelState.attributes.setNumericalAttribute(StateTags.LEVEL_WIDTH, width);
            newLevelState.attributes.setNumericalAttribute(StateTags.LEVEL_HEIGHT, height);
        } else {
            throw new Exception("invalid size of level");
        }
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
        DrawableGameElementState gameElement = GameElementStateFactory
                .createDrawableGameElementState(data);
        myGameState.getGameUniverse().addDrawableGameElementState(gameElement);
        updateObservers();
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
        } catch (SaveLoadException e) {
            // TODO remove
            e.printStackTrace();
        }
    }

    public void removeDrawableGameElement (String elementName) {
        Optional<DrawableGameElementState> option = getGameUniverse()
                .getDrawableGameElementStates().stream().filter( (state) -> {
                    return state.getName().equals(elementName);
                }).findFirst();
        if (option.isPresent()) {
            getGameUniverse().removeDrawableGameElementState(option.get());
        }
    }

    public void removeSelectableGameElement (String elementName) {
        Optional<SelectableGameElementState> option = getGameUniverse()
                .getSelectableGameElementStates().stream().filter( (state) -> {
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

    public void addUnitToLevel (LevelState levelState, String elementName, Double xValue,
            Double yValue) throws Exception {
        if (areCoordinatesValid(levelState, xValue, yValue)) {
            SelectableGameElementState unit = getGameUniverse().getSelectableGameElementState(
                    elementName);
            unit.attributes.setNumericalAttribute(StateTags.X_POSITION, xValue);
            unit.attributes.setNumericalAttribute(StateTags.Y_POSITION, yValue);
            myModifiedContainer.getRecentlyAddedUnits().add(unit);
            levelState.addUnit(unit);
        } else {
            throw new Exception("location not within level grid bounds");
        }

    }

    public void setTerrain (LevelState levelState, String terrainName) {
        int width = levelState.attributes.getNumericalAttribute(StateTags.LEVEL_WIDTH).intValue();
        int height = levelState.attributes.getNumericalAttribute(StateTags.LEVEL_HEIGHT).intValue();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                DrawableGameElementState terrain = getGameUniverse().getDrawableGameElementState(
                        terrainName);
                terrain.attributes.setNumericalAttribute(StateTags.X_POSITION, i);
                terrain.attributes.setNumericalAttribute(StateTags.Y_POSITION, j);
                myModifiedContainer.getRecentlyAddedTerrain().add(terrain);
                levelState.addTerrain(terrain);
            }
        }
    }

    public void addTerrainToLevel (LevelState levelState, String elementName, Double xValue,
            Double yValue) throws Exception {
        if (areCoordinatesValid(levelState, xValue, yValue)) {
            DrawableGameElementState terrain = getGameUniverse().getDrawableGameElementState(
                    elementName);
            terrain.attributes.setNumericalAttribute(StateTags.X_POSITION, xValue);
            terrain.attributes.setNumericalAttribute(StateTags.Y_POSITION, yValue);
            myModifiedContainer.getRecentlyAddedTerrain().add(terrain);
            levelState.addTerrain(terrain);
        } else {
            throw new Exception("location not within level grid bounds");
        }
    }

    private boolean areCoordinatesValid (LevelState levelState, Double x, Double y) {
        Number width = levelState.attributes.getNumericalAttribute(StateTags.LEVEL_WIDTH);
        Number height = levelState.attributes.getNumericalAttribute(StateTags.LEVEL_HEIGHT);
        return (x >= 0 && width.doubleValue() > x && y >= 0 && height.doubleValue() > y);
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
        SpriteImageContainer matchingContainer = mySpriteImageGenerator
                .fetchImageContainer(imageTag);
        ImageView newImageView = new ImageView(matchingContainer.getSpritesheet().getImage());
        ImageView newColorMaskImageView = new ImageView(matchingContainer.getColorMask("BLUE")
                .getImage());
        SpriteImageContainer spriteContainer = new SpriteImageContainer(newImageView,
                newColorMaskImageView);
        return spriteContainer;
    }
}
