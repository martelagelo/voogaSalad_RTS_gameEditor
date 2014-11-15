package util;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class handling saving a JSONable object.
 * @author Rahul
 *
 */
public class SaveUtility implements ISave {
    /**
     * Save a JSONable object to a library file
     * @param object that can be converted to JSON format
     * @param filename to which object should be saved 
     * @throws IOException in case of trouble reading
     */
    public void save (JSONable object, String filename) throws IOException {
        String json = object.toJSON();
        FileWriter writer = new FileWriter("resources/" + filename);
        writer.write(json);
        writer.close();
    }
}
