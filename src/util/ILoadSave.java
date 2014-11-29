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
     *        Java class object that is being loaded
     * @param filePath
     *        that resource to be loaded from
     * @return Java class object
     */
    public <T> T loadResource (Class className, String filePath);

    /**
     * Save a JSONable object to a library file
     * 
     * @param object
     *        that can be converted to JSON format
     * @param filePath
     *        to which object should be saved
     * @throws IOException
     *         in case of trouble reading
     */
    public void save (JSONable object, String filePath) throws IOException;

    /**
     * Save an image from a particular file path
     * 
     * @param image
     * @param filePath
     *        at which image is located
     * @return filePath where the image is saved to
     * @throws IOException
     */
    public String saveImage (Image image, String filePath) throws IOException;

    /**
     *
     * Load an image at given file path
     * 
     * @param filePath
     *        of image
     * @return Image object loaded if exists
     * @throws IOException
     */
    public Image loadImage (String filePath) throws IOException;

}
