package util;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class handling saving a JSONable object.
 * @author Rahul Harikrishnan
 *
 */
public class SaveUtility {

    /**
     * Save a JSONable object to a library file
     * @param object that can be converted to JSON format
     * @return result of save operation (true if successful, else false)
     */
    public void save (JSONable object, String filename) throws IOException {
        String json = object.toJSON();
        FileWriter writer = new FileWriter("resources/" + filename);
        writer.write(json);
        writer.close();
    }
}
