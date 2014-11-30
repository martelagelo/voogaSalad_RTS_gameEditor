package game_engine.visuals.elementVisuals.animations;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class SpriteImageContainer {
    
    private Image mySpritesheet;
    private Map<String,Image> myTeamColorMasks;
    
    public SpriteImageContainer(Image spritesheet){
        mySpritesheet = spritesheet;
        myTeamColorMasks = new HashMap<>();
    }

    public Image getSpritesheet () {
        return mySpritesheet;
    }

    public Image getTeamColorSheet (String teamColor) {
        return myTeamColorMasks.getOrDefault(teamColor, myTeamColorMasks.get("BLUE"));
    }

}
