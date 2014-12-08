package model.sprite;

import java.io.File;
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
    private static Map<String, SpriteImageContainer> myCachedContainer;
    private ColorMapGenerator myColorMapGenerator;
    private String myColorMaskDelimiter = "-";
    private String myPropertiesLocation = "resources/gameelementresources/properties/";
    private String myColorMaskName = "colormasks.properties";
    private ResourceBundle myBundle;
    private ResourceBundleRetriever myBundleRetriever;

    public SpriteImageGenerator () throws SaveLoadException {
        myCachedContainer = new HashMap<>();
        myColorMapGenerator = new ColorMapGenerator();
        myBundleRetriever = new ResourceBundleRetriever();
        myBundle = myBundleRetriever.getBundle(new File(myPropertiesLocation + myColorMaskName));
        populateColorMaskMap(myBundle, myColorMaskDelimiter);
    }

    private void populateColorMaskMap (ResourceBundle bundle, String colorMaskDelimiter)
            throws SaveLoadException {
        myColorMapGenerator.populateColorMaskMap(bundle, colorMaskDelimiter);

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

    /**
     * 
     * @param state
     * @throws SaveLoadException
     */
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