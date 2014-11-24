package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * Utility interface to convert class objects into JSON representations
 * @author Josh
 * @author Rahul
 *
 */
public interface JSONable {
    public default String toJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
