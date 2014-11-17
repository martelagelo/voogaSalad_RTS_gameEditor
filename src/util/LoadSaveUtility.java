package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

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
public class LoadSaveUtility implements ILoadSave {
    public static String FILE_SEPARATOR = File.separator;

    public <T> T loadResource (Class className, String filePath) {
        Gson gson = new Gson();

        T jsonRepresentation = null;
        try {
            jsonRepresentation = (T) gson.fromJson(new FileReader(new File(filePath)),
                    className);
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            // TODO Need to communicate exception to calling class
        }

        return jsonRepresentation;
    }

    public void save (JSONable object, String filePath) throws IOException {
        String json = object.toJSON();
        FileWriter writer = new FileWriter(filePath);
        writer.write(json);
        writer.close();
    }

    public void saveImage (Image image, String filePath) throws IOException {
        File output = new File(filePath);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", output);
        
    }

    public Image loadImage (String filePath) {
        // TODO need to figure out a way to take a filePath to load an image.
        Image image = new Image(getClass().getResourceAsStream("/resources/img/" + filePath));
        return image;
    }
}
