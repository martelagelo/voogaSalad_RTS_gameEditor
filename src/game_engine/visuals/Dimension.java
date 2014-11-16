package game_engine.visuals;

/**
 *
 * Very simple class that holds dimensions for an object. This was created to avoid using the
 * unserializable Point2D from JavaFX
 *
 * @author Steve, Zach
 *
 */
public class Dimension {
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
    
    public Dimension addToFormNewDimension(Dimension dimension){
        return new Dimension(myWidth+dimension.myWidth,myHeight+dimension.myHeight);
    }

    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Dimension))
            return false;
        else {
            Dimension otherDim = (Dimension) o;
            if (myWidth == otherDim.myWidth) {
                if (myHeight == otherDim.myHeight) return true;
            }
        }
        return false;
    }
}
