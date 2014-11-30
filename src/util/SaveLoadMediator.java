package util;

import editor.wizards.WizardData;
import editor.wizards.WizardDataType;
import game_engine.gameRepresentation.stateRepresentation.GameState;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import javafx.scene.image.Image;

/**
 * This class mediates between the Model and the SaveLoadUtility. Using the
 * composition pattern, MainModel holds a reference to this class allowing us to
 * consolidate all file path related function calls in one place and
 * specifically away from the model.
 * 
 * @author Rahul
 *
 */
public class SaveLoadMediator {
    private static final String PNG_EXT = ".png";
    private static final String JSON_EXT = ".json";

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
    public String saveGame (JSONable gameState, String gameName) throws Exception {
        return SaveLoadUtility.save(gameState, getGameLocation(gameName));

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

    private String getGameLocation (String name) {
        return "myGames" + File.separator + name + File.separator + name + JSON_EXT;
    }
    
    private String getSpritesheetLocation(String imageTag) {
        //TODO return the path at which spritesheets are saved to and loaded from.
        return "";
    }
    
    private String getColorMasksLocation(String imageTag) {
        //TODO return the path at which the colormasks are located.
        return "";
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

    public Image loadSpritesheet (String imageTag) throws Exception {
        return SaveLoadUtility.loadImage(getSpritesheetLocation(imageTag));
    }

    public void locateTeamColorMasks (String imageTag) {
        File directory = new File(getColorMasksLocation(imageTag));
        FileFilter fileFilter = new WildcardFileFilter(imageTag + "*" + PNG_EXT);
        // TODO: need to determine how to get the color from the file
        File[] files = directory.listFiles(fileFilter);
    }

}
