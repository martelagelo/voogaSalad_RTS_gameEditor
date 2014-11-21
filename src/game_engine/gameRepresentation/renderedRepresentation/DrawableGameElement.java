package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.visuals.AnimationPlayer;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Displayable;
import game_engine.visuals.Spritesheet;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;


// TODO comment
/**
 * 
 * @author Zach, Jonathan, Nishad, Rahul, John L.
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
        n.setTranslateX(-myAnimation.getDimension().getWidth()/2);
        n.setTranslateY(-myAnimation.getDimension().getHeight()/2);
        return n;
    }

    public double[] getBounds () {
        return myState.getBounds();
    }
    
    public void setLocation(Point2D location){
        Node n = myAnimation.getNode();
        n.setLayoutX(location.getX());
        myState.setNumericalAttribute(DrawableGameElementState.X_POS_STRING, location.getX());
        n.setLayoutY(location.getY());
        myState.setNumericalAttribute(DrawableGameElementState.Y_POS_STRING, location.getY());
    }
    
    public Point2D getLocation(){
        Point2D p = new Point2D(myAnimation.getNode().getLayoutX(), myAnimation.getNode().getLayoutY());
        return p;
    }
}
