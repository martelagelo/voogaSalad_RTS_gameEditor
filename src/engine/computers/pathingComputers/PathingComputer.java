package engine.computers.pathingComputers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Implements the A* Search algorithm to figure out a path from a location to an
 * objective.
 * 
 * @author Steve
 *
 */
public class PathingComputer {

	private MapGrid myGrid;

	public PathingComputer(MapGrid grid) {
		this.myGrid = grid;
	}

	public List<Location> findPath(Location from, Location to) {
		List<Location> rawPath = computePath(from, to);
		return optimizePath(rawPath);
	}

	private List<Location> optimizePath(List<Location> rawPath) {
		List<Location> raw = new ArrayList<>(rawPath);
		for (int i = 0; i < raw.size() - 2; i++) {
			if (pointsAreColinear(raw.get(i), raw.get(i + 1), raw.get(i + 2))) {
				raw.remove(i + 1);
				i--;
			}
		}
		return raw;
	}

	private boolean pointsAreColinear(Location first, Location second, Location third) {
		double x0 = first.getX();
		double y0 = first.getY();
		double x1 = second.getX();
		double y1 = second.getY();
		double x2 = third.getX();
		double y2 = third.getY();

		return Math.ulp((y1 - y0) - (y2 - y0) / (x2 - x0) * (x1 - x0)) < 10;
	}

	private PriorityQueue<Location> frontierLocations;
	private HashMap<Location, Location> previousLocations;
	private HashMap<Location, Double> minimumCosts;
	private Location origin;
	private List<Location> goals;

	/**
	 * Naive implementation of heuristic function (for now) assuming constant
	 * traversal time for every square, allowing for 8-directional movement and
	 * weighting diagonals appropriately.
	 * 
	 * @param location
	 *            - starting location
	 * @param goal
	 *            - ending location
	 * @return diagonal adjustment to Chebyshev distance
	 */
	private double determineHeuristic(Location location) {
		double bestHeuristic = Double.MAX_VALUE;
		for (Location goal : goals) {
			double dX = Math.abs(location.getX() - goal.getX());
			double dY = Math.abs(location.getY() - goal.getY());
			double tempHeuristic = (dX + dY) + (Math.sqrt(2) - 2) * Math.min(dX, dY);
			if (tempHeuristic < bestHeuristic) {
				bestHeuristic = tempHeuristic;
			}
		}
		return bestHeuristic;
	}

	/**
	 * Naive implementation of distance function allowing for 8-directional
	 * movement and weighting diagonals appropriately.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	private double determinePathLength(Location from, Location to) {
		double dX = Math.abs(from.getX() - to.getX());
		double dY = Math.abs(from.getY() - to.getY());
		return from.getMovementSpeed() * ((dX + dY) + (Math.sqrt(2) - 2) * Math.min(dX, dY));
	}

	public double determineCost(Location from, Location to) {
		double previousDistance = minimumCosts.getOrDefault(from, 0.0);
		double currentDistance = determinePathLength(from, to);

		return previousDistance + currentDistance;
	}

	private List<Location> computePath(Location from, Location to) {
		origin = from;
		goals = myGrid.getNeighborsForXYCoordinate(to);

		frontierLocations = new PriorityQueue<Location>();
		previousLocations = new HashMap<Location, Location>();
		minimumCosts = new HashMap<Location, Double>();

		frontierLocations.addAll(myGrid.getNeighborsForXYCoordinate(from));
		while (!frontierLocations.isEmpty()) {
			Location currentLocation = frontierLocations.poll();
			if (goals.contains(currentLocation)) {
				List<Location> path = reconstructPath(currentLocation);
				path.add(0, to);
				Collections.reverse(path);
				return path;
			} else {

				for (Location neighbor : myGrid.getNeighbors(currentLocation)) {
					double newCost = determineCost(currentLocation, neighbor);
					if (!minimumCosts.containsKey(neighbor) || newCost < minimumCosts.get(neighbor)) {
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

	private List<Location> reconstructPath(Location destination) {
		List<Location> path = new LinkedList<Location>();
		Location next = destination;
		while (next != null) {
			path.add(next);
			if (myGrid.getNeighborsForXYCoordinate(origin).contains(next)) {
				break;
			}
			next = previousLocations.get(next);
		}
		return path;
	}
}
