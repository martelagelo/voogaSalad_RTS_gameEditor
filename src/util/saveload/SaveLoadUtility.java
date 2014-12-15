// This entire file is part of my masterpiece.
// Rahul Harikrishnan

package util.saveload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import util.JSONable;
import util.exceptions.JSONLoadException;
import util.exceptions.JSONSaveException;
import util.exceptions.LoadImageException;
import util.exceptions.SaveFileException;
import util.exceptions.SaveImageException;
import util.exceptions.SaveLoadException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * Utility class that curently handles the loading/saving of image files as well
 * as JSON files.
 * 
 * @author Rahul
 *
 */

public class SaveLoadUtility implements IGSONable, IImageSaveLoad {
    private static final String DEFAULT_DELIMITER = "_";
    private static final String WHITESPACE = " ";
    private static Gson myGson = new Gson();

    @SuppressWarnings("unchecked")
    @Override
    public <T> T loadResource (Class<?> className, String filePath) throws SaveLoadException {
        T jsonRepresentation = null;
        try {
            jsonRepresentation = (T) myGson.fromJson(new FileReader(new File(filePath)), className);
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            throw new JSONLoadException(e, filePath);

        }
        return jsonRepresentation;
    }

    @Override
    public String saveResource (JSONable jsonableClass, String filePath, String delimiter)
            throws SaveLoadException {
        filePath = processFilePath(filePath, delimiter);
        FileWriter writer = null;
        File file = null;
        try {
            file = obtainFile(filePath);
            writer = new FileWriter(file);
            String json = jsonableClass.toJSON();
            writer.write(json);
            writer.close();
        } catch (IOException | SaveLoadException e) {
            throw new JSONSaveException(e, filePath);
        }

        return file.getPath();
    }

    @Override
    public Image loadImage (String filePath) throws SaveLoadException {
        File imageFile = obtainFile(filePath);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new LoadImageException(e);
        }
        if (bufferedImage != null) {
            WritableImage image = null;
            // Optional second parameter to save pixel data (setting to
            // null)
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            return image;
        }
        throw new SaveLoadException();
    }

    @Override
    public String saveImage (String source, String destination, String defaultDelimiter)
            throws SaveLoadException {
        destination = processFilePath(destination, defaultDelimiter);
        File sourceFile = obtainFile(source);
        File destFile = obtainFile(destination);

        try {
            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new SaveImageException(e, sourceFile.getName(), destFile.getName());
        }
        return destination;
    }

    /**
     * Returns the File object reference at the specified file path. Creates
     * file if currently non-existent and required directories.
     * 
     * @param filePath
     * @return File object at specified file path
     * @throws SaveLoadException
     */
    private File obtainFile (String filePath) throws SaveLoadException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new SaveFileException(e, filePath);
            }
        }
        return file;
    }

    /**
     * Strips the provided file path of whitespaces replacing it with the
     * delimiter provided
     * 
     * @param filePath
     *            file path to file
     * @param delimiter
     *            to be used to replace whitespace
     * @return
     */
    private String processFilePath (String filePath, String delimiter) {
        delimiter = (!delimiter.equals(WHITESPACE)) ? delimiter : DEFAULT_DELIMITER;
        filePath = filePath.replaceAll(WHITESPACE, DEFAULT_DELIMITER);
        return filePath;

    }
}