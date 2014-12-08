package model.sprite;

import javafx.scene.image.Image;
import util.SaveLoadUtility;
import util.exceptions.SaveLoadException;

/**
 * 
 * @author Rahul
 *
 */
public class SpriteImageLoader {
    private static final String DEFAULT_COLORMASK = "resources/gameelementresources/default.png";

    public static Image loadTeamColorMask (String imageTag) throws SaveLoadException {
        return (!imageTag.isEmpty()) ? SaveLoadUtility.loadImage(imageTag) : SaveLoadUtility
                .loadImage(DEFAULT_COLORMASK);

    }

    public static Image loadSpritesheet (String imageTag) throws SaveLoadException {
        Image spritesheet = SaveLoadUtility.loadImage(imageTag);
        return spritesheet;
    }
}