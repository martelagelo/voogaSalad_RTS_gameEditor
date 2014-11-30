package util;

import editor.wizards.WizardData;
import editor.wizards.WizardDataType;
import game_engine.gameRepresentation.stateRepresentation.GameState;

import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;

/**
 * 
 * @author Rahul
 *
 */
public class SaveLoadManager {
    private static final String PNG_EXT = ".png";
    private static final String JSON_EXT = ".json";

    /**
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
    public <T> T loadGame (String filePath) throws Exception {
        return SaveLoadUtility.loadResource(GameState.class, getGameSaveLocation(filePath));

    }

    /**
     * 
     * @param gameState
     * @param filePath
     * @return
     * @throws Exception
     */
    public String saveGame (JSONable gameState, String filePath) throws Exception {
        return SaveLoadUtility.save(gameState, getGameSaveLocation(filePath));

    }

    /**
     * 
     * @param data
     * @return
     * @throws IOException
     */
    public String saveImage (WizardData data) throws IOException {
        // TODO: Remove this hardcoded save location
        String saveLocation = "testSpritesheet";

        return SaveLoadUtility.saveImage(data.getValueByKey(WizardDataType.IMAGE), saveLocation
                + System.getProperty("file.separator") + data.getValueByKey(WizardDataType.NAME)
                + PNG_EXT);

    }

    private String getGameSaveLocation (String name) {
        return "myGames" + File.separator + name + File.separator + name + JSON_EXT;
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
