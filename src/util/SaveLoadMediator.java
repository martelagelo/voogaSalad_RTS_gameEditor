package util;

import editor.wizards.WizardData;
import editor.wizards.WizardDataType;
import game_engine.gameRepresentation.stateRepresentation.GameState;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    private static final String RESOURCES = "resources";
    private static final String GAME_ELEMENT_RESOURCES = "gameelementresources";
    private static final String PNG_EXT = ".png";
    private static final String JSON_EXT = ".json";
    private String mySpritesheetDirectory;
    private String mySpritesheetName;

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
                + File.separator + data.getValueByKey(WizardDataType.NAME) + PNG_EXT);

    }

    private String getGameLocation (String name) {
        return "myGames" + File.separator + name + File.separator + name + JSON_EXT;
    }

    private String getSpritesheetLocation (String imageTag) {
        // TODO return the path at which spritesheets are saved to and loaded
        // from. read from properties file as opposed to hardcode path here

        int spritesheetIndex = imageTag.lastIndexOf(File.separator);

        mySpritesheetDirectory = (spritesheetIndex > 0) ? imageTag.substring(0, spritesheetIndex)
                : "";
        mySpritesheetName = (spritesheetIndex > 0) ? imageTag.substring(spritesheetIndex + 1) : "";

        return RESOURCES + File.separator + GAME_ELEMENT_RESOURCES + File.separator
                + mySpritesheetDirectory + File.separator + "spritesheets" + File.separator
                + mySpritesheetName;
    }

    private String getColorMasksLocation (String imageTag) {
        // TODO return the path at which the colormasks are located. read from
        // properties file as opposed to hardcoded path here
        return RESOURCES + File.separator + GAME_ELEMENT_RESOURCES + mySpritesheetDirectory
                + File.separator + "colormasks" + File.separator + mySpritesheetName;
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

    /**
     * 
     * @param imageTag
     * @return
     * @throws Exception
     */
    public Image loadSpritesheet (String imageTag) throws Exception {
        return SaveLoadUtility.loadImage(getSpritesheetLocation(imageTag));
    }

    /**
     * 
     * @param imageTag
     * @return
     */
    public Map<String, Image> locateTeamColorMasks (String imageTag) {
        Map<String, Image> colorMasks = new HashMap<>();
        File directory = new File(getColorMasksLocation(imageTag));
        FileFilter fileFilter = new WildcardFileFilter(imageTag + "*" + PNG_EXT);
        // TODO: need to determine how to get the color from the file
        File[] files = directory.listFiles(fileFilter);
        for (File f : files) {
            try {
                colorMasks.put(getColor(f.getName()), SaveLoadUtility.loadImage(f.getPath()));
            } catch (Exception e) {
                // TODO eliminate print stack trace
                e.printStackTrace();
            }
        }
        return colorMasks;
    }

    private String getColor (String fileName) {
        // Color mask file of format archer_red.png
        int colorIndex = fileName.lastIndexOf("_");
        int fileExtensionIndex = fileName.lastIndexOf(".");
        return (colorIndex < fileExtensionIndex) ? fileName.substring(colorIndex,
                fileExtensionIndex) : "blue";
    }

}
