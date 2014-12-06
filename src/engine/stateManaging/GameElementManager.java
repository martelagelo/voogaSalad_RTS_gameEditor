package engine.stateManaging;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import model.state.gameelement.StateTags;
import engine.computers.pathingComputers.Location;
import engine.computers.pathingComputers.PathingComputer;
import engine.elementFactories.GameElementFactory;
import engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.gameRepresentation.renderedRepresentation.Level;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import engine.users.Participant;


/**
 * A manager for selecting, deselecting, and interacting with game elements
 *
 * @author John, Steve, Michael D., Zach
 *
 */
public class GameElementManager {

    private Level myLevel;
    private GameElementFactory myFactory;
    private PathingComputer pathingComputer;

    public GameElementManager (GameElementFactory factory) {
        myFactory = factory;
    }

    /**
     * Set the level for the element manager
     * 
     * @param level
     */
    public void setLevel (Level level) {
        myLevel = level;
        pathingComputer = new PathingComputer(myLevel.getGrid());
    }

    /**
     * Get all the game elements of a given type
     *
     * @param typeName
     *        the name of the type of the game elements
     * @return all the game elements with the given type
     */
    public List<GameElement> findAllElementsOfType (String typeName) {
        return myLevel.getUnits().stream()
                .filter(o -> o.getType().equals(typeName)).map(o -> o)
                .collect(Collectors.toList());
    }

    public void addGameElementToLevel (String typeName, double x, double y) {
        GameElement newElement = myFactory.createGameElement(typeName, x, y);
        myLevel.addElement(newElement);
    }

    public void addDrawableGameElementToLevel (String typeName, double x,
                                               double y) {
        DrawableGameElement newElement = myFactory.createDrawableGameElement(
                                                                             typeName, x, y);
        myLevel.addElement(newElement);
    }

    public void addSelectableGameElementToLevel (String typeName, double x,
                                                 double y) {
        SelectableGameElement newElement = myFactory
                .createSelectableGameElement(typeName, x, y);
        myLevel.addElement(newElement);
    }

    /**
     * Select all the elements in a given rectangle
     *
     * @param rectPoints
     *        the points in the rectangle surrounding the player's units
     */
    public void selectUnitsInBounds (double[] rectPoints, boolean multiSelect, Participant u) {
        boolean isTeamSelected = false;
        for (SelectableGameElement e : myLevel.getUnits()) {
            if (contains(rectPoints, e.getPosition()) && checkOnTeam(e, u)) {
                isTeamSelected = true;
                break;
            }
        }

        for (SelectableGameElement e : myLevel.getUnits()) {
            if (!multiSelect || !checkOnTeam(e, u)) {
                e.deselect();
            }
            if (isTeamSelected) {
                if (contains(rectPoints, e.getPosition()) && checkOnTeam(e, u)) {
                    e.select();
                }
            }
            else {
                if (contains(rectPoints, e.getPosition())) {
                    e.select();
                    break;
                }
            }
        }
    }

    private void deselectAllElements () {
        for (SelectableGameElement e : myLevel.getUnits()) {
            e.deselect();
        }
    }

    private boolean checkOnTeam (SelectableGameElement e, Participant u) {
        return u.checkSameTeam(e.getNumericalAttribute(StateTags.TEAM_ID).doubleValue());
    }

    public void selectSingleUnit (Point2D clickLoc, boolean multiSelect, Participant u) {
        for (SelectableGameElement e : myLevel.getUnits()) {
            // deselects all units if only clicking on one
            if (!multiSelect) {
                e.deselect();
            }
            double[] cornerBounds = e.findGlobalBounds();
            if (new Polygon(cornerBounds).contains(clickLoc)) {
                e.select();
            }
            // don't want to multi-select enemy units
            if (multiSelect) {
                if (!checkOnTeam(e, u)) e.deselect();
            }
        }
    }

    public void setSelectedUnitCommand (Point2D click, boolean queueCommand, Participant u) {
        // check to see if a unit was right-clicked on
        if (filterSelectedUnits().stream()
                .filter(e -> u.checkSameTeam(e.getNumericalAttribute(StateTags.TEAM_ID).intValue())).count() > 0) {
            for (SelectableGameElement e : myLevel.getUnits()) {
                double[] cornerBounds = e.findGlobalBounds();
                if (new Polygon(cornerBounds).contains(click)) {
                    issueOrderToSelectedUnits(e);
                    return;
                }
            }
        }
        
        // if location is clicked on instead, tell all selected units to go there
        for (SelectableGameElement e : filterSelectedUnits()) {
            if (!checkOnTeam(e, u)) continue;
            if (queueCommand) {
                // TODO: either implement this and allow for having headings be a linked-list, or
                // remove this
                // e.clearHeadings();
            }
            else {
            	double currentX = e.getNumericalAttribute(StateTags.X_POSITION).doubleValue();
            	double currentY = e.getNumericalAttribute(StateTags.Y_POSITION).doubleValue();
            	Location from = new Location(currentX, currentY);
            	Location to = new Location(click.getX(), click.getY());
            	List<Location> waypoints = pathingComputer.findPath(from, to);
                e.setWaypoints(waypoints);
            }
        }
    }

    private void issueOrderToSelectedUnits (SelectableGameElement e) {
        System.out.println(e.getPosition().getX()+", "+e.getPosition().getY());
        
    }

    private List<SelectableGameElement> filterSelectedUnits () {
        return myLevel
                .getUnits()
                .stream()
                .filter(unit -> unit.getNumericalAttribute(StateTags.IS_SELECTED).doubleValue() == 1)
                .collect(Collectors.toList());
    }

    /**
     * Determines whether the rectangle defined by rectPoints contains a Point2D
     * 
     * @param rectPoints a length 4 array with points { x_top_right, y_top_right, x_bottom_left,
     *        y_bottom_left}
     * @param unitLocationCenter point2D that contains is checking
     * @return whether the rectangle contains the point2D
     */
    private boolean contains (double[] rectPoints, Point2D unitLocationCenter) {

        double topLeftX = rectPoints[0];
        double topLeftY = rectPoints[1];
        double bottomRightX = rectPoints[2];
        double bottomRightY = rectPoints[3];

        if (topLeftX <= unitLocationCenter.getX() && bottomRightX >= unitLocationCenter.getX()) {
            if (topLeftY <= unitLocationCenter.getY() && bottomRightY >= unitLocationCenter.getY()) { return true; }
        }
        return false;
    }
}
