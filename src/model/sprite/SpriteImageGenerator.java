package model.sprite;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import util.ResourceBundleRetriever;
import util.exceptions.SaveLoadException;
import engine.visuals.elementVisuals.animations.AnimatorState;

/**
 * 
 * @author Rahul
 *
 */
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
        myBundle = myBundleRetriever.getBundle(new File(RESOURCES_PROPERTIES_LOCATION
                + resourceFile));
        myResourceMapping = new HashMap<>();
        myCachedContainer = new HashMap<>();
        populateColorMaskMap();
        populateResourceMap();
    }

    private void populateColorMaskMap () throws SaveLoadException {
        myColorMapGenerator.populateColorMaskMap();

    }

    private void populateResourceMap () {
        Enumeration<String> keys = myBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = myBundle.getString(key).trim();
            myResourceMapping.put(key, value);
        }
    }

    /**
     * 
     * @param animatorStates
     * @return
     * @throws SaveLoadException
     */
    public static Map<String, SpriteImageContainer> loadSpriteImageContainers (
            Set<AnimatorState> animatorStates) throws SaveLoadException {
        for (AnimatorState state : animatorStates) {
            loadSpriteImageContainer(state);
        }
        return myCachedContainer;
    }

    public static void loadSpriteImageContainer (AnimatorState state) throws SaveLoadException {
        if (!myCachedContainer.containsKey(state.getImageTag())) {
            myCachedContainer.put(state.getImageTag(), new SpriteImageContainer(
                    state.getImageTag(), state.getColorMaskTag()));    
        }
    }

    /**
     * 
     * @param imageTag
     * @return
     */
    public SpriteImageContainer fetchImageContainer (String imageTag) {  
        return myCachedContainer.get(imageTag);
    }

}