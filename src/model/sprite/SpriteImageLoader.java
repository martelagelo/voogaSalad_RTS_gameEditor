package model.sprite;

import javafx.scene.image.Image;
import model.exceptions.SaveLoadException;
import util.SaveLoadUtility;

/**
 * 
 * @author Rahul
 *
 */
public class SpriteImageLoader {
    private static final String DOT = ".";
    private static final String RELATIVE_PATH_DELIMITER = "/";
    public static final String TAG_DELIMITER = "|";
    public static final String ESCAPE_SEQ = "\\";
    public static final String COLORMASKS = "colormasks";
    public static final String SPRITESHEETS = "spritesheets";
    public static final String DEFAULT_COLOR = "BLUE";

    public static Image loadTeamColorMasks (String imageTag) throws SaveLoadException {
        Image colorMask = SaveLoadUtility.loadImage(imageTag);
        return colorMask;
    }

    public static Image loadSpritesheet (String imageTag) throws SaveLoadException {
        Image spritesheet = SaveLoadUtility.loadImage(imageTag);
        return spritesheet;
    }
}