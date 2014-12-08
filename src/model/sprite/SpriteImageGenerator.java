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
 * This class generates and caches sprite image containers that encapsulate a
 * spritesheet and colormask represented in game elements.
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
     * Creates sprite image containers from the provided animator states
     * 
     * @param animatorStates
     *            set of animator states holding a spritesheet and colormask
     * @throws SaveLoadException
     */
    public static void loadSpriteImageContainers (Set<AnimatorState> animatorStates)
            throws SaveLoadException {
        for (AnimatorState state : animatorStates) {
            loadSpriteImageContainer(state);
        }
    }

    /**
     * Creates a sprite image container from an animator state and caches the
     * container.
     * 
     * @param state
     *            animator state holding a spritesheet and colormask
     * @throws SaveLoadException
     */
    public static void loadSpriteImageContainer (AnimatorState state) throws SaveLoadException {
        if (!myCachedContainer.containsKey(state.getImageTag())) {
            myCachedContainer.put(state.getImageTag(), new SpriteImageContainer(
                    state.getImageTag(), state.getColorMaskTag()));
        }
    }

    /**
     * Get a sprite image container from an image tag specifying the location of
     * the spritesheet.
     * 
     * @param imageTag
     *            path to image
     * @return SpriteImageContainer wrapping the spritesheet and colormask
     */
    public SpriteImageContainer fetchImageContainer (String imageTag) {
        return myCachedContainer.get(imageTag);
    }

}