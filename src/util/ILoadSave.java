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
     * @param filename
     * @return Java class object
     */
    public <T> T loadResource (String filename);

    /**
     * Save a JSONable object to a library file
     * 
     * @param object
     *            that can be converted to JSON format
     * @param fileName
     *            to which object should be saved
     * @throws IOException
     *             in case of trouble reading
     */
    public void save (JSONable object, String fileName) throws IOException;

    /**
     * 
     * @param image
     * @param fileName
     */
    public void saveImage (Image image, String fileName);

    /**
     * 
     * @param filePath
     * @return
     */
    public Image loadImage (String filePath);

}
