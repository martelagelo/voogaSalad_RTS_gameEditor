package model.sprite;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import util.exceptions.SaveLoadException;

/**
 * Passive data structure that wraps an animation spritesheet with a color mask
 * for team identification. This is given to the Animator allowing for access to
 * the spritsheet and color mask fundamental to visual rendering of game
 * elements.
 * 
 * @author Rahul
 *
 */
public class SpriteImageContainer {
    private ImageView mySpritesheet;
    private ImageView myColorMask;

    public SpriteImageContainer (ImageView spritesheet, ImageView colorMask) {
        mySpritesheet = spritesheet;
        myColorMask = colorMask;
    }

    public SpriteImageContainer (String spritesheetTag, String colorMaskTag)
            throws SaveLoadException {
        locateSpritesheet(spritesheetTag);
        locateColorMask(colorMaskTag);
    }

    private void locateColorMask (String colorMaskTag) throws SaveLoadException {
        myColorMask = new ImageView(SpriteImageLoader.loadTeamColorMask(colorMaskTag));
    }

    private void locateSpritesheet (String spritesheetTag) throws SaveLoadException {
        mySpritesheet = new ImageView(SpriteImageLoader.loadSpritesheet(spritesheetTag));
    }

    public ImageView getSpritesheet () {
        return mySpritesheet;
    }

    public ImageView getColorMask () {
        return myColorMask;
    }

    /**
     * 
     * @param color
     * @return
     */
    public ImageView getColorMask (long color) {
        if(myColorMask.getImage() == null) return new ImageView();
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(0.0);
        Blend blush = new Blend(BlendMode.SRC_ATOP, monochrome, new ColorInput(0, 0, myColorMask
                .getImage().getWidth(), myColorMask.getImage().getHeight(), ColorMapGenerator.colorFromLong(color)));
        
        myColorMask.setEffect((Effect) blush);
        
        return myColorMask;
    }
}