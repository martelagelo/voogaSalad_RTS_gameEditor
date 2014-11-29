package game_engine.computers.pathingComputers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


/**
 * Implements the A* Search algorithm to figure out a path from a location to an objective.
 * 
 * @author Steve
 *
 */
public class PathingComputer {
    
    private MapGrid grid;

    public PathingComputer (MapGrid grid) {
        this.grid = grid;
    }

    public List<Location> findPath (double xStart, double yStart, double xEnd, double yEnd) {
        return findPath(new Location(xStart,yStart), new Location(xEnd, yEnd));
    }

    private PriorityQueue<Location> frontierLocations;
    private HashMap<Location, Location> previousLocations;
    private HashMap<Location, Double> minimumCosts;
    private Location origin;
    private List<Location> goals;

    /**
     * Naive implementation of heuristic function (for now) assuming constant traversal time for
     * every square, allowing for 8-directional movement and weighting diagonals appropriately.
     * 
     * @param location - starting location
     * @param goal - ending location
     * @return diagonal adjustment to Chebyshev distance
     */
    private double determineHeuristic (Location location) {
        double bestHeuristic = Double.MAX_VALUE;
        for(Location goal : goals){
            double dX = Math.abs(location.myX - goal.myX);
            double dY = Math.abs(location.myY - goal.myY);
            double tempHeuristic = (dX + dY) + (Math.sqrt(2) - 2) * Math.min(dX, dY);
            if(tempHeuristic<bestHeuristic){
                bestHeuristic = tempHeuristic;
            }
        }
        return bestHeuristic;
    }

    /**
     * Naive implementation of distance function allowing for 8-directional movement and weighting
     * diagonals appropriately.
     * 
     * @param from
     * @param to
     * @return
     */
    private double determinePathLength (Location from, Location to) {
        double dX = Math.abs(from.myX - to.myX);
        double dY = Math.abs(from.myY - to.myY);
        return from.movementSpeedScalingFactor*((dX + dY) + (Math.sqrt(2) - 2) * Math.min(dX, dY));
    }
    
    public double determineCost (Location from, Location to){
        double previousDistance = minimumCosts.getOrDefault(from, 0.0);
        double currentDistance = determinePathLength(from, to);
        
        return previousDistance + currentDistance;
    }
    
    public List<Location> findPath (Location from, Location to){
        origin = from;
        goals = grid.getNeighborsForXYCoordinate(to);
        
        frontierLocations = new PriorityQueue<Location>();
        previousLocations = new HashMap<Location,Location>();
        minimumCosts = new HashMap<Location, Double>();
        
        frontierLocations.addAll(grid.getNeighborsForXYCoordinate(from));
        while(!frontierLocations.isEmpty()){
            Location currentLocation = frontierLocations.poll();
            if(goals.contains(currentLocation)){
                List<Location> path = reconstructPath(currentLocation);
                path.add(to);
                return path;
            }
            else {

                for(Location neighbor : grid.getNeighbors(currentLocation)){
                    double newCost = determineCost(currentLocation, neighbor);
                    if(!minimumCosts.containsKey(neighbor) || newCost < minimumCosts.get(neighbor)){
                        minimumCosts.put(neighbor, newCost);
                        neighbor.setPriority(newCost + determineHeuristic(neighbor));
                        frontierLocations.remove(neighbor);
                        frontierLocations.offer(neighbor);
                        previousLocations.put(neighbor, currentLocation);
                    }
                }
            }
        }
        return reconstructPath(origin);
    }
    
    private List<Location> reconstructPath(Location destination){
        List<Location> path = new LinkedList<Location>();
        Location next = destination;
        while(next != null){
            path.add(next);
            if(grid.getNeighborsForXYCoordinate(origin).contains(next)){
                break;
            }
            next = previousLocations.get(next);
        }
        return path;
    }
}
