package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import test.util.TestCampaign;
import test.util.TestDescribable;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * Utility class that handles the loading of objects from JSON files.
 * @author Rahul
 *
 */
public class LoadUtility implements ILoad {

    public <T> T loadResource (String filename) {
        Gson gson = new Gson();
        
        T jsonRepresentation = null;
            try {
                jsonRepresentation =  (T) gson.fromJson(new FileReader(new File("resources/" + filename)), TestCampaign.class);
            } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
        
        return jsonRepresentation;
    }      
}
