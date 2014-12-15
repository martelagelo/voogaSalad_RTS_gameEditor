// This entire file is part of my masterpiece.
// Rahul Harikrishnan
package util.saveload;

import javafx.scene.image.Image;
import util.exceptions.SaveLoadException;

/**
 * Interface used for loading and saving image files.
 * 
 * @author Rahul
 *
 */
public interface IImageSaveLoad {

    /**
     * Loads a Java FX image object at a specified file path.
     * 
     * @param filePath
     *            location of image
     * @return Image
     * @throws SaveLoadException
     */
    public Image loadImage (String filePath) throws SaveLoadException;

    /**
     * Save's an image from the source file path to the destination file path
     * 
     * @param source
     *            source location of the image
     * @param destination
     *            desired destination location of the image
     * @param delimiter
     *            delimiter used to replace spaces in provided file path
     * @return actual destination file path of saved image (for compatibility
     *         across different file systems)
     * @throws SaveLoadException
     */
    public String saveImage (String source, String destination, String delimiter)
            throws SaveLoadException;
}
