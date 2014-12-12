package engine.stateManaging;

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

    private DrawableGameElement selectedElement;

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
                .filter(o -> o.isType(typeName)).map(o -> o)
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

    public SelectableGameElement addSelectableGameElementToLevel (String typeName,
                                                                  double x,
                                                                  double y,
                                                                  String color) {
        SelectableGameElement newElement = myFactory
                .createSelectableGameElement(typeName, x, y, color);
        myLevel.addElement(newElement);
        return newElement;
    }

    public void selectAnySingleUnit (Point2D clickLoc, Participant u) {
        selectedElement = null;
        deselectAllElements();
        List<DrawableGameElement> units = myLevel.getUnits().stream().map((unit)->{
            return (DrawableGameElement) unit;
        }).collect(Collectors.toList());
        selectUnit(units, clickLoc);
        selectUnit(myLevel.getTerrain(), clickLoc);
    }

    private void selectUnit (List<DrawableGameElement> elements, Point2D clickLoc) {
        for (DrawableGameElement e : elements) {
            double[] cornerBounds = e.findGlobalBounds();
            if (new Polygon(cornerBounds).contains(clickLoc)) {
                selectedElement = e;
                return;
            }
        }
    }

    private void deselectAllElements() {
        myLevel.getUnits().forEach((unit)->unit.deselect());
    }
    
    public void moveSelectedUnit (Point2D mapPoint2d, Participant user) {
        if (selectedElement == null) return;
        selectedElement.setPosition(mapPoint2d.getX(), mapPoint2d.getY());
    }
    
    public void deleteSelectedUnit() {
        if (selectedElement == null) return;
        myLevel.removeElement(selectedElement);
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

    private boolean checkOnTeam (SelectableGameElement e, Participant u) {
        return u.checkSameTeam(e.getTextualAttribute(StateTags.TEAM_COLOR.getValue()));
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
        if (filterTeamIDElements(filterSelectedUnits(myLevel.getUnits()), u).size() > 0) {

            boolean elementSelected = false;
            for (SelectableGameElement e : myLevel.getUnits()) {
                double[] cornerBounds = e.findGlobalBounds();
                if (new Polygon(cornerBounds).contains(click)) {
                    notifySelectedElementsOfTarget(e, u);
                    elementSelected = true;
                }
            }
            if (!elementSelected) {
                clearSelectedElementsOfTarget(u);
            }
        }

        // if location is clicked on instead, tell all selected units to go there
        for (SelectableGameElement e : filterSelectedUnits(myLevel.getUnits())) {
            if (!checkOnTeam(e, u)) continue;
            if (queueCommand) {
            	e.addWaypoint(click.getX(), click.getY());
            }
            else {
                double currentX = e.getNumericalAttribute(StateTags.X_POSITION.getValue()).doubleValue();
                double currentY = e.getNumericalAttribute(StateTags.Y_POSITION.getValue()).doubleValue();
                Location from = new Location(currentX, currentY);
                Location to = new Location(click.getX(), click.getY());
                List<Location> waypoints = pathingComputer.findPath(from, to);
                e.setWaypoints(waypoints);
                //e.clearPaths();
                //e.addToPath(to);
            }
        }
    }

    private void notifySelectedElementsOfTarget (SelectableGameElement e, Participant u) {
        filterTeamIDElements(filterSelectedUnits(myLevel.getUnits()), u)
                .forEach(unit -> unit.setFocusedElement(e));

    }

    private void clearSelectedElementsOfTarget (Participant u) {
        filterTeamIDElements(filterSelectedUnits(myLevel.getUnits()), u)
                .forEach(unit -> unit.clearFocusedElement());
    }

    private List<SelectableGameElement> filterSelectedUnits (List<SelectableGameElement> list) {
        return list
                .stream()
                .filter(unit -> unit.getNumericalAttribute(StateTags.IS_SELECTED.getValue()).doubleValue() == 1)
                .collect(Collectors.toList());
    }

    private List<SelectableGameElement> filterTeamIDElements (List<SelectableGameElement> list,
                                                              Participant p) {
        return list
                .stream()
                .filter(e -> p.checkSameTeam(e.getTextualAttribute(StateTags.TEAM_COLOR.getValue())
                		)).collect(Collectors.toList());
    }

    public void notifyButtonClicked (int buttonID, Participant u) {
        for (SelectableGameElement e : filterTeamIDElements(filterSelectedUnits(myLevel.getUnits()), u)) {
            e.setNumericalAttribute(StateTags.LAST_BUTTON_CLICKED_ID.getValue(), buttonID);
            e.executeAllButtonActions();
        }
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
