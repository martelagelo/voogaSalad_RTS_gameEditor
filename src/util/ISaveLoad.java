package util;

import java.io.IOException;

import javafx.scene.image.Image;

/**
 * Interface originally implemented by SaveLoadUtility, but no longer is because
 * it is easier to load and save objects from different classes without having
 * to instantiate a SaveLoadUtility class each time.
 * 
 * @author Rahul
 *
 */
public interface ISaveLoad {

    /**
     * Load a resource from a given file and return Java object representation
     * 
     * @param className
     *            Java class object that is being loaded
     * @param filePath
     *            that resource to be loaded from
     * @return Java class object
     * @throws Exception
     */
    public <T> T loadResource (Class<?> className, String filePath) throws Exception;

    /**
     * Save a JSONable object to a library file. Note ".json" extension added
     * internally
     * 
     * @param object
     *            that can be converted to JSON format
     * @param sourcePath
     *            location to which object should be saved
     * @return filePath to which the JSON file is saved
     * @throws Exception
     * 
     */
    public String save (JSONable object, String sourcePath) throws Exception;

    /**
     * 
     * @param sourcePath
     *            source location for the image
     * @param destinationPath
     *            destination location for the image
     * @return location that image is saved to (may be different to allow for
     *         cross-platform compatibility)
     * @throws IOException
     */
    public String saveImage (String sourcePath, String destinationPath) throws IOException;

    /**
     * 
     * @param filePath
     *            encoding of the file path
     * @return Image object at that file path
     * @throws Exception
     */
    public Image loadImage (String filePath) throws Exception;

}
