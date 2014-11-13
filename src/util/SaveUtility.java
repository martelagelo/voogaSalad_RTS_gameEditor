package util;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Rahul Harikrishnan
 *
 */
public class SaveUtility implements JSONable {

    /**
     * Save a JSONable object to a library file
     * @param object that can be converted to JSON format
     * @return result of save operation (true if successful, else false)
     */
    public boolean save (JSONable object) {
        String json = object.toJSON();
        try {
            FileWriter writer = new FileWriter("c:\\file.json");
            writer.write(json);
            writer.close();
     
        } catch (IOException e) {
            System.out.println("Error Saving");
        }
        return true;
    }
    
    

}
