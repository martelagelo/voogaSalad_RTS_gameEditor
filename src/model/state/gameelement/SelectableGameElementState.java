package model.state.gameelement;

import engine.visuals.elementVisuals.animations.AnimatorState;


/**
 * This is the most widely used GameElement. This type of GameElement has both a
 * bounding box and a vision box. This is the element that reacts to collisions
 * with DrawableGameElements.
 *
 * @author Steve
 *
 */
public class SelectableGameElementState extends DrawableGameElementState {
   
    private static final long serialVersionUID = 778064050257391179L;    
    
    public SelectableGameElementState (Number xPosition,
                                       Number yPosition,
                                       AnimatorState animatorState) {
        super(xPosition, yPosition, animatorState);
    }

}
