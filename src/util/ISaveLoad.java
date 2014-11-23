package util;

import java.io.IOException;

import javafx.scene.image.Image;

/**
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
    public <T> T loadResource (Class className, String filePath) throws Exception;

    /**
     * Save a JSONable object to a library file. Note ".json" extension added
     * internally
     * 
     * @param object
     *            that can be converted to JSON format
     * @param sourcePath
     *            location to which object should be saved
     * @return filePath to which the Json file is saved
     * @throws IOException
     * 
     */
    public String save (JSONable object, String sourcePath) throws IOException;

    /**
     * Save an image from a particular file path
     * 
     * @param image
     *            object that needs to be saved
     * @param location
     *            location to which image is saved
     * @return filePath where the image is saved to
     * @throws IOException
     */

    /**
     * Save an image from a particular file path
     * 
     * @param sourcePath
     *            source location for the image
     * @param destinationPath
     *            destination location for the image
     * @throws IOException
     */
    public void saveImage (String sourcePath, String destinationPath) throws IOException;

    /**
     * 
     * @param filePath
     *            encoding of the file path
     * @return Image object at that file path
     * @throws Exception
     */
    public Image loadImage (String filePath) throws Exception;

}
