package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * Utility class that handles the loading/saving of files.
 * 
 * @author Rahul
 *
 */
public class SaveLoadUtility implements ISaveLoad {
    private static final String BAD_FILE_PATH = "Bad File Path";
    public static String FILE_SEPARATOR = System.getProperty("file.separator");
    public static String myDefaultLocation = "resources" + FILE_SEPARATOR;
    public static String myDefaultGameLocation = "myGames" + FILE_SEPARATOR;

    public <T> T loadResource (Class className, String filePath) {
        Gson gson = new Gson();

        T jsonRepresentation = null;
        try {
            jsonRepresentation = (T) gson.fromJson(new FileReader(new File(filePath)), className);
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            // TODO Need to communicate exception to calling class
        }

        return jsonRepresentation;
    }

    public String save (JSONable object, String filePath) throws IOException {

        // TODO: remove duplication here and line 67...
        String[] contents = filePath.split("\\" + FILE_SEPARATOR);
        if (contents.length < 1) {
            throw new IOException(BAD_FILE_PATH);
        }
        String fileName = contents[contents.length - 1];
        File file = obtainFile(myDefaultGameLocation + filePath + FILE_SEPARATOR + fileName + ".json");
        FileWriter writer = new FileWriter(file);
        String json = object.toJSON();
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

    public String saveImage (Image image, String filePath) throws IOException {
        // TODO remove duplication here and line 43
        String[] contents = filePath.split("\\" + FILE_SEPARATOR);
        if (contents.length < 1) {
            throw new IOException(BAD_FILE_PATH);
        }
        String fileName = contents[contents.length - 1];
        File output = obtainFile(myDefaultLocation + filePath + FILE_SEPARATOR +  fileName);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", output);
        return output.getPath();

    }

    public Image loadImage (String filePath) throws IOException {
        // TODO need to figure out a way to take a filePath to load an image.
        BufferedImage bufferedImage = ImageIO.read(new File(filePath));
        WritableImage image = null;
        SwingFXUtils.toFXImage(bufferedImage, image);
        return image;
    }
}
