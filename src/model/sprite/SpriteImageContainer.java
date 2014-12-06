package model.sprite;

import javafx.scene.image.ImageView;
import model.exceptions.SaveLoadException;

/**
 * Passive data structure that wraps an animation spritesheet with a color mask
 * for team identification. Holds a reference to a SaveLoadMediator object
 * allowing for filepath related contents to be concentrated in a single
 * location.
 * 
 * @author Rahul
 *
 */
public class SpriteImageContainer {
    private ImageView mySpritesheet;
    private ImageView myColorMask;

    public SpriteImageContainer (String imageTag) throws SaveLoadException {
        locateSpritesheet(imageTag);
        locateTeamColorMasks(imageTag);
    }

    private void locateTeamColorMasks (String imageTag) throws SaveLoadException {
        myColorMask = new ImageView(SpriteImageLoader.loadTeamColorMasks(imageTag));
    }

    private void locateSpritesheet (String imageTag) throws SaveLoadException {
        mySpritesheet = new ImageView(SpriteImageLoader.loadSpritesheet(imageTag));
    }

    public ImageView getSpritesheet () {
        return mySpritesheet;
    }

    public ImageView getColorMask () {
        return myColorMask;
    }
}