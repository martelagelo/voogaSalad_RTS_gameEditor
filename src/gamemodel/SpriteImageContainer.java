package gamemodel;

import java.util.Map;

import util.SaveLoadMediator;
import javafx.scene.image.Image;

/**
 * Passive data structure that wraps an animation spritesheet with team color
 * masks.
 * 
 * @author Rahul
 *
 */
public class SpriteImageContainer {
    private Image mySpritesheet;
    private Map<String, Image> myTeamColorMasks;
    private SaveLoadMediator mySaveLoadMediator;

    public SpriteImageContainer (String imageTag) throws Exception {
        locateSpritesheet(imageTag);
        locateTeamColorMasks(imageTag);

    }

    private void locateTeamColorMasks (String imageTag) {
        myTeamColorMasks = mySaveLoadMediator.locateTeamColorMasks(imageTag);
    }

    private void locateSpritesheet (String imageTag) throws Exception {
        mySaveLoadMediator.loadSpritesheet(imageTag);
    }

    public Image getSpritesheet () {
        return mySpritesheet;
    }

    public Image getTeamColorSheet (String teamColor) {
        return myTeamColorMasks.getOrDefault(teamColor, myTeamColorMasks.get("BLUE"));

    }

}
