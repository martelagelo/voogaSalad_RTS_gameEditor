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
public class SaveLoadUtility implements ISaveLoad {
    private static final String IMAGE_NOT_LOADED = "Image could not be loaded";
    public static String FILE_SEPARATOR = System.getProperty("file.separator");

    public SaveLoadUtility () {

    }

    public <T> T loadResource (Class className, String filePath) throws Exception {
        Gson gson = new Gson();
        T jsonRepresentation = (T) gson.fromJson(new FileReader(new File(filePath)), className);
        return jsonRepresentation;
    }

    public String save (JSONable object, String filePath) throws IOException {
        File file = obtainFile(filePath + ".json");
        FileWriter writer = new FileWriter(file);
        String json = object.toJSON();
        //TODO remove print
        System.out.println(json);
        writer.write(json);
        writer.close();
        return file.getPath();
    }

    private File obtainFile (String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            System.out.println(filePath);
            file.createNewFile();
        }
        return file;
    }

    public void saveImage (String source, String destination) throws IOException {
        File sourceFile = obtainFile(source);
        File destFile = obtainFile(destination);
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

    }

    public Image loadImage (String filePath) throws Exception {
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

    private boolean fileExists (String filePath) {
        File file = new File(filePath);
        return file != null;
    }
}
