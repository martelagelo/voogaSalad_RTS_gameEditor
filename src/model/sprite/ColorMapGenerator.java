package model.sprite;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.exceptions.SaveLoadException;
import util.ResourceBundleRetriever;

public class ColorMapGenerator {
    private static final String COLOR_MASKS_LOCATION = "resources/gameelementresources/colormasks.properties";
    private static final int RGB_LENGTH = 3;
    private ResourceBundle myBundle;
    private ResourceBundleRetriever myBundleRetriever;
    private static HashMap<String, Paint> myColorMap;

    public ColorMapGenerator () throws SaveLoadException {
        myColorMap = new HashMap<>();
        myBundleRetriever = new ResourceBundleRetriever();
        try {
            //TODO: save the location outside of source
            myBundle = myBundleRetriever.getBundle(new File(COLOR_MASKS_LOCATION));
            populateColorMaskMap();
        } catch (MalformedURLException e) {
            throw new SaveLoadException("Unable to load resources", e);
        }

    }

    private void populateColorMaskMap () throws SaveLoadException {

        Enumeration<String> keys = myBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String[] values = myBundle.getString(key).trim().split("-");
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

    public static Paint getColorMask (String color) {
        return myColorMap.get(color);
    }

}
