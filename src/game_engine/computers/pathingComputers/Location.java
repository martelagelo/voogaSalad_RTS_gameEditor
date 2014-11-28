package game_engine.computers.pathingComputers;
/**
 * Simple Point2D clone for use in pathing calculations.
 * 
 * @author Steve
 * @param <T>
 *
 */
public class Location {
    public double myX;
    public double myY;
    
    public Location(double x, double y){
        myX = x;
        myY = y;
    }
    
    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Location))
            return false;
        else {
            Location otherNode = (Location) o;
            if (myX == otherNode.myX) {
                if (myY == otherNode.myY)
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toString () {
        return "(" + myX + ", " + myY + ")";
    }
}
