package engine.visuals;

import java.io.Serializable;

/**
 *
 * Very simple class that holds dimensions for an object. This was created to
 * avoid using the unserializable Point2D from JavaFX. A data wrapping class
 * that contains no logic of its own.
 *
 * @author Steve, Zach
 *
 */
public class Dimension implements Serializable {
    private static final long serialVersionUID = 519246965846726853L;
    private int myWidth;
    private int myHeight;

    public Dimension (int row, int col) {
        myWidth = row;
        myHeight = col;
    }

    public int getWidth () {
        return myWidth;
    }

    public int getHeight () {
        return myHeight;
    }

    public Dimension addToFormNewDimension (Dimension dimension) {
        return new Dimension(myWidth + dimension.myWidth, myHeight + dimension.myHeight);
    }

    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Dimension)) {
            return false;
        } else {
            Dimension otherDim = (Dimension) o;
            if (myWidth == otherDim.myWidth) {
                if (myHeight == otherDim.myHeight) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString () {
        return "(" + myWidth + ", " + myHeight + ")";
    }
}
