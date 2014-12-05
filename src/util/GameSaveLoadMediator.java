package util;

import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;
import model.exceptions.SaveLoadException;
import model.state.GameState;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;

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

    public GameSaveLoadMediator () throws Exception {

    }

    /**
     * 
     * @param gameName
     * @return
     * @throws Exception
     */
    public <T> T loadGame (String gameName) throws Exception {
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
     * @return
     * @throws IOException
     */
    public String saveImage (WizardData data) throws SaveLoadException {
        // TODO: Remove this hardcoded save location
        String saveLocation = "testSpritesheet";

        return SaveLoadUtility.saveImage(data.getValueByKey(WizardDataType.IMAGE), saveLocation
                + File.separator + data.getValueByKey(WizardDataType.NAME) + PNG_EXT);

    }

    private String getGameLocation (String name) {
        return GAME_DIRECTORY + File.separator + name + File.separator + name + JSON_EXT;
    }

    /**
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
    public Image loadImage (String filePath) throws Exception {
        return SaveLoadUtility.loadImage(filePath);

    }
}