package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import test.util.TestCampaign;
import test.util.TestDescribable;

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
            System.out.println(FILE_SEPARATOR);
            jsonRepresentation = (T) gson.fromJson(new FileReader(new File("src" + FILE_SEPARATOR
                    + "resources" + FILE_SEPARATOR + "game" + FILE_SEPARATOR + filePath)),
                    className.class);
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            // TODO Need to communicate exception to calling class
        }

        return jsonRepresentation;
    }

    public void save (JSONable object, String filePath) throws IOException {
        String json = object.toJSON();
        FileWriter writer = new FileWriter("src" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR
                + "game" + FILE_SEPARATOR + filePath);
        writer.write(json);
        writer.close();
    }

    public void saveImage (Image image, String filePath) {
        File output = new File("src" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "img"
                + FILE_SEPARATOR + filePath);
        try {
            boolean b = ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", output);
            System.out.println(b);

        } catch (Exception s) {
        }
    }

    public Image loadImage (String filePath) {
        Image image = new Image(getClass().getResourceAsStream("/resources/img/" + filePath));
        return image;
    }
}
