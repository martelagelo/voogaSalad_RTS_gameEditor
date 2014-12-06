package model.sprite;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
    private Map<String, SpriteImageContainer> myCachedContainer;
    private ColorMapGenerator myColorMapGenerator;

    public SpriteImageGenerator () throws SaveLoadException {
        myBundleRetriever = new ResourceBundleRetriever();
        myColorMapGenerator = new ColorMapGenerator();
        try {
            myBundle = myBundleRetriever.getBundle(new File(RESOURCES_PROPERTIES_LOCATION + resourceFile));
        } catch (MalformedURLException e) {
            throw new SaveLoadException("Unable to load resources", e);
        }
        myResourceMapping = new HashMap<>();
        myCachedContainer = new HashMap<>();
        populateMap();
        loadSpriteImageContainers();
    }

    public void populateMap () {
        Enumeration<String> keys = myBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = myBundle.getString(key).trim();
            myResourceMapping.put(key, value);
        }
    }

    public Map<String, SpriteImageContainer> loadSpriteImageContainers () throws SaveLoadException {
        String animationStateLocation = myResourceMapping.get(GameElementType.ANIMATORSTATE
                .toString());
        File directory = new File(animationStateLocation);
        FileFilter fileFilter = new WildcardFileFilter(GameSaveLoadMediator.WILDCARD
                + GameSaveLoadMediator.JSON_EXT);
        File[] files = directory.listFiles(fileFilter);
        if (files != null) {
            for (File f : files) {
                AnimatorState state = SaveLoadUtility
                        .loadResource(AnimatorState.class, f.getPath());
                myCachedContainer.put(state.getImageTag(),
                        new SpriteImageContainer(state.getImageTag()));
            }
        }
        return myCachedContainer;
    }

    public SpriteImageContainer fetchImageContainer (String imageTag) {
        return myCachedContainer.get(imageTag);
    }

}