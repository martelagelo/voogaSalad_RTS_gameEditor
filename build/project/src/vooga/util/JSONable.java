package vooga.util;

import com.google.gson.Gson;

/**
 * Josh's interface to allow any object to serialize itself into JSON
 * using GSON
 */
public interface JSONable {

    /**
     * Convert an object to its JSON representation using GSON
     * 
     * @return The JSON representation of the object
     */
    public default String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
