package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility class that handles the loading/saving of files.
 * 
 * @author Rahul
 *
 */
public class SaveLoadUtility implements ISaveLoad {
    private static final String MALFORMED_FILE_PATH = "Malformed file path";
    private static final String JSON_EXT = ".json";
    private static final String IMAGE_NOT_LOADED = "Image could not be loaded";
    public static String FILE_SEPARATOR = System.getProperty("file.separator");
    private Gson myGson;
    private List<String> mySavedGames;

    public SaveLoadUtility () {
        myGson = new Gson();        
        mySavedGames = new ArrayList<>();

    }

    public <T> T loadResource (Class className, String filePath) throws Exception {
        T jsonRepresentation = (T) myGson.fromJson(new FileReader(new File(filePath)), className);
        return jsonRepresentation;
    }

    public String save (JSONable object, String filePath) throws Exception {
        filePath = preProcess(filePath);
        if (!mySavedGames.contains(filePath)) {
            mySavedGames.add(filePath);
            // setDefaults(topLevelDirectory(filePath));
        }
        File file = obtainFile(filePath);
        FileWriter writer = new FileWriter(file);
        String json = object.toJSON();
        writer.write(json);
        writer.close();
        return file.getPath();
    }

    private String topLevelDirectory (String filePath) throws Exception {
        String contents = filePath.substring(0, filePath.lastIndexOf("\\") + 1);

        return contents;

    }

    private void setDefaults (String filePath) throws Exception {
        File file = new File("src/resources/img");
        File file2 = new File(filePath);
        FileUtils.copyDirectory(file, file2);
        // Files.copy(file.toPath(), file2.toPath(),
        // StandardCopyOption.REPLACE_EXISTING);

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

    public String saveImage (String source, String destination) throws IOException {
        source = preProcess(source);
        destination = preProcess(destination);
        File sourceFile = obtainFile(source);
        File destFile = obtainFile(destination);
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return destination;

    }

    private String preProcess (String source) {
        source = source.replaceAll(" ", "_");
        return source;
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
