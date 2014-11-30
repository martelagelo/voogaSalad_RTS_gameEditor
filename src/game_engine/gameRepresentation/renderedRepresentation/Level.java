package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.computers.pathingComputers.MapGrid;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.StateTags;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;


/**
 * The representation of a basic level in the game. Contains a state of the level, a display group,
 * lists of terrains and units, and a list of goals for the level. Essentially acts as a displayable
 * wrapper around the serializable levelState object.
 * 
 * @author Jonathan, Nishad, Rahul
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
     * Take in a level state and create the visual representation of the level from it.
     * 
     * @param levelState
     */
    public Level (LevelState levelState,
                  List<DrawableGameElement> terrain,
                  List<SelectableGameElement> units,
                  List<GameElement> goals,
                  Group terrainAndBuildingsGroup,
                  Group unitsGroup,
                  MapGrid grid) {
        myState = levelState;
        myTerrain = terrain;
        myUnits = units;
        myGoals = goals;
        myTerrainAndBuildingsGroup = terrainAndBuildingsGroup;
        myUnitsGroup = unitsGroup;
        myGrid = grid;
    }

    public LevelState getLevelState () {
        return myState;
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
     * @param attributTag the tag of the attribute of interest
     * @return the atribute's value or 0 if the attribute was not declared
     */
    public Number getNumericalAttribute (String attributeTag) {
        return myState.attributes.getNumericalAttribute(attributeTag);
    }

    /**
     * Set a numeric attribute contained by the game element
     *
     * @param attributeTag the tag of the attribute
     * @param attributeValue the value of the attribute
     */
    public void setNumericalAttribute (String attributeTag, Number attributeValue) {
        myState.attributes.setNumericalAttribute(attributeTag, attributeValue);
    }

    /**
     * Get a textual attribute contained by the game element
     *
     * @param attributeTag the tag of the attribute
     * @return the value of the attribute or "" if it does not exist
     */
    public String getTextualAttribute (String attributeTag) {
        return myState.attributes.getTextualAttribute(attributeTag);
    }

    /**
     * Set the value of a textual attribute contained by the game element
     *
     * @param attributeTag the tag of the attribute
     * @param attributeValue the attribute's value
     */
    public void setTextualAttribute (String attributeTag, String attributeValue) {
        myState.attributes.setTextualAttribute(attributeTag, attributeValue);
    }


    public List<Group> getGroups () {
        List<Group> displayGroups = new ArrayList<Group>();
        displayGroups.add(myTerrainAndBuildingsGroup);
        displayGroups.add(myUnitsGroup);
        return displayGroups;
    }

    public void addElement (GameElement newElement) {
        myGoals.add(newElement);
    }

    public void addElement (DrawableGameElement newElement) {
        myTerrain.add(newElement);
        myTerrainAndBuildingsGroup.getChildren().add(newElement.getNode());
        if (newElement.getNumericalAttribute(StateTags.BLOCKING).intValue() == 1) {
            myGrid.registerObstaclePlacement(newElement.getBounds());
        }
    }

    public void addElement (SelectableGameElement newElement) {
        myUnits.add(newElement);
        if (newElement.getNumericalAttribute(StateTags.MOVEMENT_SPEED).doubleValue() == 0) {
            myTerrainAndBuildingsGroup.getChildren().add(newElement.getNode());
            if (newElement.getNumericalAttribute(StateTags.BLOCKING).intValue() == 1) {
                myGrid.registerObstaclePlacement(newElement.getBounds());
            }
        }
        else {
            myUnitsGroup.getChildren().add(newElement.getNode());
        }
    }

}
