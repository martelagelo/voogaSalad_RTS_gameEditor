package model.sprite;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;

import javafx.scene.image.Image;
import model.GameSaveLoadMediator;
import model.exceptions.SaveLoadException;

import org.apache.commons.io.filefilter.WildcardFileFilter;

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
        File directory = new File(getColorMasksLocation(imageTag));
        FileFilter fileFilter = new WildcardFileFilter(getImageName(imageTag)
                + GameSaveLoadMediator.WILDCARD + GameSaveLoadMediator.PNG_EXT);
        // TODO: need to determine how to get the color from the file
        File[] files = directory.listFiles(fileFilter);
        Image colorMask = null;
        if (files.length > 0) {
            colorMask = SaveLoadUtility.loadImage(files[0].getPath());
        }
        return colorMask;
    }

    public static Image loadSpritesheet (String imageTag) throws SaveLoadException {
        Image image = SaveLoadUtility.loadImage(imageTag);
        return image;
    }

    public static String getColorMasksLocation (String imageTag) {
        // TODO return the path at which the colormasks are located. read from
        // properties file as opposed to hardcoded path here
        // Replace all pipes in image tag with the file separator which varies
        // based on operating system
        String processedTag = processImageTag(imageTag);
        String colorMaskLocation = processedTag.replace(SPRITESHEETS, COLORMASKS);
        int colorMaskDirectory = colorMaskLocation.lastIndexOf(RELATIVE_PATH_DELIMITER);
        // TODO make the return be a default location
        return (colorMaskDirectory > 0) ? colorMaskLocation.substring(0, colorMaskDirectory) : "";
    }

    private static String processImageTag (String imageTag) {
        // Making a copy of the string as a precaution
        String copy = new String(imageTag);
        // Regex limitation in strings
        return copy.replaceAll(ESCAPE_SEQ + TAG_DELIMITER, ESCAPE_SEQ + RELATIVE_PATH_DELIMITER);
    }

    private static String getImageName (String imageTag) {
        // TODO return default image name in case of bad image tag
        String processedTag = processImageTag(imageTag);
        int imageNameIndex = processedTag.lastIndexOf(RELATIVE_PATH_DELIMITER);
        int fileDotIndex = processedTag.lastIndexOf(DOT);
        return ((fileDotIndex > (imageNameIndex + 1)) && (imageNameIndex > 0)) ? processedTag
                .substring(imageNameIndex + 1, fileDotIndex) : "";

    }
}