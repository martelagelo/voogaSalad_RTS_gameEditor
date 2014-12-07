package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.scene.image.ImageView;
import model.exceptions.CampaignExistsException;
import model.exceptions.CampaignNotFoundException;
import model.exceptions.ElementInUseException;
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
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
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
    private String myEditorChosenSelectableElement;
    private String myEditorChosenDrawableElement;
    private GameSaveLoadMediator mySaveLoadMediator;
    private SpriteImageGenerator mySpriteImageGenerator;
    private ModifiedContainer myModifiedContainer;

    public MainModel () throws SaveLoadException {
        clearEditorChosen();
        mySaveLoadMediator = new GameSaveLoadMediator();
        mySpriteImageGenerator = new SpriteImageGenerator();
        myModifiedContainer = new ModifiedContainer();
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
        }
        catch (SaveLoadException e) {
            DialogBoxUtility.createMessageDialog(MultiLanguageUtility.getInstance()
                    .getStringProperty(LOAD_GAME_ERROR_KEY).getValue());
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
        for (DrawableGameElementState dges : drawableStates) {
            animatorStates.add(dges.myAnimatorState);
        }
        for (SelectableGameElementState dges : selectableStates) {
            animatorStates.add(dges.myAnimatorState);
        }
        try {
            mySaveLoadMediator.loadSpritesAndMasks(animatorStates);
        }
        catch (SaveLoadException e) {
            DialogBoxUtility.createMessageDialog(e.getMessage());
        }
    }

    public void saveGame () throws RuntimeException {
        saveGame(myGameState);
    }

    public void saveGame (GameState game) {
        try {
            JSONableSet<String> existingGames =
                    SaveLoadUtility.loadResource(JSONableSet.class,
                                                 SplashScreen.EXISTING_GAMES);
            existingGames.add(game.getName());
            SaveLoadUtility.save(existingGames, SplashScreen.EXISTING_GAMES);
            mySaveLoadMediator.saveGame(game, game.getName());
        }
        catch (SaveLoadException e) {
            e.printStackTrace();
        }

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

    public void setEditorChosenDrawable (String elementName) {
        clearEditorChosen();
        myEditorChosenDrawableElement = elementName;
    }

    public void setEditorChosenSelectable (String elementName) {
        clearEditorChosen();
        myEditorChosenSelectableElement = elementName;
    }

    public void clearEditorChosen () {
        myEditorChosenSelectableElement = "";
        myEditorChosenDrawableElement = "";
    }
    
    public String getEditorChosenSelectable () {
        return myEditorChosenSelectableElement;
    }
    
    public String getEditorChosenDrawable () {
        return myEditorChosenDrawableElement;
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
                                                                                                throws LevelExistsException,
                                                                                                CampaignNotFoundException,
                                                                                                Exception {
        CampaignState campaignState = myGameState.getCampaign(campaignName.trim());
        LevelState newLevelState = new LevelState(levelName.trim());
        if (width.doubleValue() > 0 && height.doubleValue() > 0) {

            newLevelState.attributes.setNumericalAttribute(StateTags.LEVEL_WIDTH.getValue(), width);
            newLevelState.attributes.setNumericalAttribute(StateTags.LEVEL_HEIGHT.getValue(), height);
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
        }
        catch (SaveLoadException e) {
            // TODO remove
            e.printStackTrace();
        }
    }

    public void removeDrawableGameElement (String elementName) throws ElementInUseException {
        if (elementIsInUse(elementName))
            throw new ElementInUseException(elementName);
        Optional<DrawableGameElementState> option = getGameUniverse()
                .getDrawableGameElementStates().stream().filter( (state) -> {
                    return state.getName().equals(elementName);
                }).findFirst();
        if (option.isPresent()) {
            getGameUniverse().removeDrawableGameElementState(option.get());
        }
    }

    public void removeSelectableGameElement (String elementName) throws ElementInUseException {
        if (elementIsInUse(elementName))
            throw new ElementInUseException(elementName);
        Optional<SelectableGameElementState> option = getGameUniverse()
                .getSelectableGameElementStates().stream().filter( (state) -> {
                    return state.getName().equals(elementName);
                }).findFirst();
        if (option.isPresent()) {
            getGameUniverse().removeSelectableGameElementState(option.get());
        }
    }

    private boolean elementIsInUse (String elementName) {
        List<GameElementState> allStates = new ArrayList<>();
        myGameState.getCampaigns().forEach((campaign -> {
            campaign.getLevels().forEach( (level) -> {
                allStates.addAll(level.getTerrain().stream().filter( (terrain) -> {
                    return terrain.getName().equals(elementName);
                }).collect(Collectors.toList()));
                allStates.addAll(level.getUnits().stream().filter( (unit) -> {
                    return unit.getName().equals(elementName);
                }).collect(Collectors.toList()));
            });
        }));
        return allStates.size() > 0;
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
            unit.attributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), xValue);
            unit.attributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), yValue);
            myModifiedContainer.getRecentlyAddedUnits().add(unit);
            levelState.addUnit(unit);
        }
        else {
            throw new Exception("location not within level grid bounds");
        }

    }

    public void setTerrain (LevelState levelState, String terrainName) {
        int width = levelState.attributes.getNumericalAttribute(StateTags.LEVEL_WIDTH.getValue()).intValue();
        int height = levelState.attributes.getNumericalAttribute(StateTags.LEVEL_HEIGHT.getValue()).intValue();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                DrawableGameElementState terrain = getGameUniverse().getDrawableGameElementState(
                        terrainName);
                terrain.attributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), i);
                terrain.attributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), j);

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
            terrain.attributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), xValue);
            terrain.attributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), yValue);

            myModifiedContainer.getRecentlyAddedTerrain().add(terrain);
            levelState.addTerrain(terrain);
        }
        else {
            throw new Exception("location not within level grid bounds");
        }
    }

    private boolean areCoordinatesValid (LevelState levelState, Double x, Double y) {
        Number width = levelState.attributes.getNumericalAttribute(StateTags.LEVEL_WIDTH.getValue());
        Number height = levelState.attributes.getNumericalAttribute(StateTags.LEVEL_HEIGHT.getValue());
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
