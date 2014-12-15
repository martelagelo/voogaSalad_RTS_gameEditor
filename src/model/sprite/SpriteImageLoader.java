package model.sprite;

import javafx.scene.image.Image;
import util.exceptions.SaveLoadException;
import util.saveload.SaveLoadUtility;

/**
 * This class uses the mediator pattern to connect the SpriteImageGenerator with
 * the SaveLoadUtility. This adds another layer of indirection and allows for
 * all loading and saving of spritesheets and colormasks to pass through this
 * module.
 * 
 * @author Rahul
 *
 */
public class SpriteImageLoader {
    private static final String DEFAULT_COLORMASK = "resources/gameelementresources/default.png";
    private static SaveLoadUtility mySaveLoadUtility = new SaveLoadUtility();

    /**
     * Loads the image of the color mask at the specified file path
     * 
     * @param imageTag
     *            file path to image
     * @return image at the specified path
     * @throws SaveLoadException
     */
    public static Image loadTeamColorMask (String imageTag) throws SaveLoadException {
        return (!imageTag.isEmpty()) ? mySaveLoadUtility.loadImage(imageTag) : mySaveLoadUtility
                .loadImage(DEFAULT_COLORMASK);
    }

    /**
     * Loads the image of the spritesheet at the specified file path
     * 
     * @param imageTag
     *            file path to image
     * @return image at the specified path
     * @throws SaveLoadException
     */
    public static Image loadSpritesheet (String imageTag) throws SaveLoadException {
        Image spritesheet = mySaveLoadUtility.loadImage(imageTag);
        return spritesheet;
    }
}