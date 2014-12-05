package model.sprite;

import java.util.Map;
import javafx.scene.image.Image;

/**
 * Passive data structure that wraps an animation spritesheet with team color
 * masks. Holds a reference to a SaveLoadMediator object allowing for filepath
 * related contents to be concentrated in a single location.
 * 
 * @author Rahul
 *
 */
public class SpriteImageContainer {
    private Image mySpritesheet;
    private Map<String, Image> myTeamColorMasks;

    public SpriteImageContainer (String imageTag) throws Exception {
        locateSpritesheet(imageTag);
        locateTeamColorMasks(imageTag);
    }

    private void locateTeamColorMasks (String imageTag) throws Exception {
        myTeamColorMasks = SpriteImageLoader.loadTeamColorMasks(imageTag);
    }

    private void locateSpritesheet (String imageTag) throws Exception {

        mySpritesheet = SpriteImageLoader.loadSpritesheet(imageTag);
    }

    public Image getSpritesheet () {
        return mySpritesheet;
    }

    public Map<String, Image> getTeamColorMasks () {
        return myTeamColorMasks;
    }

    public Image getTeamColorSheet (String teamColor) {
        return myTeamColorMasks.getOrDefault(teamColor, myTeamColorMasks.get("BLUE"));

    }

}