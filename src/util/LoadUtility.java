package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import test.util.TestDescribable;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * Utility class that handles the loading of objects from JSON files.
 * @author Rahul Harikrishnan
 *
 */
public class LoadUtility {

    public TestDescribable loadResource (String filename, Class<? extends TestDescribable> classType) {
        Gson gson = new Gson();
        TestDescribable describable = null;
        try {
            describable =  gson.fromJson(new FileReader(new File("resources/" + filename)), classType);
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) 
        {
            // TODO: Send error in form of request to RequestMediator.
        }
        return describable;
    }      
}
