package game_engine.gameRepresentation.stateRepresentation.gameElement;

import game_engine.computers.boundsComputer.Boundable;
import game_engine.visuals.AnimationPlayer;
import game_engine.visuals.Spritesheet;
import javafx.geometry.Bounds;
import javafx.scene.Node;


/**
 * This GameElement is drawable but not necessarily selectable - examples include terrain. These
 * elements must have a bounding box.
 * 
 * @author Steve
 *
 */

public class DrawableGameElementState extends GameElementState implements Boundable {

    protected AnimationPlayer visualRepresentation;
    protected Spritesheet spriteSheet;

    public DrawableGameElementState (Number xPosition, Number yPosition) {
        this.numericalAttributes.add(new Attribute<Number>("xPosition", xPosition));
        this.numericalAttributes.add(new Attribute<Number>("yPosition", yPosition));
    }

    @Override
    public Bounds getBounds () {
        return visualRepresentation.getBounds();
    }

    public Node getVisibleRepresentation () {
        // TODO added this getter for testing purposes, really not sure how to actually add a
        // drawable game element to the scene?
        return visualRepresentation.getNode();
    }

}
