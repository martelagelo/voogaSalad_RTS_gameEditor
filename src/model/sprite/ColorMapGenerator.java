package model.sprite;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import util.exceptions.SaveLoadException;

/**
 * 
 * @author Rahul
 *
 */
public class ColorMapGenerator {
    
    private static int RGB_LENGTH = 3;
    private static HashMap<String, Paint> myColorMap = new HashMap<>();

  /**
   * 
   * @param bundle
   * @param valueDelimiter
   * @throws SaveLoadException
   */
    protected void populateColorMaskMap (ResourceBundle bundle, String valueDelimiter) throws SaveLoadException {

        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String[] values = bundle.getString(key).trim().split(valueDelimiter);
            int[] rgbValues = new int[RGB_LENGTH];
            if (values.length < RGB_LENGTH) {
                throw new SaveLoadException("Misformatted sprite color file", new Exception());
            }
            for (int i = 0; i < RGB_LENGTH; i++) {
                try {
                    rgbValues[i] = Integer.parseInt(values[i]);
                } catch (NumberFormatException e) {
                    throw new SaveLoadException("RGB values not specified as numbers", e);
                }
            }
            myColorMap.put(key.toUpperCase(), Color.rgb(rgbValues[0], rgbValues[1], rgbValues[2]));

        }
    }

    /**
     * 
     * @param color
     * @return
     */
    public static Paint getColorMask (String color) {
        return myColorMap.get(color);
    }

}
