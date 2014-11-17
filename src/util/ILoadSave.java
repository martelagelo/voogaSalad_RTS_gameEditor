package util;

import java.io.IOException;

import javafx.scene.image.Image;

/**
 * 
 * @author Rahul
 *
 */
public interface ILoadSave {

    /**
     * Load a resource from a given file and return Java object representation
     * 
     * @param className
     * @param filePath
     * @return
     */
    public <T> T loadResource (Class className, String filePath);

    /**
     * Save a JSONable object to a library file
     * 
     * @param object
     *            that can be converted to JSON format
     * @param filePath
     *            to which object should be saved
     * @throws IOException
     *             in case of trouble reading
     */
    public void save (JSONable object, String filePath) throws IOException;

    /**
     * 
     * @param image
     * @param filePath
     */
    public void saveImage (Image image, String filePath);

    /**
     * 
     * @param filePath
     * @return
     */
    public Image loadImage (String filePath);

}
