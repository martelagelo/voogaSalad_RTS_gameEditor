package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

/**
 * Utility class that handles the loading/saving of files.
 * 
 * @author Rahul
 *
 */

public class SaveLoadUtility {
    private static final String IMAGE_NOT_LOADED = "Image could not be loaded";
    public static String FILE_SEPARATOR = System.getProperty("file.separator");
    private static DefaultResource myDefaultResource = new DefaultResource();
    private static Gson myGson = new Gson();

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
    public static <T> T loadResource (Class<?> className, String filePath) throws Exception {
        T jsonRepresentation = (T) myGson.fromJson(new FileReader(new File(filePath)), className);
        return jsonRepresentation;
    }

    /**
     * Save a JSONable object to a library file.
     * 
     * @param object
     *            that can be converted to JSON format
     * @param sourcePath
     *            location to which object should be saved
     * @return filePath to which the JSON file is saved
     * @throws Exception
     * 
     */
    public static String save (JSONable object, String filePath) throws Exception {
        filePath = preProcess(filePath);
        myDefaultResource.setDefaults((topLevelDirectory(filePath)));
        File file = obtainFile(filePath);
        FileWriter writer = new FileWriter(file);
        String json = object.toJSON();
        writer.write(json);
        writer.close();
        return file.getPath();
    }

    private static String topLevelDirectory (String filePath) throws Exception {
        String contents = filePath.substring(0, filePath.lastIndexOf("\\") + 1);
        return contents;
    }

    private static File obtainFile (String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            System.out.println(filePath);
            file.createNewFile();
        }
        return file;
    }

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
    public static String saveImage (String source, String destination) throws IOException {
        destination = preProcess(destination);
        File sourceFile = obtainFile(source);
        File destFile = obtainFile(destination);
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return destination;
    }

    private static String preProcess (String source) {
        source = source.replaceAll(" ", "_");
        return source;
    }

    /**
     * 
     * @param filePath
     *            encoding of the file path
     * @return Image object at that file path
     * @throws Exception
     */
    public static Image loadImage (String filePath) throws Exception {
        if (fileExists(filePath)) {
            File imageFile = obtainFile(filePath);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            if (bufferedImage != null) {
                WritableImage image = null;
                // Optional second parameter to save pixel data (setting to
                // null)
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                return image;
            }
        }
        throw new Exception(IMAGE_NOT_LOADED);
    }

    private static boolean fileExists (String filePath) {
        File file = new File(filePath);
        return file != null;
    }
}
