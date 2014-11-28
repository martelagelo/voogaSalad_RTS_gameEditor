package game_engine.computers.pathingComputers;

import game_engine.stateManaging.GameElementManager;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;


/**
 * Implments the A* Search algorithm to figure out a path from a location to an objective.
 * 
 * @author Steve
 * @param <T>
 *
 */
public class PathingComputer<T> {

    public static final int TILE_WIDTH = 0;
    public static final int TILE_HEIGHT = 0;
    public static final int WAYPOINT_X_SEPARATION = 0;
    public static final int WAYPOINT_Y_SEPARATION = 0;
    private GameElementManager myManager;
    private PriorityQueue<Path<T>> myPaths;
    private HashMap<Path<T>, Double> minimumDistances;
    private Double lastCost;

    public PathingComputer (GameElementManager manager) {
        myManager = manager;
    }

    public List<Path> findPath (double xStart, double yStart, double xEnd, double yEnd) {
        return findPath(new Location(xStart, yStart), new Location(xEnd, yEnd));
    }

    public List<Path> findPath (Location start, Location end) {
        return null;
    }

    private <T> double calculateCostToLocation (T from, T to) {
        return 0.0;
    }

    private <T> double calculateCostToGoalHeuristic (T from, T to) {
        return 0.0;
    }

    protected <T> Double calculateCost (Path<T> p, T from, T to) {
        double costToPreviousLocation =
                ((p.myPrevious != null) ? p.myPrevious.costToLocation : 0.0);
        double costToLocation = calculateCostToLocation(from, to) + costToPreviousLocation;
        double costToGoalHeuristic = calculateCostToGoalHeuristic(from, to);

        p.costToLocation = costToLocation;
        p.cost = costToLocation + costToGoalHeuristic;

        return p.cost;
    }
}
