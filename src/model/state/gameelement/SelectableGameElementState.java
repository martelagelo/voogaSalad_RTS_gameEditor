package model.state.gameelement;

import model.state.gameelement.traits.Sighted;


/**
 * This is the most widely used GameElement. This type of GameElement has both a
 * bounding box and a vision box. This is the element that reacts to collisions
 * with DrawableGameElements.
 *
 * @author Steve, Jonathan, Rahul, Zach
 *
 */
public class SelectableGameElementState extends DrawableGameElementState implements Sighted {

    /**
     * 
     */
    private static final long serialVersionUID = 778064050257391179L;

    private double[] myVisionBounds;

    public SelectableGameElementState (Number xPosition, Number yPosition) {
        super(xPosition, yPosition, null);
    }

    @Override
    public double[] getVisionBounds () {
        // TODO do this
        return null;
        // return visualRepresentation.getVisionBounds();
    }
    public void setVisionBounds(double[] bounds){
        //TODO implement this
    }

}
