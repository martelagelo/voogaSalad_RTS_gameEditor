// This entire file is part of my masterpiece.
// Rahul Harikrishnan
package util.saveload;

import util.JSONable;
import util.exceptions.SaveLoadException;

/**
 * Interface used for loading and saving resources using Google's GSON library.
 * 
 * @author Rahul
 *
 */
public interface IGSONable {
    /**
     * Loads a Java class instance from the JSON representation of the same
     * given path to JSON file
     * 
     * @param className
     *            Java class to be loaded
     * @param filePath
     *            to JSON file
     * @return class instance to be loaded
     * @throws SaveLoadException
     */
    public <T> T loadResource (Class<?> className, String filePath) throws SaveLoadException;

    /**
     * Save a JSON representation of a Java class implementing the JSONable
     * interface at the given file path.
     * 
     * @param jsonableClass
     *            class instance that can be saved
     * @param filePath
     *            preferred location to save the JSON file
     * @param delimiter
     *            delimiter used to replace spaces in provided file path for
     *            cross-platform compatibility
     * @return actual saved location of the file (for compatibility across
     *         different file systems)
     * @throws SaveLoadException
     */
    public String saveResource (JSONable jsonableClass, String filePath, String delimiter)
            throws SaveLoadException;
}
