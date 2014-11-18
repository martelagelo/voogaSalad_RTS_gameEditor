package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.visuals.AnimationPlayer;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Displayable;
import game_engine.visuals.Spritesheet;
import javafx.scene.Node;
import javafx.scene.image.Image;


// TODO comment
/**
 * 
 * @author Zach, Jonathan, Nishad, Rahul
 *
 */
public class DrawableGameElement extends GameElement implements Displayable {

    private DrawableGameElementState myState;
    protected AnimationPlayer myAnimation;

    // TODO: Handle possible class cast exception?
    // factory needs to handle this
    public DrawableGameElement (GameElementState gameElementState) {
        this((DrawableGameElementState) gameElementState);
    }

    public DrawableGameElement (DrawableGameElementState element) {
        super(element);
        myState = element;
        Spritesheet spritesheet = element.getSpritesheet();
        myAnimation =
                new AnimationPlayer(new Image(spritesheet.imageTag), spritesheet.frameDimensions,
                                    spritesheet.numCols);
    }

    @Override
    public void update () {
        super.update();
        // state.update();
        // Use polling because java.util.observable requires inheritance
        // and javafx.beans.observable isn't serializable
        myAnimation.setAnimation(myState.getAnimation());
        myAnimation.update();
    }

    public void setAnimation (AnimationSequence animation) {
        myAnimation.setAnimation(animation);
    }

    public DrawableGameElementState getState () {
        return myState;
    }

    @Override
    public Node getNode () {
        Node n = myAnimation.getNode();
        n.setLayoutX(myState.getNumericalAttribute(DrawableGameElementState.X_POS_STRING)
                .doubleValue());
        n.setLayoutY(myState.getNumericalAttribute(DrawableGameElementState.Y_POS_STRING)
                .doubleValue());
        System.out.println("node layout: " + n.getLayoutX() + ", " + n.getLayoutY());
        return n;
    }

    public double[] getBounds () {
        return myState.getBounds();
    }
}
