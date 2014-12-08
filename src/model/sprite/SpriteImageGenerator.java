package model.sprite;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    public SpriteImageGenerator () throws SaveLoadException {
        myColorMapGenerator = new ColorMapGenerator();
        myCachedContainer = new HashMap<>();
        populateColorMaskMap();
    }

    private void populateColorMaskMap () throws SaveLoadException {
        myColorMapGenerator.populateColorMaskMap();

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