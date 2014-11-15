package game_engine.gameRepresentation.stateRepresentation.gameElement;

import game_engine.computers.boundsComputer.Boundable;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Spritesheet;
import java.util.HashMap;
import java.util.Map;
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
    private Spritesheet mySpritesheet;
    private Map<String, AnimationSequence> myAnimations;
    private AnimationSequence myAnimation;

    public DrawableGameElementState (Number xPosition, Number yPosition) {
        this.numericalAttributes.add(new Attribute<Number>("xPosition", xPosition));
        this.numericalAttributes.add(new Attribute<Number>("yPosition", yPosition));
        myAnimations = new HashMap<>();
    }
    public void addAnimation(AnimationSequence animation){
        myAnimations.put(animation.toString(), animation);
    }

    public AnimationSequence getAnimation(){
        return myAnimation;
    }
    public void setSpritesheet(Spritesheet spritesheet){
        mySpritesheet = spritesheet;
    }
    public Spritesheet getSpritesheet(){
        return mySpritesheet;
    }
    @Override
    public Bounds getBounds () {
        //TODO fix
        return visualRepresentation.getBounds();
    }

    public Node getVisibleRepresentation () {
        // TODO added this getter for testing purposes, really not sure how to actually add a
        // drawable game element to the scene?
        return visualRepresentation.getNode();
    }

}
