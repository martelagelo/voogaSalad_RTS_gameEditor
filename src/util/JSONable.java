package util;

import com.google.gson.Gson;
/**
 * Utility interface to convert class objects into JSON representations
 * @author Josh
 * @author Rahul
 *
 */
public interface JSONable {
    public default String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}