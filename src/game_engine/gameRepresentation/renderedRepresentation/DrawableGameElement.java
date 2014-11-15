package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.visuals.AnimationPlayer;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Displayable;
import game_engine.visuals.Spritesheet;
import javafx.scene.Node;
import javafx.scene.image.Image;


//TODO comment
/**
 * 
 * @author Zach
 *
 */
public class DrawableGameElement implements Displayable{

    private DrawableGameElementState state;
    private AnimationPlayer player;

    public DrawableGameElement (DrawableGameElementState element) {
        Spritesheet spritesheet = element.getSpritesheet();
        player = new AnimationPlayer(new Image(spritesheet.imageTag),spritesheet.frameDimensions,spritesheet.numCols);
    }

    public void update () {
        state.update();
        //Use polling because java.util.observable requires inheritance
        //and javafx.beans.observable isn't serializable
        player.setAnimation(state.getAnimation());
        player.update();
    }
    public void setAnimation(AnimationSequence animation){
        player.setAnimation(animation);
    }

    public DrawableGameElementState getState () {
        return state;
    }

    @Override
    public Node getNode () {
        return player.getNode();
    }
}
