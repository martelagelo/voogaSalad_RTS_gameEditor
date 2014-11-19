package gamemodel;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents a bundle of game element info
 * as strings (from the view) to create a game element
 * 
 * @author Jonathan Tseng
 * @author Joshua Miller
 *
 */
public class GameElementInfoBundle {

    private Map<String, String> myStringAttributes = new HashMap<>();
    private Map<String, Number> myNumberAttributes = new HashMap<>();
    private Map<String, String> myTriggers = new HashMap<>();

    public GameElementInfoBundle () {

    }

    public void addTrigger (String condition, String value) {
        myTriggers.put(condition, value);
    }

    public void addAttribute (String key, String value) {
        try {
            myNumberAttributes.put(key, Double.parseDouble(value));
        }
        catch (Exception e) {
            myStringAttributes.put(key, value);
        }
    }

    public Map<String, String> getMyStringAttributes () {
        return myStringAttributes;
    }

    public Map<String, Number> getMyNumberAttributes () {
        return myNumberAttributes;
    }

    public Map<String, String> getMyTriggers () {
        return myTriggers;
    }

}
