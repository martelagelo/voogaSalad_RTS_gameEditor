package engine.computers.pathingComputers;
/**
 * Simple Point2D clone for use in pathing calculations.
 * 
 * @author Steve, Michael D.
 * @param <T>
 *
 */
public class Location implements Comparable<Location>{
    private double myX;
    private double myY;
    private double myMovementSpeedScalingFactor = 1;
    private double myPriority;
    
    public Location(double x, double y){
        myX = x;
        myY = y;
    }
    
    public void setPriority(double priority){
        this.myPriority = priority;
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

    @Override
    public int compareTo (Location arg0) {
        return (int) (this.myPriority - arg0.myPriority);
    }
    
    public double getX() {
    	return myX;
    }
    
    public double getY() {
    	return myY;
    }
    
    public double getPriority() {
    	return myPriority;
    }
    
    public double getMovementSpeed() {
    	return myMovementSpeedScalingFactor;
    }
}
