package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.visuals.AnimationPlayer;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Displayable;
import game_engine.visuals.Spritesheet;
import javafx.scene.Node;
import javafx.scene.image.Image;


// TODO comment
/**
 * 
 * @author Zach
 *
 */
public class DrawableGameElement implements Displayable {

    private DrawableGameElementState state;
    protected AnimationPlayer myAnimation;

    public DrawableGameElement (DrawableGameElementState element) {
        state = element;
        Spritesheet spritesheet = element.getSpritesheet();
        myAnimation =
                new AnimationPlayer(new Image(spritesheet.imageTag), spritesheet.frameDimensions,
                                    spritesheet.numCols);
    }

    public void updateAnimation () {
        // state.update();
        // Use polling because java.util.observable requires inheritance
        // and javafx.beans.observable isn't serializable
        myAnimation.setAnimation(state.getAnimation());
        myAnimation.update();
    }

    public void setAnimation (AnimationSequence animation) {
        myAnimation.setAnimation(animation);
    }

    public DrawableGameElementState getState () {
        return state;
    }

    @Override
    public Node getNode () {
        Node n = myAnimation.getNode();
        n.setLayoutX(state.getNumericalAttribute(DrawableGameElementState.X_POS_STRING)
                .doubleValue());
        n.setLayoutY(state.getNumericalAttribute(DrawableGameElementState.Y_POS_STRING)
                .doubleValue());
        System.out.println("node layout: " + n.getLayoutX() + ", " + n.getLayoutY());
        return n;
    }
    
    public double[] getBounds(){
        return state.getBounds();
    }
}
