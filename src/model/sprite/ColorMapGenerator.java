package model.sprite;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import util.ResourceBundleRetriever;
import util.exceptions.SaveLoadException;

/**
 * 
 * @author Rahul
 *
 */
public class ColorMapGenerator {
    private String COLORMASK_DELIMITER = "-";
    private String RESOURCES_PROPERTIES_LOCATION = "resources/gameelementresources/properties/";
    private String myColorMaskName = "colormasks.properties";
    private static final int RGB_LENGTH = 3;
    private ResourceBundle myBundle;
    private ResourceBundleRetriever myBundleRetriever;
    private static HashMap<String, Paint> myColorMap = new HashMap<>();

    public ColorMapGenerator () throws SaveLoadException {
        myColorMap = new HashMap<>();
        myBundleRetriever = new ResourceBundleRetriever();
        myBundle = myBundleRetriever.getBundle(new File(RESOURCES_PROPERTIES_LOCATION
                + myColorMaskName));
    }

    /**
     * 
     * @throws SaveLoadException
     */
    protected void populateColorMaskMap (ResourceBundle bundle, String valueDelimiter) throws SaveLoadException {

        Enumeration<String> keys = myBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String[] values = myBundle.getString(key).trim().split(COLORMASK_DELIMITER);
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
