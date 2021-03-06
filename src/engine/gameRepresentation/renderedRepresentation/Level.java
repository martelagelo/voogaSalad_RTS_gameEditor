package engine.gameRepresentation.renderedRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.Group;
import model.state.LevelState;
import model.state.gameelement.StateTags;
import engine.computers.pathingComputers.MapGrid;

/**
 * The representation of a basic level in the game. Contains a state of the
 * level, a display group, lists of terrains and units, and a list of goals for
 * the level. Essentially acts as a displayable wrapper around the serializable
 * levelState object.
 *
 * @author Nishad, Jonathan, Rahul
 *
 */
public class Level {

    private LevelState myState;
    private Group myTerrainAndBuildingsGroup;
    private Group myUnitsGroup;
    private MapGrid myGrid;
    private List<DrawableGameElement> myTerrain;
    private List<SelectableGameElement> myUnits;
    private List<GameElement> myGoals;

    /**
     * Take in a level state and create the visual representation of the level
     * from it.
     *
     * @param levelState
     */
    public Level (LevelState levelState, List<DrawableGameElement> terrain,
            List<SelectableGameElement> units, List<GameElement> goals,
            Group terrainAndBuildingsGroup, Group unitsGroup, MapGrid grid) {
        myState = levelState;
        myTerrain = terrain;
        myUnits = new CopyOnWriteArrayList<>(units);
        myGoals = goals;
        myTerrainAndBuildingsGroup = terrainAndBuildingsGroup;
        myUnitsGroup = unitsGroup;
        myGrid = grid;
    }

    public LevelState getLevelState () {
        return myState;
    }

    public double getMapWidth () {
        return myState.myAttributes.getNumericalAttribute(StateTags.LEVEL_WIDTH.getValue())
                .doubleValue();
    }

    public double getMapHeight () {
        return myState.myAttributes.getNumericalAttribute(StateTags.LEVEL_HEIGHT.getValue())
                .doubleValue();
    }

    public String getName () {
        return myState.getName();
    }

    public String getDescription () {
        return myState.getDescription();
    }

    public MapGrid getGrid () {
        return myGrid;
    }

    public List<DrawableGameElement> getTerrain () {
        return myTerrain;
    }

    public List<SelectableGameElement> getUnits () {
        return myUnits;
    }

    public List<GameElement> getGoals () {
        return myGoals;
    }

    /**
     * Get a numeric attribute contained by the game element
     *
     * @param attributTag
     *            the tag of the attribute of interest
     * @return the attribute's value or 0 if the attribute was not declared
     */
    public Number getNumericalAttribute (String attributeTag) {
        return myState.myAttributes.getNumericalAttribute(attributeTag);
    }

    /**
     * Set a numeric attribute contained by the game element
     *
     * @param attributeTag
     *            the tag of the attribute
     * @param attributeValue
     *            the value of the attribute
     */
    public void setNumericalAttribute (String attributeTag, Number attributeValue) {
        myState.myAttributes.setNumericalAttribute(attributeTag, attributeValue);
    }

    /**
     * Get a textual attribute contained by the game element
     *
     * @param attributeTag
     *            the tag of the attribute
     * @return the value of the attribute or "" if it does not exist
     */
    public String getTextualAttribute (String attributeTag) {
        return myState.myAttributes.getTextualAttribute(attributeTag);
    }

    /**
     * Set the value of a textual attribute contained by the game element
     *
     * @param attributeTag
     *            the tag of the attribute
     * @param attributeValue
     *            the attribute's value
     */
    public void setTextualAttribute (String attributeTag, String attributeValue) {
        myState.myAttributes.setTextualAttribute(attributeTag, attributeValue);
    }

    public List<Group> getGroups () {
        List<Group> displayGroups = new ArrayList<Group>();
        displayGroups.add(myTerrainAndBuildingsGroup);
        displayGroups.add(myUnitsGroup);
        return displayGroups;
    }

    /**
     * Remove an element from the level
     *
     * @param element
     *            the element to be removed
     */
    public void removeElement (DrawableGameElement element) {
        myState.removeElement(element.getState());
        myUnits.remove(element);
        myTerrain.remove(element);
        myUnitsGroup.getChildren().remove(element.getNode());
        myTerrainAndBuildingsGroup.getChildren().remove(element.getNode());
    }

    public void addElement (GameElement newElement) {
        myGoals.add(newElement);
        newElement.registerAsChild(s -> myState.addGoal(s));
    }

    public void addElement (DrawableGameElement newElement) {
        myTerrain.add(newElement);
        myTerrainAndBuildingsGroup.getChildren().add(newElement.getNode());
        if (newElement.getNumericalAttribute(StateTags.BLOCKING.getValue()).intValue() == 1) {
            myGrid.registerObstaclePlacement(newElement.getBounds());
        }
        newElement.registerAsDrawableChild(s -> myState.addTerrain(s));
    }

    public void addElement (SelectableGameElement newElement) {
        myUnits.add(newElement);
        if (newElement.getNumericalAttribute(StateTags.MOVEMENT_SPEED.getValue()).doubleValue() == 0) {
            myTerrainAndBuildingsGroup.getChildren().add(newElement.getNode());
            if (newElement.getNumericalAttribute(StateTags.BLOCKING.getValue()).intValue() == 1) {
                myGrid.registerObstaclePlacement(newElement.getBounds());
            }
        } else {
            myUnitsGroup.getChildren().add(newElement.getNode());
        }
        newElement.registerAsSelectableChild(s -> myState.addUnit(s));
    }

    /**
     * Evaluates all the goals of the level and determines whether the level
     * needs to end
     *
     * @return positive if player has won, negative if player has lost, 0 else.
     *         If both win and lose are set, will return negative (losing
     *         overrides winning)
     */
    public int evaluateGoals () {
        // TODO: implement this in actions
        int won = 0;
        int lost = 0;
        for (GameElement goal : getGoals()) {
            goal.update();
            // iterate through the goals
            // evaluating all of these goals will set the internal values of
            // "won" or "lost"
            // TODO fix these hard strings
            if (goal.getNumericalAttribute("Won").doubleValue() == 1.0) {
                won = 1;
            }
            if (goal.getNumericalAttribute("Lost").doubleValue() == 1.0) {
                lost = 1;
            }
        }
        return won - 2 * lost;
    }

}
