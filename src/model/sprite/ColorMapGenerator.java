package model.sprite;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import util.exceptions.SaveLoadException;

/**
 * This class generates all color masks that can be applied to a spritesheet.
 * The color masks are cached upon loading, and can be retrieved with the string
 * representation of the color.
 * 
 * @author Rahul
 *
 */
public class ColorMapGenerator {

    private static int RGB_LENGTH = 3;
    private static Map<String, Paint> myColorMap = new HashMap<>();   
    
    public static Color colorFromLong(long colorValue) {
        String colorHexValue= "000000" + Long.toHexString(colorValue);
        String colorString = String.format("#%s", colorHexValue.substring(colorHexValue.length() - 6));
        return Color.web(colorString);
    }

    /**
     * Generates a map of string of colors to JavaFX paint from a resource
     * bundle whose values (RGB) are separated by the provided delimiter
     * 
     * @param bundle
     *            resource bundle consisting of color keys and rgb integer
     *            values (separated by valueDelimiter)
     * @param valueDelimiter
     *            delimiter separating rgb integer values
     * 
     * @return Map of String colors in resource bundle to JavaFX paint objects
     * @throws SaveLoadException
     */
    protected Map<String, Paint> populateColorMaskMap (ResourceBundle bundle, String valueDelimiter)
            throws SaveLoadException {

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
        return myColorMap;
    }

    /**
     * Retrieves the JavaFX paint object instance of the provided string color
     * representation
     * 
     * @param color
     *            String representation of the color
     * @return Paint object equivalent to the color passed in
     */
    public static Paint getColorMask (String color) {
        return myColorMap.get(color);
    }
}
