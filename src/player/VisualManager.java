package player;

import javafx.scene.Group;

public class VisualManager {
    private ScrollableScene scene;
    private ScrollableBackground background;
    
    public VisualManager(Group gameObjectVisuals, double screenWidth, double screenHeight){
        scene = new ScrollableScene(gameObjectVisuals, screenWidth, screenHeight);
        background = scene.getBackground();
    }
    
    public void update(){
        scene.update();
    }

    public Group getVisualRepresentation () {
        return new Group(background.getChildren());
    }
    
}
