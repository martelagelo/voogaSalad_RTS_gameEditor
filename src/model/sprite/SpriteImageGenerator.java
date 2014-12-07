package model.sprite;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.exceptions.SaveLoadException;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import util.GameElementType;
import util.GameSaveLoadMediator;
import util.ResourceBundleRetriever;
import util.SaveLoadUtility;
import engine.visuals.elementVisuals.animations.AnimatorState;

public class SpriteImageGenerator {
    private ResourceBundleRetriever myBundleRetriever;
    private ResourceBundle myBundle;
    public static final String RESOURCES_PROPERTIES_LOCATION = "resources/gameelementresources/properties/";
    private String resourceFile = "gameelementresources.properties";
    private Map<String, String> myResourceMapping;
    private static Map<String, SpriteImageContainer> myCachedContainer;
    private ColorMapGenerator myColorMapGenerator;

    public SpriteImageGenerator () throws SaveLoadException {
        myBundleRetriever = new ResourceBundleRetriever();
        myColorMapGenerator = new ColorMapGenerator();
        try {
            myBundle = myBundleRetriever.getBundle(new File(RESOURCES_PROPERTIES_LOCATION
                    + resourceFile));
        } catch (MalformedURLException e) {
            throw new SaveLoadException("Unable to load resources", e);
        }
        myResourceMapping = new HashMap<>();
        myCachedContainer = new HashMap<>();
        populateMap();
    }

    public void populateMap () {
        Enumeration<String> keys = myBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = myBundle.getString(key).trim();
            myResourceMapping.put(key, value);
        }
    }

    public static Map<String, SpriteImageContainer> loadSpriteImageContainers (
            Set<AnimatorState> animatorStates) throws SaveLoadException {
        for (AnimatorState state : animatorStates) {
            myCachedContainer.put(state.getImageTag(),
                    new SpriteImageContainer(state.getImageTag(), state.getColorMaskTag()));
        }
        return myCachedContainer;
    }

    public SpriteImageContainer fetchImageContainer (String imageTag) {
        return myCachedContainer.get(imageTag);
    }

}