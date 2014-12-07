package model;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javafx.scene.image.Image;
import model.exceptions.SaveLoadException;
import model.sprite.SpriteImageGenerator;
import model.state.GameState;
import util.JSONable;
import util.SaveLoadUtility;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;
import engine.visuals.elementVisuals.animations.AnimatorState;

/**
 * This class mediates between the Model and the SaveLoadUtility. Using the
 * composition pattern, MainModel holds a reference to this class allowing us to
 * consolidate all file path related function calls in one place and
 * specifically away from the model.
 * 
 * @author Rahul
 *
 */
public class GameSaveLoadMediator {
    public static final String GAME_ELEMENT_RESOURCES = "gameelementresources";
    public static final String WILDCARD = "*";
    public static final String ANIMATOR_STATE = "animatorstate";
    public static final String GAME_DIRECTORY = "myGames";
    public static final String RESOURCES = "resources";
    public static final String PNG_EXT = ".png";
    public static final String JSON_EXT = ".json";
    public static final String PATH_DELIMITER = "/";

    /**
     * 
     * @param gameName
     * @return
     * @throws Exception
     */
    public <T> T loadGame (String gameName) throws SaveLoadException {
        return SaveLoadUtility.loadResource(GameState.class, getGameLocation(gameName));

    }

    /**
     * 
     * @param gameState
     * @param gameName
     * @return
     * @throws Exception
     */
    public String saveGame (JSONable gameState, String gameName) throws SaveLoadException {
        return SaveLoadUtility.save(gameState, getGameLocation(gameName));

    }

    /**
     * 
     * @param data
     * @param elementType
     * @return
     * @throws SaveLoadException
     */
    public String saveImage (WizardData data, GameElementImageType elementType)
            throws SaveLoadException {
        String localLocation = data.getValueByKey(WizardDataType.IMAGE);
        String destinationLocation = processImagePath(localLocation,
                GameElementImageType.Spritesheet, elementType);
        String savedLocation = SaveLoadUtility.saveImage(localLocation, destinationLocation);
        return savedLocation;
    }

    private String processImagePath (String localLocation, GameElementImageType imageType,
            GameElementImageType stateType) {
        String imageName = getImageName(localLocation);
        String saveLocation = GAME_ELEMENT_RESOURCES + PATH_DELIMITER + stateType.getFolderName()
                + PATH_DELIMITER + imageType.getFolderName() + PATH_DELIMITER + imageName;
        return saveLocation;
    }

    private String getImageName (String localLocation) {
        File file = new File(localLocation);
        return file.getName();
    }

    /**
     * 
     * @param data
     * @return
     * @throws IOException
     */
    public String saveColorMask (WizardData data, GameElementImageType elementType)
            throws SaveLoadException {
        String currentLocation = data.getValueByKey(WizardDataType.COLOR_MASK);
        String destinationLocation = processImagePath(currentLocation,
                GameElementImageType.Colormask, elementType);
        return SaveLoadUtility.saveImage(currentLocation, destinationLocation);

    }

    private String getGameLocation (String name) {
        return GAME_DIRECTORY + PATH_DELIMITER + name + PATH_DELIMITER + name + JSON_EXT;
    }

    /**
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
    public Image loadImage (String filePath) throws SaveLoadException {
        return SaveLoadUtility.loadImage(filePath);

    }

    public void loadSpritesAndMasks (Set<AnimatorState> animatorStates) throws SaveLoadException {
        SpriteImageGenerator.loadSpriteImageContainers(animatorStates);

    }

}