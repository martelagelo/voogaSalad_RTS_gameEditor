package game_engine.computers.pathingComputers;

/**
 * Simple linked-list clone for use in pathing calculations.
 * 
 * @author Steve
 * @param <T>
 *
 */
public abstract class Path<T> implements Comparable<T> {
    public T lastLocation;
    public Path<T> myPrevious;
    public double costToLocation; // analogous to "g"
    public double costToGoalHeuristic; // analogous to "h"
    public double cost; // analogous to "f"

    public Path (T loc) {
        lastLocation = loc;
        myPrevious = null;
        costToLocation = 0;
        cost = 0;
    }

    public Path (T loc, Path<T> previous) {
        this(loc);
        myPrevious = previous;
    }

    @Override
    public int compareTo (T arg0) {
        Path<T> otherPath = (Path<T>) arg0;
        return (int) (this.cost - otherPath.cost);
    }

}
