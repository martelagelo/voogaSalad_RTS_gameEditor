package model;

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

    public void addStringAttribute (String key, String value) {
        myStringAttributes.put(key, value);
    }

    public void addNumberAttribute (String key, double value) {
        myNumberAttributes.put(key, value);
    }

    @Override
    public String toString () {
        StringBuilder s = new StringBuilder();
        for (String k : myTriggers.keySet()) {
            s.append("Trigger - " + k);
            s.append(" : ");
            s.append(myTriggers.get(k));
            s.append("\n");
        }
        for (String k : myStringAttributes.keySet()) {
            s.append("StringAttribute - " + k);
            s.append(" : ");
            s.append(myStringAttributes.get(k));
            s.append("\n");
        }
        for (String k : myNumberAttributes.keySet()) {
            s.append("NumberAttribute - " + k);
            s.append(" : ");
            s.append(myNumberAttributes.get(k));
            s.append("\n");
        }
        return s.toString();
    }

}
